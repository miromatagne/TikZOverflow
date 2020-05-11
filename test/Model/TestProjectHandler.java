package Model;

import Controller.Session;
import Model.Exceptions.*;
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
    private final String path = "./test/projecthandler/";


    public TestProjectHandler(){
        userHandler = new UserHandler();
        projectHandler = new ProjectHandler();
        user = new User();
        user.setFirstName("Armand");
        user.setLastName("Losfeld");
        user.setMail("armand.losfeld@ulb.ac.be");
        user.setPassword("mdp");
        user.setUsername("alosfeld");
        try {
            userHandler.createUserSave(user);
        }catch(SaveUserCreationException e){
            fail("Impossible to create save of the user. Check saveCreatorUser test.");
        }

        Session.getInstance().setUser(user);
    }

    @Test
    public void createProject(){
        Project projectToCreate = null;
        try{
            projectToCreate = projectHandler.createProject(user,path,"test");
        } catch (ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException | LatexWritingException e){
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
        String dateString = dateFormatter.format(projectToSave.getDate());
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
        try{
            projectHandler.deleteProject(projectToSave);
        }catch(ProjectDeletionException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to delete project. Check deleteProject test.");
        }
    }

    @Test
    public void renameProject(){
        Project projectToRename = null;
        try{
            projectToRename = projectHandler.createProject(user,path,"test");
        } catch (ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException | LatexWritingException e){
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

        try{
            projectHandler.renameProject(projectToRename,"testRename");
        }catch(ProjectRenameException e){
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
        Project project = null;
        try {
            project = projectHandler.createProject(user, path, "test");
        }catch(ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException | LatexWritingException e){
            fail("Impossible to create project. Check createProject test.");
        }

        user.getProjectPaths().add(project.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        Project loadProject = null;
        try{
            loadProject = projectHandler.loadProject(user.getProjectPaths().get(0));
        }catch(ProjectLoadException e){
            fail("Impossible to load project.");
        }

        assertEquals(project.getPath(),loadProject.getPath());
        assertEquals(project.getTitle(), loadProject.getTitle());
        assertEquals(project.getCreatorUsername(),loadProject.getCreatorUsername());
        assertEquals(new SimpleDateFormat(ProjectHandler.DATE_FORMAT,Locale.ENGLISH).format(project.getDate()),new SimpleDateFormat(ProjectHandler.DATE_FORMAT,Locale.ENGLISH).format(loadProject.getDate()));
        assertEquals(project.getCollaboratorsUsernames(),loadProject.getCollaboratorsUsernames());

        try{
            projectHandler.deleteProject(project);
        }catch(ProjectDeletionException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to delete project.Check deleteProject test.");
        }
    }

    @Test
    public void createCopy() {
        Project project1 = null;
        try {
            project1 = projectHandler.createProject(user, path, "TestCopy");
        } catch (ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException | LatexWritingException e) {
            fail("Impossible to create project. Check createProject test.");
        }

        user.getProjectPaths().add(project1.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        User user2 = null;
        File fileUser = new File("./save user/.User2.txt");
        if(!fileUser.exists()){
            try{
                user2 = new User();
                user2.setUsername(".User2");
                user2.setFirstName("none");
                user2.setLastName("none");
                user2.setMail("none@none.com");
                user2.setPassword("mdp");
                userHandler.createUserSave(user2);
            }catch(SaveUserCreationException e){
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
        assertEquals(new SimpleDateFormat(ProjectHandler.DATE_FORMAT, Locale.ENGLISH).format(project1.getDate()), new SimpleDateFormat(ProjectHandler.DATE_FORMAT, Locale.ENGLISH).format(project2.getDate()));
        assertEquals(project1.getCollaboratorsUsernames(), project2.getCollaboratorsUsernames());

        try{
            assertEquals(projectHandler.readInFile(project1.getPath() + File.separator + project1.getTitle() + ".tex"),projectHandler.readInFile(project2.getPath() + File.separator + project2.getTitle() + ".tex"));
        }catch(IOException e){
            fail("Impossible to read in file. Check readInfile test.");
        }

        try {
            projectHandler.deleteProject(project1);
            projectHandler.deleteProject(project2);
        }catch(ProjectDeletionException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to delete project. Check deleteProject test.");
        }
    }

    @Test
    void deleteProject(){
        Project projectToDelete = null;
        try {
            projectToDelete = projectHandler.createProject(user, path, "TestDelete");
        } catch (ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException | LatexWritingException e) {
            fail("Impossible to create project. Check createProject test.");
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

        assertFalse(userAfterDelete.getProjectPaths().contains(projectToDelete.getPath()));

        assertFalse(properties.exists());
        assertFalse(tex.exists());
    }

    @Test
    public void makeTexFile(){
        Project project = null;
        try {
            project = projectHandler.createProject(user, path, "TestMakeTexFile");
        } catch (ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException | LatexWritingException e) {
            fail("Impossible to create project. Check createProject test.");
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

        try{
            projectHandler.deleteProject(project);
        }catch(ProjectDeletionException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to delete project. Check deleteProject test.");
        }
    }

    @Test
    public void shareProject(){
        Project projectToShare = null;
        try{
            projectToShare = projectHandler.createProject(user,path,"test");
        } catch (ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException | LatexWritingException e){
            fail("Impossible to create project. Check createProject test.");
        }

        user.getProjectPaths().add(projectToShare.getPath());
        try {
            userHandler.saveUser(user);
        } catch(SaveUserException e){
            fail("Impossible to save user. Check saveUser test.");
        }

        User user2 = null;
        File fileUser = new File("./save user/.User2.txt");
        if(!fileUser.exists()){
            try{
                user2 = new User();
                user2.setUsername(".User2");
                user2.setFirstName("none");
                user2.setLastName("none");
                user2.setMail("none@none.com");
                user2.setPassword("mdp");
                userHandler.createUserSave(user2);
            }catch(SaveUserCreationException e){
                fail("Impossible to create save of the user. Check createSaveUser test.");
            }
        }else{
            try{
                user2 = userHandler.getUserFromSave(".User2");
            }catch(UserFromSaveCreationException e){
                fail("Impossible to get user from save. Check getUserFromSave test.");
            }
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

        try{
            projectHandler.deleteProject(projectToShare);
        }catch(ProjectDeletionException | SaveUserException | UserFromSaveCreationException e){
            fail("Impossible to delete project. Check deleteProject test.");
        }
    }

}
