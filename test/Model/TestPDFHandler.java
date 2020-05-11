package Model;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TestPDFHandler {

    @Test
    void convertPdfToImage() {
        PDFHandler pdfHandler = new PDFHandler("./test/Controller/TestPDFHandler/test_latex.pdf");
        try {
            pdfHandler.convertPdfToImageOnDisk();
        } catch (Exception e) {
            System.err.println("Error converting pdf file");
            e.printStackTrace();
        }
        File imageFile = new File("./test/Controller/TestPDFHandler/test_latex.jpg");
        assertTrue(imageFile.exists());
    }
}