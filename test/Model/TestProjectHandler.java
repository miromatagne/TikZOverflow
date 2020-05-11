package Model;

import Controller.Session;
import Model.Exceptions.DirectoryCreationException;
import Model.Exceptions.ProjectHandler.*;
import Model.Exceptions.UserAlreadyExistsException;
import Model.Exceptions.UserHandler.SaveUserCreationException;
import Model.Exceptions.UserHandler.SaveUserException;
import Model.Exceptions.UserHandler.UserFromSaveCreationException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
public class TestProjectHandler {

    private UserHandler userHandler;
    private ProjectHandler projectHandler;
    private User user;
    private final String TEST_DIRECTORY = "./test/Model/TestProjectHandler";


    public void testProjectHandlerSetup(String path){
        userHandler = UserHandler.getInstance();
        userHandler.setSaveUserDirectory(path);
        projectHandler = new ProjectHandler();
        user = new User();
        user.setFirstName("Armand");
        user.setLastName("Losfeld");
        user.setMail("armand.losfeld@ulb.ac.be");
        user.setPassword("mdp");
        user.setUsername("alosfeld");
        try {
            if (userHandler.saveUserExists(user.getUsername())){
                userHandler.saveUser(user);
            } else {
                userHandler.createUserSave(user);
            }
        }catch(SaveUserCreationException | SaveUserException | UserAlreadyExistsException e){
            fail("Impossible to create save of the user. Check saveCreatorUser test.");
        }
        Session.getInstance().setUser(user);
    }

