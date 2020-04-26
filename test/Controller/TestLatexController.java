package Controller;

import Model.User;
import View.ViewControllers.MainPageViewController;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestLatexController {

    @Test
    void getTextInFile() {
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
    void compileTikz() {
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
        try {
            String result = latexController.compileTikz(latexController.getTextInFile());
            assertEquals(result, "Errors (0)");
        } catch (IOException e) {
            fail();
        }

    }

    @Test
    void createImage() {
        LatexController latexController = new LatexController(new MainPageViewController());
        try {
            latexController.createImage("./Latex/out/test.pdf");
        } catch (NullPointerException e) {
            System.out.println("Image file could not be rendered in display");
        }
        assertTrue(new File("./Latex/out/test.jpg").exists());
    }

    @Test
    void saveTikz() {
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

    @Test
    void extractShapesSubCode() {
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
        String shapesOnlyCode = "\\node (h) at (0,0) {Hello};\n" +
                "\\node (w) at (2,3) {World};\n" +
                "\\draw (h) edge (w);\n";
        assertEquals(latexController.extractShapesSubCode(sourceCode, true), shapesOnlyCode);
    }

    @Test
    void buildFullCodeFromShapesOnlyCode() {
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
                "\t\\node (h) at (0,0) {Hello};\n" +
                "\t\\node (w) at (2,3) {World};\n" +
                "\t\\draw (h) edge (w);\n" +
                "    \\end{tikzpicture}\n" +
                "\\end{document}\n";
        String shapesOnlyCode = "   \\node (h) at (0,0) {Hello};\n" +
                "   \\node (w) at (2,3) {World};\n" +
                "   \\draw (h) edge (w);\n";
        assertEquals(latexController.buildFullCodeFromShapesOnlyCode(shapesOnlyCode), sourceCode);
    }
}