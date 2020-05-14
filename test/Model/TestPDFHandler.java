package Model;

import Model.Latex.PDFHandler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestPDFHandler {

    @Test
    void convertPdfToImage() {
        try {
            PDFHandler pdfHandler = new PDFHandler("./test/Model/TestPDFHandler/test_latex.pdf");
            pdfHandler.convertPdfToImageOnDisk();
            File imageFile = new File("./test/Model/TestPDFHandler/test_latex.jpg");
            assertTrue(imageFile.exists());
        } catch (IOException e) {
            System.err.println("Error converting pdf file");
            e.printStackTrace();
            fail();
        }
    }
}