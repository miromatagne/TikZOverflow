package Model;

import Model.Exceptions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
/*
public class TestProjectHandler {

    @Test
    public void creation() throws ProjectCreationException {

        ProjectHandler projectHandler = new ProjectHandler();


        //Creation
        User user1 = new User();
        user1.setUsername("User1");

        User user2 = new User();
        user2.setUsername("User2");

        Project project = projectHandler.createProject(user1);

        //Check of the default title
        assertEquals("Unnamed",project.getTitle());

        //Information setup

        project.setTitle("Premier projet");
        project.addCollaborator(user2.getUsername());
        project.setCode("code");

        //Check of the setup
        assertEquals(0 ,project.getID());
        assertEquals("Premier projet",project.getTitle());
        assertEquals("User1",project.getCreatorUsername());
        assertEquals("User2",project.getCollaboratorsUsernames().get(0));
        assertEquals("code",project.getCode());

    }

    @Test
    public void copy() throws ProjectCreationException, ProjectCopyException {

        ProjectHandler projectHandler = new ProjectHandler();
        User user1 = new User();
        Project project1 = projectHandler.createProject(user1);
        project1.setTitle("Project number 1");
        project1.setCode("code number 1");

        User user2 = new User();
        user2.setUsername("User2");
        project1.addCollaborator(user2.getUsername());

        Project project2 = projectHandler.createCopy(project1, user2);

        assertEquals("Project number 1", project2.getTitle());
        assertEquals("code number 1", project2.getCode());
        assertEquals("User2", project2.getCreatorUsername());

    }

    @Test
    void delete() throws ProjectCreationException, ProjectDeletionException {
        ProjectHandler projectHandler = new ProjectHandler();
        User user1 = new User();
        User user2 = new User();
        Project project1 = projectHandler.createProject(user1);
        projectHandler.createProject(user2);

        projectHandler.deleteProject(project1);
    }

    @Test
    void share() throws ProjectCreationException {
        ProjectHandler projectHandler = new ProjectHandler();
        User user1 = new User();
        User user2 = new User();
        user2.setUsername("User2");
        Project project1 = projectHandler.createProject(user1);
        projectHandler.shareProject(project1, user2);

        assertEquals("User2", project1.getCollaboratorsUsernames().get(0));

    }

    @Test
    void save() throws ProjectCreationException, ProjectSaveException, FileHandlerConstructorException, IOException {
        ProjectHandler projectHandler = ProjectHandler.getInstance();
        User user1 = new User();
        user1.setUsername("User1");
        Project project1 = projectHandler.createProject(user1);

        project1.setCode("Hello World");
        project1.setTitle("Mon projet");

        User user2 = new User();
        user2.setUsername("User2");

        projectHandler.shareProject(project1, user2);

        projectHandler.saveProjectInfo(project1);

        FileHandler fileHandler = new FileHandler();
        String fileContent = fileHandler.readInFile("projects/0");

        SimpleDateFormat dateFormatter = new SimpleDateFormat(ProjectHandler.DATE_FORMAT);
        String dateString = dateFormatter.format(project1.getDate());
        String contentExpected = "#0\n" +
                "#User1\n"+
                "#Mon projet\n"+
                "#"+dateString+"\n"+
                "#User2\n"+
                "Hello World\n";

        assertEquals(contentExpected, fileContent);
    }

    @Test
    void load() throws ProjectCreationException, ProjectLoadException, ProjectSaveException {
        ProjectHandler projectHandler = ProjectHandler.getInstance();
        User user = new User();
        user.setUsername("Createur");

        Project project  = projectHandler.createProject(user);
        project.setTitle("Mon projet 1");
        project.setCode("HELLO WORLD");

        projectHandler.saveProjectInfo(project);

        Project loadedProject = projectHandler.loadProject(0);

        assertEquals("Mon projet 1", loadedProject.getTitle());
        assertEquals("HELLO WORLD", loadedProject.getCode());
        assertEquals("Createur", loadedProject.getCreatorUsername());
        assertEquals(project.getDate().toString(), loadedProject.getDate().toString());
    }

    @Test
    void rename() throws ProjectCreationException {
        ProjectHandler projectHandler = ProjectHandler.getInstance();
        User user = new User();
        user.setUsername("Createur");
        Project project = projectHandler.createProject(user);
        project.setTitle("Title number 1");
        projectHandler.renameProject(project, "New number 1");

        assertEquals("New number 1", project.getTitle());
    }


}
*/