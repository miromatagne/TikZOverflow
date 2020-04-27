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
    private MainPageViewController mainPageViewController;

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
     * @throws IOException If reading the log was unsuccessful
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
        } catch (Exception e) {
            System.err.println("Error converting " + pdfPath + " to image");
            e.printStackTrace();
        }
        String imagePath = pdfPath.replace(".pdf", ".jpg");
        try {
            Image renderedImage = new Image(new FileInputStream(imagePath));
            mainPageViewController.renderImage(renderedImage);
        } catch (IOException e) {
            System.err.println("Image file not found");
            e.printStackTrace();
        }
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    /**
     * Save current source code in .tex file
     *
     * @param sourceCode Source code to be saved
     */
    public void saveTikz(String sourceCode) {
        try {
            fileHandler.makeTexFile(Session.getInstance().getUser(), sourceCode);
        } catch (LatexWritingException e){
            System.err.println("Error while writing in tex file");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
    }

    /**
     * Extracts shapes-only code from full LaTeX code.
     * @param fullCode full LaTeX source code.
     * @param trim option to trim lines or not
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
     * @param shapesOnlyCode shapes-only TikZ code
     * @return full LaTeX code
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

    @Override
    public void onCompilationAttempt(String code) {
        try {
            String sourceCode = buildFullCodeFromShapesOnlyCode(code);
            String errorsButtonText = null;
            errorsButtonText = compileTikz(sourceCode);
            mainPageViewController.setErrorButtonText(errorsButtonText);
        } catch (TikzCompilationException e) {
            System.err.println("TikZ/LaTeX compilation failed");
            e.printStackTrace();
            e.getCause().printStackTrace();
        } catch (BuildFullCodeFromShapesOnlyException e) {
            System.err.println("Building code from shapes only failed");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
    }

    @Override
    public String getShapesOnlyText() {
        try {
            String textInLatexFile = getTextInFile();
            String textSaved = extractShapesSubCode(textInLatexFile, true);
            return textSaved;
        } catch (GetTextInFileException e) {
            System.err.println("Error while getting the source code");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
        return "";
    }

    @Override
    public int getErrorsCounter() {
        return fileHandler.getErrorsCounter();
    }

    @Override
    public String getFullText() {
        try {
            return getTextInFile();
        } catch (GetTextInFileException e) {
            System.err.println("Error while getting the full text");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
        return "";
    }

    @Override
    public void saveCodeInterfaceCode(String tikzCode) {
        try {
            saveTikz(buildFullCodeFromShapesOnlyCode(tikzCode));
        } catch (BuildFullCodeFromShapesOnlyException e) {
            System.err.println("Error while getting the full code");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }

    }

    @Override
    public String getErrorsText() {
        return fileHandler.getErrors();
    }
}
