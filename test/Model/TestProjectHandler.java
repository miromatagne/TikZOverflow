package Model;

import Model.Exceptions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
public class TestProjectHandler {

    @Test
    public void creation() throws ProjectCreationException, DirectoryCreationException, ProjectAlreadyExistsException, ProjectDeletionException {

        ProjectHandler projectHandler = new ProjectHandler();


        //Creation
        User user1 = new User();
        user1.setUsername("User1");

        User user2 = new User();
        user2.setUsername("User2");

        Project project = projectHandler.createProject(user1,"Test/Project", "TestCreation");

        //Check of the default title
        assertEquals("TestCreation", project.getTitle());

        //Information setup
        project.setTitle("Premier projet");
        project.addCollaborator(user2.getUsername());
        project.setCode("code");

        //Check of the setup
        assertEquals("Premier projet", project.getTitle());
        assertEquals("User1", project.getCreatorUsername());
        assertEquals("User2", project.getCollaboratorsUsernames().get(0));
        assertEquals("code", project.getCode());
        projectHandler.deleteProject(project);
    }

    @Test
    public void copy() throws ProjectCreationException, ProjectCopyException, DirectoryCreationException, ProjectAlreadyExistsException, ProjectDeletionException {

        ProjectHandler projectHandler = new ProjectHandler();
        User user1 = new User();
        Project project1 = projectHandler.createProject(user1, "Test/Project", "TestCopy");

        project1.setTitle("Project number 1");
        project1.setCode("code number 1");

        User user2 = new User();
        user2.setUsername("User2");
        project1.addCollaborator(user2.getUsername());

        Project project2 = projectHandler.createCopy(project1, user2, "Test/Project2");

        assertEquals("Project number 1", project2.getTitle());
        assertEquals("code number 1", project2.getCode());
        assertEquals("User2", project2.getCreatorUsername());
        projectHandler.deleteProject(project1);
        projectHandler.deleteProject(project2);
    }

    @Test
    void delete() throws ProjectCreationException, ProjectDeletionException, DirectoryCreationException, ProjectAlreadyExistsException {
        ProjectHandler projectHandler = new ProjectHandler();
        User user1 = new User();
        //User user2 = new User();
        Project project1 = projectHandler.createProject(user1,"Test/Project", "TestDelete");
        //projectHandler.createProject(user2,"User2/Project", "TestDelete");

        projectHandler.deleteProject(project1);
    }

    /*
    @Test
    void share() throws ProjectCreationException, DirectoryCreationException, ProjectAlreadyExistsException, ProjectDeletionException {
        ProjectHandler projectHandler = new ProjectHandler();
        User user1 = new User();
        User user2 = new User();
        user2.setUsername("User2");
        Project project1 = projectHandler.createProject(user1,"Test/Project", "TestShare");
        projectHandler.shareProject(project1, user2);

        assertEquals("User2", project1.getCollaboratorsUsernames().get(0));
        projectHandler.deleteProject(project1);
    }
*/
    /*
    @Test
    void save() throws ProjectCreationException, ProjectSaveException, FileHandlerConstructorException, IOException, DirectoryCreationException, ProjectAlreadyExistsException, ProjectDeletionException {
        ProjectHandler projectHandler = new ProjectHandler();
        User user1 = new User();
        user1.setUsername("User1");
        Project project1 = projectHandler.createProject(user1,"Test/Project", "TestSave");

        project1.setCode("Hello World");

        User user2 = new User();
        user2.setUsername("User2");

        projectHandler.shareProject(project1, user2);

        projectHandler.saveProjectInfo(project1);

        UserHandler userHandler = new UserHandler();
        String fileContent = userHandler.readInFile(new File(project1.getPath() + File.separator + "project.properties"));

        SimpleDateFormat dateFormatter = new SimpleDateFormat(ProjectHandler.DATE_FORMAT);
        String dateString = dateFormatter.format(project1.getDate());
        String contentExpected = "title:TestSave\n"+
                "creator:User1\n"+
                "collaborators:User2\n"+
                "creation_date:"+dateString+"\n"+
                "modification_date:"+ dateFormatter.format(new Date())+"\n";

        assertEquals(contentExpected, fileContent);
        projectHandler.deleteProject(project1);
    }*/

    @Test
    void load() throws ProjectCreationException, ProjectLoadException, ProjectSaveException, DirectoryCreationException, ProjectAlreadyExistsException, ProjectDeletionException {
        ProjectHandler projectHandler = new ProjectHandler();
        User user = new User();
        user.setUsername("creator");

        Project project  = projectHandler.createProject(user,"Test/Project", "TestLoad");
        project.setTitle("My project 1");

        projectHandler.saveProjectInfo(project);

        Project loadedProject = projectHandler.loadProject("Test/Project");

        assertEquals("My project 1", loadedProject.getTitle());
        assertEquals("creator", loadedProject.getCreatorUsername());
        assertEquals(project.getDate().toString(), loadedProject.getDate().toString());
        projectHandler.deleteProject(project);
    }

    /*
    @Test
    void rename() throws ProjectCreationException, DirectoryCreationException, ProjectAlreadyExistsException, ProjectDeletionException {
        ProjectHandler projectHandler = new ProjectHandler();
        User user = new User();
        user.setUsername("Createur");
        Project project = projectHandler.createProject(user,"User/Project", "TestRename");
        project.setTitle("Title number 1");
        projectHandler.renameProject(project, "New number 1");

        assertEquals("New number 1", project.getTitle());
        projectHandler.deleteProject(project);
    }
*/

}