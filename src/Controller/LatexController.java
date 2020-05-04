package Controller;

import Controller.Exceptions.BuildFullCodeFromShapesOnlyException;
import Controller.Exceptions.GetTextInFileException;
import Controller.Exceptions.LatexControllerConstructorException;
import Controller.Exceptions.TikzCompilationException;
import Model.Exceptions.*;
import Model.FileHandler;
import Model.LatexCompiler;
import Model.PDFHandler;
import View.ViewControllers.MainPageViewController;
import javafx.scene.image.Image;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles all requests regarding the TikZ source code coming from View.
 */
public class LatexController implements MainPageViewController.CodeInterfaceListener {

    FileHandler fileHandler;
    private final MainPageViewController mainPageViewController;

    /**
     * Constructor.
     *
     * @param mainPageViewController MainPageViewController we interact with
     * @throws LatexControllerConstructorException if FileHandler could not be constructed
     */
    public LatexController(MainPageViewController mainPageViewController) throws LatexControllerConstructorException {
        try {
            this.fileHandler = new FileHandler();
            this.mainPageViewController = mainPageViewController;
        } catch (FileHandlerConstructorException e) {
            throw new LatexControllerConstructorException(e);
        }
    }

    /**
     * Gets the source code from a previous save.
     *
     * @return Source code
     * @throws GetTextInFileException if an IOException occurs while reading the text from the file
     */
    public String getTextInFile() throws GetTextInFileException {
        try {
            String filePath = "./Latex/" + Session.getInstance().getUser().getUsername() + ".tex";
            return fileHandler.readInFile(filePath);
        } catch (IOException e) {
            throw new GetTextInFileException(e);
        }
    }

    /**
     * Compiles current source code to a .pdf file.
     *
     * @param sourceCode Source code to be compiled
     * @return String with error count
     * @throws TikzCompilationException If reading the log was unsuccessful or compilation failed
     */
    public String compileTikz(String sourceCode) throws TikzCompilationException {
        try {
            saveTikz(sourceCode);
            String filePath = "./Latex/" + Session.getInstance().getUser().getUsername() + ".tex";
            LatexCompiler.runProcess(filePath);
            String pdfPath = "./Latex/out/" + Session.getInstance().getUser().getUsername() + ".pdf";
            createImage(pdfPath);
            fileHandler.errorLogs("./Latex/out/" + Session.getInstance().getUser().getUsername() + ".log", Session.getInstance().getUser().getUsername());
            int errorsCount = fileHandler.getErrorsCounter();
            return "Errors (" + errorsCount + ")";
        } catch (LatexCompilationException | LogErrorException e) {
            throw new TikzCompilationException(e);
        }
    }

    /**
     * Creates image from compilation result (PDF).
     *
     * @param pdfPath path to Latex compilation output (PDF format)
     */
    public void createImage(String pdfPath) {
        PDFHandler pdfHandler = new PDFHandler(pdfPath);
        try {
            pdfHandler.convertPdfToImageOnDisk();
            String imagePath = pdfPath.replace(".pdf", ".jpg");
            Image renderedImage = new Image(new FileInputStream(imagePath));
            mainPageViewController.renderImage(renderedImage);
        } catch (IOException e) {
            System.err.println("Image file not found");
            e.printStackTrace();
            AlertController.showStageError("Error while converting the pdf into a jpg to get an overview.", "Overview not available now");
        }
    }

    /**
     * Save current source code in .tex file
     *
     * @param sourceCode Source code to be saved
     */
    public void saveTikz(String sourceCode) {
        try {
            fileHandler.makeTexFile(Session.getInstance().getUser(), sourceCode);
        } catch (LatexWritingException e) {
            System.err.println("Error while writing in tex file");
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while writing in the tex file.", "Tex file not written");
        }
    }

