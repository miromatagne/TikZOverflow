package Model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class used to handle PDF files.
 */
public class PDFHandler {
    private String pdfPath;

    /**
     * Constructor
     * @param pdfPath   path to the pdf file to be treated
     */
    public PDFHandler(String pdfPath){
        this.pdfPath = pdfPath;
    }

    /**
     * Converts pdf file to PNG image file in the same directory
     * @throws Exception    if pdf file doesn't exist
     */
    public void convertPdfToImage() throws Exception {
        File file = new File(pdfPath);
        PDDocument document = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage renderedImage = renderer.renderImage(0);
        ImageIO.write(renderedImage, "PNG", new File(pdfPath.replace(".pdf", ".png")));
        document.close();
    }
}