    @Test
    public void createProject(){
        String path = TEST_DIRECTORY+File.separator+"CreateProjectDirectory";
        testProjectHandlerSetup(path);
        Project projectToCreate = null;
        try{
            projectToCreate = projectHandler.createProject(user,path,"test");
        } catch (ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException e){
            fail("Impossible to create project.");
        }

        user.getProjectPaths().add(projectToCreate.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        File directories = new File(projectToCreate.getPath());
        assertTrue(directories.exists());

        File properties = new File(projectToCreate.getPath() + File.separator + "project.properties");
        assertTrue(properties.exists());
        File tex = new File(projectToCreate.getPath() + File.separator +projectToCreate.getTitle() +".tex");
        assertTrue(tex.exists());

        String contentExpected = "% Preamble\n" +
                "\\documentclass{article}\n" +
                "\\pagenumbering{gobble}\n" +
                "\n" +
                "% Packages\n" +
                "\\usepackage{tikz}\n" +
                "\\usetikzlibrary{arrows.meta}\n" +
                "\n" +
                "% Document\n" +
                "\\begin{document}\n" +
                "    \\begin{tikzpicture}[remember picture, overlay, shift={(-4,-15)}]\n" +
                "        \\node (h) at (0,0) {Hello};\n" +
                "        \\node (w) at (2,3) {World};\n" +
                "        \\draw (h) edge (w);\n" +
                "    \\end{tikzpicture}\n" +
                "\\end{document}\n";

        String content = "";
        try{
            content = projectHandler.readInFile(projectToCreate.getPath() + File.separator + projectToCreate.getTitle() + ".tex");
        }catch(IOException e){
            fail("Impossible to read in tex file. check readInfile test.");
        }

        assertEquals(contentExpected,content);

        try{
            projectHandler.deleteProject(projectToCreate);
        }catch(ProjectDeletionException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to delete project. Check deleteProject test.");
        }
    }

    @Test
    public void saveProjectInfo(){
        String path = TEST_DIRECTORY+File.separator+"SaveProjectInfoDirectory";
        testProjectHandlerSetup(path);
        Project projectToSave = new Project(user.getUsername(),path,"test");
        user.getProjectPaths().add(projectToSave.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }
        try{
            projectHandler.saveProjectInfo(projectToSave);
        }catch(ProjectSaveException e){
            e.printStackTrace();
            fail("Impossible to save project.");
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat(ProjectHandler.DATE_FORMAT, Locale.ENGLISH);
        String dateString = dateFormatter.format(projectToSave.getCreationDate());
        String contentExpected = "title:test\n"+
                "creator:alosfeld\n"+
                "collaborators:\n"+
                "creation_date:"+dateString+"\n"+
                "modification_date:"+ dateFormatter.format(new Date())+"\n";

        String fileContent = "";
        try {
            fileContent = projectHandler.readInFile(projectToSave.getPath() + File.separator + "project.properties");
        } catch(IOException e){
            fail("Impossible to read in the project.properties.");
        }
        assertEquals(contentExpected, fileContent);

    }

    @Test
    public void renameProject(){
        String path = TEST_DIRECTORY+File.separator+"RenameProjectDirectory";
        testProjectHandlerSetup(path);
        Project projectToRename = null;
        try{
            projectHandler.deleteProject(new Project(user.getUsername(), path+File.separator+"test", "test"));
        } catch (ProjectDeletionException | SaveUserException | UserFromSaveCreationException e) {
            /* Project already deleted or still does not exist */
        }
        try{
            projectHandler.deleteProject(new Project(user.getUsername(), path+File.separator+"testRename", "testRename"));
        } catch (ProjectDeletionException | SaveUserException | UserFromSaveCreationException e) {
            /* Project already deleted or still does not exist */
        }

        try{
            projectToRename = projectHandler.createProject(user,path,"test");
        } catch (ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException e){
            fail("Impossible to create project. Check createProject test");
        }

        user.getProjectPaths().add(projectToRename.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        File properties = new File(projectToRename.getPath() + File.separator + "project.properties");
        assertTrue(properties.exists());
        File tex = new File(projectToRename.getPath() + File.separator +projectToRename.getTitle() +".tex");
        assertTrue(tex.exists());

        System.out.println(projectToRename.getPath());
        try{
            projectHandler.renameProject(projectToRename,"testRename");
        }catch(ProjectRenameException e){
            e.printStackTrace();
            fail("impossible to rename project.");
        }

        assertFalse(properties.exists());
        assertFalse(tex.exists());

        properties = new File(projectToRename.getPath() + File.separator + "project.properties");
        assertTrue(properties.exists());
        tex = new File(projectToRename.getPath() + File.separator +projectToRename.getTitle() +".tex");
        assertTrue(tex.exists());

        try{
            projectHandler.deleteProject(projectToRename);
        }catch(ProjectDeletionException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to delete project. Check deleteProject test.");
        }
    }

    @Test
    public void loadProject(){
        String path = TEST_DIRECTORY+File.separator+"LoadProjectDirectory";
        testProjectHandlerSetup(path);
        Project project = null;

        try{
            projectHandler.deleteProject(new Project(user.getUsername(), path+File.separator+"test", "test"));
        } catch (ProjectDeletionException | SaveUserException | UserFromSaveCreationException e) {
            /* Project is already deleted or does not exists */
        }

        try {
            project = projectHandler.createProject(user, path, "test");
        }catch(ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException e){
            fail("Impossible to create project. Check createProject test.");
        }

        user.getProjectPaths().add(project.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        Project loadProject = null;
        System.out.println(user.getProjectPaths().get(0));
        try{
            loadProject = projectHandler.loadProject(user.getProjectPaths().get(0));
        }catch(ProjectLoadException e){
            fail("Impossible to load project.");
        }

        assertEquals(project.getPath(),loadProject.getPath());
        assertEquals(project.getTitle(), loadProject.getTitle());
        assertEquals(project.getCreatorUsername(),loadProject.getCreatorUsername());
        assertEquals(new SimpleDateFormat(ProjectHandler.DATE_FORMAT,Locale.ENGLISH).format(project.getCreationDate()),new SimpleDateFormat(ProjectHandler.DATE_FORMAT,Locale.ENGLISH).format(loadProject.getCreationDate()));
        assertEquals(project.getCollaboratorsUsernames(),loadProject.getCollaboratorsUsernames());

    }

    @Test
    public void createCopy() {
        String path = TEST_DIRECTORY+File.separator+"CreateCopyDirectory";
        testProjectHandlerSetup(path);
        Project project1 = null;
        try {
            project1 = projectHandler.createProject(user, path, "TestCopy");
        } catch (ProjectCreationException | DirectoryCreationException e) {
            fail("Impossible to create project. Check createProject test.");
        } catch (ProjectAlreadyExistsException e){
            /* Project already exists but this is not a problem */
            try {
                project1 = projectHandler.loadProject(path+File.separator+"TestCopy");
                project1.setCreationDate(new Date());
            } catch (ProjectLoadException ex) {
                ex.printStackTrace();
                fail("Impossible to load project");
            }
        }

        user.getProjectPaths().add(project1.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        User user2 = null;
        File fileUser = new File(path+File.separator+".User2.txt");
        if(!fileUser.exists()){
            try{
                user2 = new User();
                user2.setUsername(".User2");
                user2.setFirstName("none");
                user2.setLastName("none");
                user2.setMail("none@none.com");
                user2.setPassword("mdp");
                userHandler.createUserSave(user2);
            }catch(SaveUserCreationException | UserAlreadyExistsException e){
                fail("Impossible to create save of the user. Check createSaveUser test.");
            }
        }else {
            try {
                user2 = userHandler.getUserFromSave(".User2");
            } catch (UserFromSaveCreationException e) {
                fail("Impossible to get user from save. Check getUserFromSave test.");
            }
        }

        try{
            String newCode = "\\begin{document} \n" +
                    "\\end{document}\n";
            File texFile = new File(project1.getPath() + File.separator + project1.getTitle() + ".tex");
            projectHandler.writeInFile(texFile,newCode);
        }catch (IOException e){
            fail("Impossible to write in file. Check writeInFile test.");
        }

        Project project2 = null;
        try {
            project2 = projectHandler.createCopy(project1, user2);
        } catch (ProjectCopyException e) {
            fail("Impossible to copy the project.");
        }

        user2.getProjectPaths().add(project2.getPath());
        try {
            userHandler.saveUser(user2);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        assertEquals(path+File.separator+"TestCopy2", project2.getPath());
        assertEquals("TestCopy2", project2.getTitle());
        assertEquals(".User2", project2.getCreatorUsername());
        assertEquals(new SimpleDateFormat(ProjectHandler.DATE_FORMAT, Locale.ENGLISH).format(project1.getCreationDate()), new SimpleDateFormat(ProjectHandler.DATE_FORMAT, Locale.ENGLISH).format(project2.getCreationDate()));
        assertEquals(project1.getCollaboratorsUsernames(), project2.getCollaboratorsUsernames());

        try{
            assertEquals(projectHandler.readInFile(project1.getPath() + File.separator + project1.getTitle() + ".tex"),projectHandler.readInFile(project2.getPath() + File.separator + project2.getTitle() + ".tex"));
        }catch(IOException e){
            fail("Impossible to read in file. Check readInfile test.");
        }

        try {
            projectHandler.deleteProject(project1);
            projectHandler.deleteProject(project2);
        } catch(ProjectDeletionException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to delete project. Check deleteProject test.");
        }
    }

    @Test
    void deleteProject(){
        String path = TEST_DIRECTORY+File.separator+"DeleteProjectDirectory";
        testProjectHandlerSetup(path);
        Project projectToDelete = null;
        try {
            projectToDelete = projectHandler.createProject(user, path, "TestDelete");
        } catch (ProjectCreationException | DirectoryCreationException e) {
            fail("Impossible to create project. Check createProject test.");
        } catch (ProjectAlreadyExistsException e) {
            /* Project already exists */
            projectToDelete = new Project(user.getUsername(), path+File.separator+"TestDelete", "TestDelete");
        }

        user.getProjectPaths().add(projectToDelete.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        File properties = new File(projectToDelete.getPath() + File.separator + "project.properties");
        File tex = new File(projectToDelete.getPath() + File.separator +projectToDelete.getTitle()+".tex");
        assertTrue(properties.exists());
        assertTrue(tex.exists());

        User userBeforeDelete = null;
        try{
            userBeforeDelete = userHandler.getUserFromSave("alosfeld");
        } catch(UserFromSaveCreationException e){
            fail("Impossible to create user from save. Check UserFromSave test.");
        }

        assertTrue(userBeforeDelete.getProjectPaths().contains(projectToDelete.getPath()));

        try{
            projectHandler.deleteProject(projectToDelete);
        }catch(ProjectDeletionException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to delete project.");
        }

        User userAfterDelete = null;
        try{
            userAfterDelete = userHandler.getUserFromSave("alosfeld");
        } catch(UserFromSaveCreationException e){
            fail("Impossible to create user from save. Check UserFromSave test.");
        }
        System.out.println(projectToDelete.getPath());
        assertFalse(userAfterDelete.getProjectPaths().contains(projectToDelete.getPath()));

        assertFalse(properties.exists());
        assertFalse(tex.exists());
    }

    @Test
    public void makeTexFile(){
        String path = TEST_DIRECTORY+File.separator+"MakeTexFileDirectory";
        testProjectHandlerSetup(path);
        Project project = null;
        try {
            project = projectHandler.createProject(user, path, "TestMakeTexFile");
        } catch (ProjectCreationException | DirectoryCreationException e) {
            fail("Impossible to create project. Check createProject test.");
        } catch (ProjectAlreadyExistsException e){
            /* Project already exists */
            project = new Project(user.getUsername(), path+File.separator+"TestMakeTexFile", "TestMakeTexFile");
        }

        user.getProjectPaths().add(project.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        Session.getInstance().setCurrentProject(project);

        String pathFileTex = project.getPath() + File.separator + project.getTitle() + ".tex";

        String latexTest = "\\begin{document} \n" +
                "\\end{document}\n";

        try {
            projectHandler.makeTexFile(latexTest);
        }catch(LatexWritingException e){
            fail("Impossible to write Tex.");
        }

        try {
            assertEquals(latexTest, projectHandler.readInFile(pathFileTex));
        }catch(IOException e){
            fail("Impossible to read in tex file. Check readInFile test.");
        }

    }

    @Test
    public void shareProject(){
        String path = TEST_DIRECTORY+File.separator+"ShareProjectDirectory";
        testProjectHandlerSetup(path);
        Project projectToShare = null;
        try{
            projectToShare = projectHandler.createProject(user,path,"test");
        } catch (ProjectCreationException | DirectoryCreationException  e){
            e.printStackTrace();
            fail("Impossible to create project. Check createProject test.");
        } catch (ProjectAlreadyExistsException e){
            /* Project Already Exists but this is not a problem */
            try {
                projectToShare = projectHandler.loadProject(path+File.separator+"test");
            } catch (ProjectLoadException ex) {
                fail("Impossible to load project");
            }
        }

        user.getProjectPaths().add(projectToShare.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        File fileUser = new File(path+File.separator+".User2.txt");
        User user2 = new User();
        user2.setUsername(".User2");
        user2.setFirstName("none");
        user2.setLastName("none");
        user2.setMail("none@none.com");
        user2.setPassword("mdp");

        try {
            if (!fileUser.exists()) {
                userHandler.createUserSave(user2);
            } else {
                userHandler.saveUser(user2);
            }
        } catch (SaveUserCreationException | SaveUserException | UserAlreadyExistsException e) {
            fail();
        }

        assertFalse(user2.getProjectPaths().contains(projectToShare.getPath()));

        try {
            projectHandler.shareProject(user2.getUsername(), projectToShare);
        }catch(ProjectSaveException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to share project.");
        }

        try{
            user2 = userHandler.getUserFromSave(".User2");
        }catch(UserFromSaveCreationException e){
            fail("Impossible to get user from save. Check getUserFromSave test.");
        }

        assertTrue(user2.getProjectPaths().contains(projectToShare.getPath()));
        assertTrue(projectToShare.getCollaboratorsUsernames().contains(user2.getUsername()));

        try {
            projectHandler.deleteProject(projectToShare);
        }catch(ProjectDeletionException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to delete project. Check deleteProject test.");
        }

    }

}