    /**
     * Extracts shapes-only code from full LaTeX code.
     *
     * @param fullCode full LaTeX source code.
     * @param trim     option to trim lines or not
     * @return TikZ shapes-only code
     */
    public String extractShapesSubCode(String fullCode, boolean trim) {
        Pattern pattern = Pattern.compile("\\\\begin\\{tikzpicture}.*\\\\end\\{tikzpicture}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fullCode);
        if (matcher.find()) {
            int index = 1;
            String totalString = "";
            String[] strings = matcher.group(0).split("\n");
            while (index < strings.length - 1) {
                if (trim) {
                    totalString = totalString.concat(strings[index++].trim() + "\n");
                } else {
                    totalString = totalString.concat(strings[index++] + "\n");
                }
            }
            return totalString;
        }
        return null;
    }

    /**
     * Builds full LaTeX code from shapes-only code.
     *
     * @param shapesOnlyCode shapes-only TikZ code
     * @return full LaTeX code
     * @throws BuildFullCodeFromShapesOnlyException if an IOException occurs while reading the text from the file
     */
    public String buildFullCodeFromShapesOnlyCode(String shapesOnlyCode) throws BuildFullCodeFromShapesOnlyException {
        try {
            StringBuilder shapesOnlyCodeBuilder = new StringBuilder();
            for (String line : shapesOnlyCode.split("\n")) {
                shapesOnlyCodeBuilder.append("\t").append(line.trim()).append("\n");
            }
            return getTextInFile().replace(Objects.requireNonNull(extractShapesSubCode(getTextInFile(), false)), shapesOnlyCodeBuilder.toString());
        } catch (GetTextInFileException e) {
            throw new BuildFullCodeFromShapesOnlyException(e);
        }

    }

    /**
     * Compiles code and refreshes error button text on main page.
     *
     * @param code TikZ shape-only code
     */
    @Override
    public void onCompilationAttempt(String code) {
        try {
            String sourceCode = buildFullCodeFromShapesOnlyCode(code);
            String errorsButtonText;
            errorsButtonText = compileTikz(sourceCode);
            mainPageViewController.setErrorButtonText(errorsButtonText);
        } catch (TikzCompilationException e) {
            System.err.println("TikZ/LaTeX compilation failed");
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while TikZ compilation.", "TikZ compilation failed");
        } catch (BuildFullCodeFromShapesOnlyException e) {
            System.err.println("Building code from shapes only failed");
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while building the code from the shapes.", "Code could not be built");
        }
    }

    /**
     * Reads code in file and extracts shapes-only sub code from it.
     *
     * @return TikZ shapes-only code
     */
    @Override
    public String getShapesOnlyText() {
        try {
            String textInLatexFile = getTextInFile();
            return extractShapesSubCode(textInLatexFile, true);
        } catch (GetTextInFileException e) {
            System.err.println("Error while getting the source code");
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while getting the source code.", "Source code not available");
        }
        return "";
    }

    /**
     * Reads full code from file.
     *
     * @return full LaTeX code
     */
    @Override
    public String getFullText() {
        try {
            return getTextInFile();
        } catch (GetTextInFileException e) {
            System.err.println("Error while getting the full text");
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while getting the full text from the save file.", "Save could not be loaded");
        }
        return "";
    }

    /**
     * Saves code from code interface into user's LaTeX file.
     *
     * @param tikzCode TikZ shapes-only code
     */
    @Override
    public void saveCodeInterfaceCode(String tikzCode) {
        try {
            saveTikz(buildFullCodeFromShapesOnlyCode(tikzCode));
        } catch (BuildFullCodeFromShapesOnlyException e) {
            System.err.println("Error while getting the full code");
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while saving the code in a LaTeX file from the code area.", "Latex file not saved");
        }
    }

    @Override
    public String getErrorsText() {
        return fileHandler.getErrors();
    }

    @Override
    public int getErrorsCounter() {
        return fileHandler.getErrorsCounter();
    }
}
