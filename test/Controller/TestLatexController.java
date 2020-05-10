package Controller;

import Controller.Exceptions.BuildFullCodeFromShapesOnlyException;
import Controller.Exceptions.GetTextInFileException;
import Controller.Exceptions.LatexControllerConstructorException;
import Model.Exceptions.*;
import Model.LatexHandler;
import Model.Project;
import Model.ProjectHandler;
import Model.User;
import View.ViewControllers.MainPageViewController;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TestLatexController {

    @Test
    void getTextInFile() {
        try {
            String path = "./test/Controller/TestLatexControllerGetTextInFileDirectory/";
            String title = "Project";

            LatexController latexControl = new LatexController(null);

            /* User creation */
            User user = new User();
            user.setUsername("test");
            Session.getInstance().setUser(user);

            /* Project creation */
            ProjectHandler projectHandler = new ProjectHandler();
            Project project = null;
            try {
                project = projectHandler.createProject(user, path, title);
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
            } catch (ProjectCreationException | LatexWritingException | ProjectSaveException e) {
                e.printStackTrace();
                fail();
            } catch (ProjectAlreadyExistsException e) {
                /* Test project already exists, no need to recreate it */
                project = new Project(user.getUsername(),path,title);
                project.setPath(project.getPath()+File.separator+project.getTitle());
            }


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
        } catch (GetTextInFileException | LatexControllerConstructorException | DirectoryCreationException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void saveTikz() {
        try {
            String path = "./test/Controller/TestLatexControllerSaveTikzDirectory/";
            String title = "Project";

            LatexController latexController = new LatexController(new MainPageViewController());
            User user = new User();
            user.setUsername("test");
            Session.getInstance().setUser(user);

            /* If the project already exists, we have to choose an other title to test the method */
            int i = 0;
            while(new File(path+File.separator+title+i).exists()){
                i++;
            }
            String newTitle = title+i;

            ProjectHandler projectHandler = new ProjectHandler();
            Project project = projectHandler.createProject(user, path, newTitle);
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
        } catch (GetTextInFileException | LatexControllerConstructorException | DirectoryCreationException | ProjectCreationException | ProjectAlreadyExistsException | LatexWritingException e) {
            e.printStackTrace();
            fail();
        }
    }
}