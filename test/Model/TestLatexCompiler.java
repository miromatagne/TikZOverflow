package Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestLatexCompiler {
    @Test
    void compile() {
        //Send to the compilerLatex the latex file
        String filePath = "./Latex/test_latex.t"; // Test typo
        try {
            LatexCompiler.getInstance().runProcess(filePath);
            fail("Exception not thrown"); // Error
        }
        catch (Exception e){/*Where it needs to be*/}


        filePath = "./Latex/test_latex.tex";
        try {
            LatexCompiler.getInstance().runProcess(filePath);
        }
        catch(Exception e){System.err.println("Error in compilation"); e.printStackTrace(); fail("Exception thrown");}
    }
}