package Controller;

import Model.Exceptions.LatexCompilationException;
import Model.Exceptions.LatexWritingException;
import Model.Exceptions.LogErrorException;
import Model.FileHandler;
import Model.LatexCompiler;
import Model.PDFHandler;
import View.ViewControllers.MainPageController;
import javafx.scene.image.Image;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        try {
            String filePath = "./Latex/" + Session.getInstance().getUser().getUsername() + ".tex";
            return fileHandler.readInFile(filePath);
        } catch (IOException e) {
            System.err.println("Error while getting source code");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
        return "";
    }

    /**
     * Compiles current source code to a .pdf file.
     *
     * @param sourceCode Source code to be compiled
     * @return String with error count
     */
    public String compileTikz(String sourceCode){
        try {
            saveTikz(sourceCode);
            String filePath = "./Latex/" + Session.getInstance().getUser().getUsername() + ".tex";
            LatexCompiler.runProcess(filePath);
            String pdfPath = "./Latex/out/" + Session.getInstance().getUser().getUsername() + ".pdf";
            createImage(pdfPath);
            fileHandler.errorLogs("./Latex/out/" + Session.getInstance().getUser().getUsername() + ".log", Session.getInstance().getUser().getUsername());
            int errorsCount = fileHandler.getErrorsCounter();
            System.out.println("You got " + errorsCount + " errors on the last compilation \n" + fileHandler.getErrors());
            return "Errors (" + errorsCount + ")";
        } catch (LatexCompilationException e) {
            System.err.println("Error in compilation");
            e.printStackTrace();
            e.getCause().printStackTrace();
        } catch (LogErrorException e){
            System.err.println("Error while writing log");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
        return "";

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
        } catch (IOException e) {
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
    public String buildFullCodeFromShapesOnlyCode(String shapesOnlyCode) {
        StringBuilder shapesOnlyCodeBuilder = new StringBuilder();
        for (String line : shapesOnlyCode.split("\n")) {
            shapesOnlyCodeBuilder.append("\t").append(line.trim()).append("\n");
        }
        return getTextInFile().replace(Objects.requireNonNull(extractShapesSubCode(getTextInFile(), false)), shapesOnlyCodeBuilder.toString());
    }
}
