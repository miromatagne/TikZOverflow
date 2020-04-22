package Controller;

import Model.User;
import View.ControllerSuperclass;
import org.junit.jupiter.api.Test;

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

}
