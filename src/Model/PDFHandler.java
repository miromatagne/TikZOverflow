package Model;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * Converts pdf file to jpeg image file in the same directory
     * @throws Exception    if pdf file doesn't exist
     */
    public void convertPdfToImageOnDisk() throws Exception {
        File file = new File(pdfPath);
        PDDocument document = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage renderedImage = renderer.renderImageWithDPI(0, 300, ImageType.RGB);
        ImageIO.write(renderedImage, "JPEG", new File(pdfPath.replace(".pdf", ".jpg")));
        document.close();
    }
}
