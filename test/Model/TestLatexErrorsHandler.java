package Model;

import Controller.Session;
import Model.Exceptions.LatexErrorsHandler.LogErrorException;
import Model.Latex.LatexErrorsHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the latex errors handler methods.
 */
class TestLatexErrorsHandler {

    @Test
    void generalErrorsPrefix() {
        String creatorUsername = "test";
        String path = "C:\\Users\\Andre\\IdeaProjects\\TikzOverflow8\\test\\Model\\TestLatexErrorsHandler";
        String title = "projectTest";
        Project project = new Project(creatorUsername, path, title);
        Session.getInstance().setCurrentProject(project);
        LatexErrorsHandler latexErrorsHandler = new LatexErrorsHandler();
        assertEquals("C:/Users/Andre/IdeaProjects/TikzOverflow8/test/Model/TestLatexErrorsHandler/projectTest.tex:", latexErrorsHandler.generalErrorsPrefix());
    }

    @Test
    void errorLogs() throws LogErrorException {
        String creatorUsername = "test";
        String pathDirectory = "./test/Model/TestLatexErrorsHandler";
        String logFile1 = "./test/Model/TestLatexErrorsHandler/test1.log";
        String title1 = "test1";
        Project project1 = new Project(creatorUsername, pathDirectory, title1);
        Session.getInstance().setCurrentProject(project1);
        LatexErrorsHandler latexErrorsHandler = new LatexErrorsHandler();
        latexErrorsHandler.errorLogs(logFile1);
        assertEquals("*** (job aborted, no legal \\end found)", latexErrorsHandler.getErrors());
        assertEquals(1, latexErrorsHandler.getErrorsCounter());

        String logFile2 = "./test/Model/TestLatexErrorsHandler/test2.log";
        String title2 = "test2";
        Project project2 = new Project(creatorUsername, pathDirectory, title2);
        Session.getInstance().setCurrentProject(project2);
        latexErrorsHandler.errorLogs(logFile2);
        assertEquals(" File `articl.cls' not found.\n", latexErrorsHandler.getErrors());
        assertEquals(1, latexErrorsHandler.getErrorsCounter());

        String logFile3 = "./test/Model/TestLatexErrorsHandler/test3.log";
        String title3 = "test3";
        Project project3 = new Project(creatorUsername, pathDirectory, title3);
        Session.getInstance().setCurrentProject(project3);
        latexErrorsHandler.errorLogs(logFile3);
        assertEquals("line 1: LaTeX Error: Missing \\begin{document}.\n", latexErrorsHandler.getErrors());
        assertEquals(1, latexErrorsHandler.getErrorsCounter());

        String logFile4 = "./test/Model/TestLatexErrorsHandler/test4.log";
        String title4 = "test4";
        Project project4 = new Project(creatorUsername, pathDirectory, title4);
        Session.getInstance().setCurrentProject(project4);
        latexErrorsHandler.errorLogs(logFile4);
        assertEquals("line 2: LaTeX Error: Missing \\begin{document}.\n" +
                "line 6: LaTeX Error: \\usepackage before \\documentclass.\n" +
                "line 7: Undefined control sequence.\n" +
                "line 7: LaTeX Error: Missing \\begin{document}.\n" +
                "line 10: LaTeX Error: The font size command \\normalsize is not defined:               there is probably something wrong with the class file.\n" +
                "line 11: LaTeX Error: Environment tikzpicture undefined.\n" +
                "line 12: Undefined control sequence.\n" +
                "line 13: Undefined control sequence.\n" +
                "line 14: Undefined control sequence.\n" +
                "line 15: LaTeX Error: \\begin{document} ended by \\end{tikzpicture}.\n" +
                "line 16: LaTeX Error: The font size command \\normalsize is not defined:               there is probably something wrong with the class file.\n", latexErrorsHandler.getErrors());
        assertEquals(11, latexErrorsHandler.getErrorsCounter());

        String logFile5 = "./test/Model/TestLatexErrorsHandler/test5.log";
        String title5 = "test5";
        Project project5 = new Project(creatorUsername, pathDirectory, title5);
        Session.getInstance().setCurrentProject(project5);
        latexErrorsHandler.errorLogs(logFile5);
        assertEquals("line 8: LaTeX Error: Environment tikzpicture undefined.\n" +
                "line 9: Undefined control sequence.\n" +
                "line 10: Undefined control sequence.\n" +
                "line 11: Undefined control sequence.\n" +
                "line 12: LaTeX Error: \\begin{document} ended by \\end{tikzpicture}.\n", latexErrorsHandler.getErrors());
        assertEquals(5, latexErrorsHandler.getErrorsCounter());

        String logFile6 = "./test/Model/TestLatexErrorsHandler/test6.log";
        String title6 = "test6";
        Project project6 = new Project(creatorUsername, pathDirectory, title6);
        Session.getInstance().setCurrentProject(project6);
        latexErrorsHandler.errorLogs(logFile6);
        assertEquals("line 12: Undefined control sequence.\n" +
                "line 14: Package pgf Error: No shape named `h' is known.\n" +
                "line 14: Package pgf Error: No shape named `h' is known.\n" +
                "line 14: Package pgf Error: No shape named `h' is known.\n", latexErrorsHandler.getErrors());
        assertEquals(4, latexErrorsHandler.getErrorsCounter());

    }
}