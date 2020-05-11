package Controller;

import Controller.Exceptions.GetTextInFileException;
import Controller.Exceptions.TikzCompilationException;
import Model.Exceptions.LatexCompilationException;
import Model.Exceptions.LatexWritingException;
import Model.Exceptions.LogErrorException;
import Model.Exceptions.ProjectSaveException;
import Model.*;
import View.ViewControllers.MainPageViewController;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Handles all requests regarding the TikZ source code coming from View.
 */
public class LatexController implements MainPageViewController.CodeInterfaceListener {

    final UserHandler userHandler;
    final ProjectHandler projectHandler;
    final LatexErrorsHandler latexErrorsHandler;
    private final MainPageViewController mainPageViewController;

    /**
     * Constructor.
     *
     * @param mainPageViewController MainPageViewController we interact with
     */
    public LatexController(MainPageViewController mainPageViewController) {
        this.userHandler = new UserHandler();
        this.projectHandler = new ProjectHandler();
        this.latexErrorsHandler = new LatexErrorsHandler();
        this.mainPageViewController = mainPageViewController;
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
            String filePath = Session.getInstance().getCurrentProject().getPath() + File.separator + Session.getInstance().getCurrentProject().getTitle() + ".tex";
            System.out.println(filePath);
            String pdfPath = Session.getInstance().getCurrentProject().getPath() + File.separator + Session.getInstance().getCurrentProject().getTitle() + ".pdf";
            LatexHandler.getInstance().runProcess(filePath, Session.getInstance().getCurrentProject().getPath());
            createImageFromPDF(pdfPath);
            latexErrorsHandler.errorLogs(Session.getInstance().getCurrentProject().getPath() + File.separator + Session.getInstance().getCurrentProject().getTitle() + ".log");
            int errorsCount = latexErrorsHandler.getErrorsCounter();
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
    public void createImageFromPDF(String pdfPath) {
        try {
            PDFHandler pdfHandler = new PDFHandler(pdfPath);
            pdfHandler.convertPdfToImageOnDisk();
            String imagePath = pdfPath.replace(".pdf", ".jpg");
            createImage(imagePath);
        } catch (IOException e) {
            System.err.println("Error converting " + pdfPath + " to image");
            e.printStackTrace();
        }
    }

    /**
     * Create a image and set it in the main page
     *
     * @param imagePath     Path to image
     * @throws IOException  If IO error occurs
     */
    private void createImage(String imagePath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(imagePath)){
            Image renderedImage = new Image(fileInputStream);
            mainPageViewController.renderImage(renderedImage);
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
     * Compiles code and refreshes error button text on main page.
     *
     * @param code TikZ shape-only code
     */
    @Override
    public void onCompilationAttempt(String code) {
        try {
            String sourceCode = LatexHandler.getInstance().buildFullCodeFromShapesOnlyCode(code, getTextInFile());
            String errorsButtonText;
            errorsButtonText = compileTikz(sourceCode);
            mainPageViewController.setErrorButtonText(errorsButtonText);
        } catch (TikzCompilationException e) {
            System.err.println("TikZ/LaTeX compilation failed");
            e.printStackTrace();
            e.getCause().printStackTrace();
        } catch (GetTextInFileException e) {
            System.err.println("Error reading project file.");
            AlertController.showStageError("Error while TikZ compilation.", "TikZ compilation failed");
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
            return LatexHandler.getInstance().extractShapesSubCode(textInLatexFile, true);
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
            String fullCode = LatexHandler.getInstance().buildFullCodeFromShapesOnlyCode(tikzCode, getTextInFile());
            saveTikz(fullCode);
        } catch (GetTextInFileException e){
            System.err.println("Error reading file before saving");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
    }

    @Override
    public String getErrorsText() {
        return latexErrorsHandler.getErrors();
    }

    @Override
    public int getErrorsCounter() {
        return latexErrorsHandler.getErrorsCounter();
    }
}
