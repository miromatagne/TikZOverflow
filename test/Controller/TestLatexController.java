package Controller;

import Controller.Exceptions.GetTextInFileException;
import Controller.Exceptions.LatexControllerConstructorException;
import Model.Exceptions.*;
import Model.FileHandler;
import Model.Project;
import Model.ProjectHandler;
import Model.User;
import View.ViewControllers.MainPageViewController;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test the latex controller methods
 */
class TestLatexController {

    @Test
    void getTextInFile() {
        try {
            String path = "./test/Controller/TestLatexController/GetTextInFileDirectory/";
            String title = "Project";

            /* We have to delete the test project if it already exists */
            File directory = new File(path+File.separator+title);
            if (directory.exists()){
                FileHandler fileHandler = new FileHandler();
                fileHandler.deleteDirectory(directory);
            }

            LatexController latexControl = new LatexController(null);

            /* User creation */
            User user = new User();
            user.setUsername("test");
            Session.getInstance().setUser(user);

            /* Project creation */
            ProjectHandler projectHandler = new ProjectHandler();

            Project project = projectHandler.createProject(user, path, title);
            project.setCode("% Preamble\n" +
                    "\\documentclass{article}\n" +
                    "\\pagenumbering{gobble}\n" +
                    "\n"+
                    "% Packages\n" +
                    "\\usepackage{tikz}\n"+
                    "\\usetikzlibrary{arrows.meta}\n" +
                    "\n" +
                    "% Document\n"+
                    "\\begin{document}\n"+
                    "  \\begin{tikzpicture}[remember picture, overlay, shift={(-4,-15)}]\n"+
                    "      \\node (h) at (0,0) {Hello};\n"+
                    "      \\node (w) at (2,3) {World};\n"+
                    "      \\draw (h) edge (w);\n"+
                    "  \\end{tikzpicture}\n"+
                    "\\end{document}");
            projectHandler.saveProjectInfo(project);

            Session.getInstance().setCurrentProject(project);

            /* Get the text in file */
            String stringTest = latexControl.getTextInFile();

            assertEquals("% Preamble\n" +
                            "\\documentclass{article}\n" +
                            "\\pagenumbering{gobble}\n" +
                            "\n"+
                            "% Packages\n" +
                            "\\usepackage{tikz}\n"+
                            "\\usetikzlibrary{arrows.meta}\n" +
                            "\n" +
                            "% Document\n"+
                            "\\begin{document}\n"+
                            "    \\begin{tikzpicture}[remember picture, overlay, shift={(-4,-15)}]\n"+
                            "        \\node (h) at (0,0) {Hello};\n"+
                            "        \\node (w) at (2,3) {World};\n"+
                            "        \\draw (h) edge (w);\n"+
                            "    \\end{tikzpicture}\n"+
                            "\\end{document}\n",
                    stringTest);
        } catch (GetTextInFileException | DirectoryCreationException | IOException | ProjectCreationException | ProjectSaveException | LatexWritingException | ProjectAlreadyExistsException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void saveTikz() {
        try {
            String path = "./test/Controller/TestLatexController/SaveTikzDirectory/";
            String title = "Project";

            /* We have to delete the test project if it already exists */
            File directory = new File(path+File.separator+title);
            if (directory.exists()){
                FileHandler fileHandler = new FileHandler();
                fileHandler.deleteDirectory(directory);
            }

            LatexController latexController = new LatexController(new MainPageViewController());
            User user = new User();
            user.setUsername("test");
            Session.getInstance().setUser(user);



            ProjectHandler projectHandler = new ProjectHandler();
            Project project = projectHandler.createProject(user, path, title);
            Session.getInstance().setCurrentProject(project);
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
        } catch (GetTextInFileException | DirectoryCreationException | ProjectCreationException | ProjectAlreadyExistsException | LatexWritingException | IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}