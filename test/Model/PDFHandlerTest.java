package Model;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PDFHandlerTest {

    @Test
    void convertPdfToImage() {
        PDFHandler pdfHandler = new PDFHandler("./out/test_latex.pdf");
        try {
            pdfHandler.convertPdfToImageOnDisk();
        } catch (Exception e) {
            System.err.println("Error converting pdf file");
            e.printStackTrace();
        }
        File imageFile = new File("./out/test_latex.jpg");
        assertTrue(imageFile.exists());
    }
}