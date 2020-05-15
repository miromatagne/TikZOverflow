package Model.Latex;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class used to handle PDF files.
 */
public class PDFHandler {
    private final String pdfPath;

    /**
     * Constructor.
     *
     * @param pdfPath path to the pdf file to be treated
     */
    public PDFHandler(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    /**
     * Converts pdf file to jpeg image file in the same directory.
     *
     * @throws IOException if pdf file doesn't exist
     */
    public void convertPdfToImageOnDisk() throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage renderedImage = renderer.renderImageWithDPI(0, 300, ImageType.RGB);
            ImageIO.write(renderedImage, "JPEG", new File(pdfPath.replace(".pdf", ".jpg")));
        }
    }
}
