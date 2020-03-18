package Model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PDFHandlerTest {

    @Test
    void convertPdfToImage() {
        PDFHandler pdfHandler = new PDFHandler("./Latex/out/test_latex.pdf");
        try {
            pdfHandler.convertPdfToImage();
        } catch (Exception e) {
            System.err.println("Error converting pdf file");
            e.printStackTrace();
        }
        File imageFile = new File("./Latex/out/test_latex.png");
        assertTrue(imageFile.exists());
    }
}