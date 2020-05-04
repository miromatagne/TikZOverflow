package Controller;

import Controller.Exceptions.BuildFullCodeFromShapesOnlyException;
import Controller.Exceptions.GetTextInFileException;
import Controller.Exceptions.LatexControllerConstructorException;
import Model.LatexHandler;
import Model.User;
import View.ViewControllers.MainPageViewController;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestLatexController {

    @Test
    void getTextInFile() throws LatexControllerConstructorException, GetTextInFileException {
        LatexController latexControl = new LatexController(null);
        User user = new User();
        user.setUsername("test");
        Session.getInstance().setUser(user);
        String stringTest = latexControl.getTextInFile();

        assertEquals("\\documentclass{standalone}\n" +
                "\n" +
                "\\usepackage{tikz}\n" +
                "\n" +
                "\\begin{document}\n" +
                "    \\begin{tikzpicture}\n" +
                "        \\node (h) at (0,0) {Hello};\n" +
                "        \\node (w) at (2,3) {World};\n" +
                "        \\draw (h) edge (w);\n" +
                "    \\end{tikzpicture}\n" +
                "\\end{document}\n",
                stringTest);
    }

    @Test
    void createImage() throws LatexControllerConstructorException {
        LatexController latexController = new LatexController(new MainPageViewController());
        try {
            latexController.createImage("./Latex/out/test.pdf");
        } catch (NullPointerException e) {
            System.out.println("Image file could not be rendered in display");
        }
        assertTrue(new File("./Latex/out/test.jpg").exists());
    }

    @Test
    void saveTikz() throws GetTextInFileException, LatexControllerConstructorException {
        LatexController latexController = new LatexController(new MainPageViewController());
        User user = new User();
        user.setUsername("test");
        Session.getInstance().setUser(user);
        String sourceCode = "\\documentclass{standalone}\n" +
                "\n" +
                "\\usepackage{tikz}\n" +
                "\n" +
                "\\begin{document}\n" +
                "    \\begin{tikzpicture}\n" +
                "        \\node (h) at (0,0) {Hello};\n" +
                "        \\node (w) at (2,3) {World};\n" +
                "        \\draw (h) edge (w);\n" +
                "    \\end{tikzpicture}\n" +
                "\\end{document}\n";
        latexController.saveTikz(sourceCode);
        assertEquals(latexController.getTextInFile(), sourceCode);
    }
}