package Controller;

import Model.FileHandler;
import Model.LatexCompiler;
import Model.PDFHandler;
import View.MainPageController;
import javafx.scene.image.Image;


import java.io.FileInputStream;
import java.io.IOException;

/**
 * Handles all requests regarding the TikZ source code coming from View.
 */
public class LatexController {

    FileHandler fileHandler = new FileHandler();
    private MainPageController mainPageController;

    public LatexController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    /**
     * Gets the source code from a previous save.
     *
     * @return Source code
     */
    public String getTextInFile() {
        String filePath = "./Latex/" + Session.getInstance().getUser().getUsername() + ".tex";
        return fileHandler.readInFile(filePath);
    }

    /**
     * Compiles current source code to a .pdf file.
     *
     * @return String with error count
     * @throws IOException If reading the log was unsuccessful
     */
    public String compileTikz() throws IOException {
        saveTikz();
        String filePath = "./Latex/" + Session.getInstance().getUser().getUsername() + ".tex";

        try {
            LatexCompiler.runProcess(filePath);
            String pdfPath = "./Latex/out/" + Session.getInstance().getUser().getUsername() + ".pdf";
            createImage(pdfPath);
        } catch (Exception e) {
            System.err.println("Error in compilation :  " + e.toString());
            fileHandler.errorLogs("./Latex/out/" + Session.getInstance().getUser().getUsername() + ".log", Session.getInstance().getUser().getUsername());
        }
        fileHandler.errorLogs("./Latex/out/" + Session.getInstance().getUser().getUsername() + ".log", Session.getInstance().getUser().getUsername());
        int errorsCount = fileHandler.getErrorsCounter();
        System.out.println("You got " + errorsCount + " errors on the last compilation \n" + fileHandler.getErrors());
        return "Errors (" + errorsCount + ")";
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
            mainPageController.renderImage(renderedImage);
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
     */
    public void saveTikz() {
        fileHandler.makeTexFile(Session.getInstance().getUser(), mainPageController.getCodeInterface().getText());
    }
}
