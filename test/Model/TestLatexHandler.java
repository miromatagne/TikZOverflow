package Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestLatexHandler {
    @Test
    void compile() {
        //Send to the compilerLatex the latex file
        String filePath = "./Latex/test_latex.t"; // Test typo
        try {
            LatexHandler.getInstance().runProcess(filePath, "./Latex/out");
            fail("Exception not thrown"); // Error
        }
        catch (Exception e){/*Where it needs to be*/}


        filePath = "./Latex/test_latex.tex";
        try {
            LatexHandler.getInstance().runProcess(filePath, "./Latex/out");
        }
        catch(Exception e){System.err.println("Error in compilation"); e.printStackTrace(); fail("Exception thrown");}
    }

    @Test
    void extractShapesSubCode() {
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
        assertEquals(LatexHandler.getInstance().extractShapesSubCode(sourceCode, true), shapesOnlyCode);
    }

    @Test
    void buildFullCodeFromShapesOnlyCode() {
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
        String shapesOnlyCode = "   \\node (h) at (0,1) {Hello};\n" +
                "   \\node (w) at (2,3) {World};\n" +
                "   \\draw (h) edge (w);\n";
        String result = "\\documentclass{standalone}\n" +
                "\n" +
                "\\usepackage{tikz}\n" +
                "\n" +
                "\\begin{document}\n" +
                "    \\begin{tikzpicture}\n" +
                "\t\\node (h) at (0,1) {Hello};\n" +
                "\t\\node (w) at (2,3) {World};\n" +
                "\t\\draw (h) edge (w);\n" +
                "    \\end{tikzpicture}\n" +
                "\\end{document}\n";
        assertEquals(LatexHandler.getInstance().buildFullCodeFromShapesOnlyCode(shapesOnlyCode, sourceCode), result);
    }
}