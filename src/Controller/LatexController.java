package Controller;

import Controller.Exceptions.BuildFullCodeFromShapesOnlyException;
import Controller.Exceptions.GetTextInFileException;
import Controller.Exceptions.LatexControllerConstructorException;
import Controller.Exceptions.TikzCompilationException;
import Model.*;
import Model.Exceptions.*;
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

    UserHandler userHandler;
    ProjectHandler projectHandler;
    private final MainPageViewController mainPageViewController;

    /**
     * Constructor.
     *
     * @param mainPageViewController MainPageViewController we interact with
     * @throws LatexControllerConstructorException if FileHandler could not be constructed
     */
    public LatexController(MainPageViewController mainPageViewController) throws LatexControllerConstructorException {
        try {
            this.userHandler = new UserHandler();
            this.projectHandler = new ProjectHandler();
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
            return projectHandler.getProjectCode();
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
            String filePath = Session.getInstance().getCurrentProject().getPath() + Session.getInstance().getCurrentProject().getTitle() + ".tex";
            System.out.println(filePath);
            LatexCompiler.runProcess(filePath);
            String pdfPath = Session.getInstance().getCurrentProject().getPath()+ Session.getInstance().getCurrentProject().getTitle() + ".pdf";
            createImage(pdfPath);
            userHandler.errorLogs(Session.getInstance().getCurrentProject().getPath() + Session.getInstance().getCurrentProject().getTitle() + ".log", Session.getInstance().getUser().getUsername());
            int errorsCount = userHandler.getErrorsCounter();
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

    /**
     * Save current source code in .tex file
     *
     * @param sourceCode Source code to be saved
     */
    public void saveTikz(String sourceCode) {
        try {
            projectHandler.makeTexFile(sourceCode);
            projectHandler.saveProjectInfo(Session.getInstance().getCurrentProject());
        } catch (LatexWritingException e) {
            System.err.println("Error while writing in tex file");
            e.printStackTrace();
            e.getCause().printStackTrace();
        } catch (ProjectSaveException e) {
            e.printStackTrace();
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
        } catch (BuildFullCodeFromShapesOnlyException e) {
            System.err.println("Building code from shapes only failed");
            e.printStackTrace();
            e.getCause().printStackTrace();
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
        }
    }

    @Override
    public String getErrorsText() {
        return userHandler.getErrors();
    }

    @Override
    public int getErrorsCounter() {
        return userHandler.getErrorsCounter();
    }
}
