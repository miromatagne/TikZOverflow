package Model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFHandler {
    private String pdfPath;

    public PDFHandler(String pdfPath){
        this.pdfPath = pdfPath;
    }

    public void convertPdfToImage() throws Exception {
        File file = new File(pdfPath);
        PDDocument document = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage renderedImage = renderer.renderImage(0);
        ImageIO.write(renderedImage, "PNG", new File(pdfPath.replace(".pdf", ".png")));
        document.close();
    }
}
