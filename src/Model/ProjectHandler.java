package Model;

import Controller.Session;
import Model.Exceptions.DirectoryCreationException;
import Model.Exceptions.ProjectHandler.*;
import Model.Exceptions.UserHandler.SaveUserException;
import Model.Exceptions.UserHandler.UserFromSaveCreationException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;


/**
 * This class is used to handle projects. It uses a singleton design pattern. It allows different
 * actions on projects such as copy, deletion, save, share or renaming.
 */
public class ProjectHandler extends FileHandler {
    public final static String DATE_FORMAT = "E dd-MM-yyyy HH:mm:ss";

    /**
     * Constructor
     */
    public ProjectHandler() {

    }

    /**
     * Create a new project for the user
     *
     * @param user creator
     * @return project created
     * @throws ProjectCreationException if creation failed
     */
    public Project createProject(User user, String path, String title) throws ProjectCreationException, DirectoryCreationException, ProjectAlreadyExistsException {
        try {
            Project project = new Project(user.getUsername(), path, title);
            setupProjectDirectory(project);
            saveProjectInfo(project);
            generateTexFile(project);
            return project;
        } catch (ProjectSaveException | IOException e) {
            throw new ProjectCreationException(e);
        } catch (DirectoryCreationException e) {
            throw new DirectoryCreationException();
        }
    }

    /**
     * Save a project
     *
     * @param project project to save
     * @throws ProjectSaveException if save failed
     */
    public void saveProjectInfo(Project project) throws ProjectSaveException {
        try {
            String toWrite = generateSaveFromProject(project);
            String pathProperties = project.getPath() + File.separator + "project.properties";
            writeInFile(new File(pathProperties), toWrite);
        } catch (IOException e) {
            throw new ProjectSaveException(e);
        }

    }

    /**
     * Setup the project directory
     *
     * @param project                           project which has to get a directory
     * @throws DirectoryCreationException       If the directory creation failed
     * @throws ProjectAlreadyExistsException    if the project already exists
     */
    private void setupProjectDirectory(Project project) throws DirectoryCreationException, ProjectAlreadyExistsException {
        String projectDirectoryPath = project.getPath() + File.separator + project.getTitle();
        File file = new File(projectDirectoryPath);
        if (file.exists()) { //Project directory with the same path already exists
            throw new ProjectAlreadyExistsException();
        }
        if (!file.mkdirs()) { //Path given is not a directory yet so we create it
            throw new DirectoryCreationException();
        }
        project.setPath(projectDirectoryPath);
    }

    /**
     * Load a project based on its path
     *
     * @return corresponding project
     * @throws ProjectLoadException if the load failed
     */
    public Project loadProject(String path) throws ProjectLoadException {
        try {
            String saveText = super.readInFile(path + File.separator + "project.properties");
            return generateProjectFromSave(saveText, path);
        } catch (IOException | ProjectFromSaveGenerationException e) {
            throw new ProjectLoadException(e);
        }
    }

    /**
     * Create a copy of a project for a user
     *
     * @param projectToCopy project to copy
     * @param user          creator of the new project
     * @return copy of the project
     * @throws ProjectCopyException if copy failed
     */
    public Project createCopy(Project projectToCopy, User user) throws ProjectCopyException {
        int firstAvailableIndex = findNewPathIndex(projectToCopy);
        try {
            int lastSeparatorPosition = projectToCopy.getPath().lastIndexOf(File.separator);
            String rootProjectPath = projectToCopy.getPath().substring(0,lastSeparatorPosition);

            Project project = createProject(user, rootProjectPath, projectToCopy.getTitle()+firstAvailableIndex);
            File texFile = new File(project.getPath() + File.separator + project.getTitle() + ".tex");
            writeInFile(texFile,super.readInFile(projectToCopy.getPath() + File.separator + projectToCopy.getTitle() + ".tex"));
            return project;
        } catch (ProjectCreationException | DirectoryCreationException | ProjectAlreadyExistsException | IOException e) {
            throw new ProjectCopyException(e);
        }
    }

    /**
     * Find next available name to copy a project.
     * @param projectToCopy project to copy
     * @return first available index that will be appended to project to copy title
     */
    private int findNewPathIndex(Project projectToCopy) {
        int index = 1;
        File currentDirectory;
        do {
            index++;
            int lastSeparatorPosition = projectToCopy.getPath().lastIndexOf(File.separator);
            String rootProjectPath = projectToCopy.getPath().substring(0,lastSeparatorPosition);
            currentDirectory = new File(rootProjectPath + File.separator + projectToCopy.getTitle() + index);
        } while (currentDirectory.exists());
        return index;
    }


    /**
     * Delete a project and its save
     *
     * @param project project to delete
     * @throws ProjectDeletionException if deletion failed
     */
    public void deleteProject(Project project) throws ProjectDeletionException {
        try {
            String pathProperties = project.getPath();
            ArrayList<String> collaboratorsUsernames = project.getCollaboratorsUsernames();
            String creatorUsername = project.getCreatorUsername();
            UserHandler userHandler = UserHandler.getInstance();
            File file = new File(pathProperties);
            super.deleteDirectory(file);
            deleteFromUser(project, creatorUsername, userHandler);
            for(String collaboratorUsername : collaboratorsUsernames){
                deleteFromUser(project, collaboratorUsername, userHandler);
            }
        } catch (IOException | SaveUserException | UserFromSaveCreationException e) {
            throw new ProjectDeletionException();
        }
    }

    /**
     * Removes a project from a user file.
     * @param project project to be removed
     * @param creatorUsername project creator username
     * @param userHandler UserHandler
     * @throws UserFromSaveCreationException if user file could not be read
     * @throws SaveUserException if user file could not be saved
     */
    private void deleteFromUser(Project project, String creatorUsername, UserHandler userHandler) throws UserFromSaveCreationException, SaveUserException {
        User creator = userHandler.getUserFromSave(creatorUsername);
        creator.removeProject(project.getPath());
        userHandler.saveUser(creator);
    }

    /**
     * Renames a project title.
     * Changes the name of the LaTeX file but not of the project directory.
     * @param project project to be renamed
     * @param newTitle new project title
     * @throws ProjectRenameException if project could not be saved
     */
    public void renameProject(Project project, String newTitle) throws ProjectRenameException {
        File projectFile = new File(project.getPath() + File.separator + project.getTitle() + ".tex");
        boolean success = projectFile.renameTo(new File(project.getPath() + File.separator + newTitle + ".tex"));
        if (!success) {
            throw new ProjectRenameException();
        }
        File projectDirectory = new File(project.getPath());
        int lastSeparatorPosition = project.getPath().lastIndexOf(File.separator);
        String rootProjectPath = project.getPath().substring(0,lastSeparatorPosition);
        success = projectDirectory.renameTo(new File(rootProjectPath + File.separator + newTitle));
        if (!success) {
            throw new ProjectRenameException();
        }
        String previousTitle = project.getTitle();
        String previousPath = project.getPath();
        project.setPath(rootProjectPath + File.separator+newTitle);
        project.setTitle(newTitle);
        User user = Session.getInstance().getUser();
        user.removeProject(previousPath);
        user.addProject(rootProjectPath + File.separator+newTitle);
        UserHandler userHandler = UserHandler.getInstance();
        try {
            User creator = userHandler.getUserFromSave(project.getCreatorUsername());
            creator.removeProject(previousPath);
            creator.addProject(rootProjectPath + File.separator + newTitle);
            userHandler.saveUser(creator);
            for(String collaboratorUsername : project.getCollaboratorsUsernames()) {
                User collaborator = userHandler.getUserFromSave(collaboratorUsername);
                collaborator.removeProject(previousPath);
                collaborator.addProject(rootProjectPath + File.separator + newTitle);
                userHandler.saveUser(collaborator);
            }
            saveProjectInfo(project);
        } catch (ProjectSaveException | UserFromSaveCreationException | SaveUserException e) {
            project.setTitle(previousTitle);
            project.setPath(previousPath);
            user.removeProject(previousPath);
            throw new ProjectRenameException();
        }
    }

    /**
     * Shares a project with given collaborator
     * @param collaboratorUsername username of the collaborator the project is shared with
     * @param project project to be shared
     * @throws UserFromSaveCreationException if collaborator file couldn't be read
     * @throws ProjectSaveException if project file couldn't be saved
     * @throws SaveUserException if collaborator file couldn't be saved
     */
    public void shareProject(String collaboratorUsername, Project project) throws UserFromSaveCreationException, ProjectSaveException, SaveUserException {
        UserHandler userHandler = UserHandler.getInstance();
        User collaborator = userHandler.getUserFromSave(collaboratorUsername);
        project.addCollaborator(collaboratorUsername);
        collaborator.addProject(project.getPath());
        saveProjectInfo(project);
        userHandler.saveUser(collaborator);
    }

    /**
     * Generate a save text based on a project
     *
     * @param project project to be saved
     * @return save content
     */
    private String generateSaveFromProject(Project project) {
        /* PROJECT INFO FILE FORMAT
        title:
        creator:
        collaborators:c1,c2,c3,...
        title:
        creation_date:
        modification_date:
        path:
         */
        String toWrite = "";
        final String ENDLINE = "\n";
        toWrite += "title:" + project.getTitle() + ENDLINE;
        toWrite += "creator:" + project.getCreatorUsername() + ENDLINE;
        toWrite += "collaborators:";
        String collaborators = String.join(", ", project.getCollaboratorsUsernames());
        toWrite += collaborators + ENDLINE;
        toWrite += "creation_date:" + new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(project.getCreationDate()) + ENDLINE;
        toWrite += "modification_date:" + new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(new Date()) + ENDLINE;

        return toWrite;
    }

    /**
     * Generate the project from a text save
     *
     * @param saveText content of the save
     * @param path     path to the project directory
     * @return project created
     * @throws ProjectFromSaveGenerationException if the parsing for the date failed
     */
    private Project generateProjectFromSave(String saveText, String path) throws ProjectFromSaveGenerationException {
        /* PROJECT INFO FILE FORMAT
        title:
        creator:
        collaborators:c1,c2,c3,...
        creation_date:
        modification_date:
        path:
         */
        try {
            String[] allLines = saveText.split("\n");
            String title = allLines[0].split("title:")[1];
            String creatorUsername = allLines[1].split("creator:")[1];
            ArrayList<String> collaboratorsUsernames = new ArrayList<>();
            ArrayList<String> tempCollaborators = new ArrayList<>(Arrays.asList(allLines[2].split("collaborators:")));
            if (tempCollaborators.size() > 1){
                collaboratorsUsernames = new ArrayList<>(Arrays.asList(tempCollaborators.get(1).split(",")));
            }
            String creationDateString = allLines[3].split("creation_date:")[1];
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
            Date creationDate = dateFormatter.parse(creationDateString);
            String lastModificationDateString = allLines[4].split("modification_date:")[1];
            Date lastModificationDate = dateFormatter.parse(lastModificationDateString);
            return new Project(creatorUsername,
                    title, creationDate, lastModificationDate, collaboratorsUsernames, path);
        } catch (ParseException e) {
            throw new ProjectFromSaveGenerationException(e);
        }
    }

    /**
     * Reads project code in LaTeX file.
     * @return source code
     * @throws IOException if project LaTeX file could not be read
     */
    public String getProjectCode() throws IOException {
        String filePath = Controller.Session.getInstance().getCurrentProject().getPath() + File.separator + Session.getInstance().getCurrentProject().getTitle() + ".tex";
        return super.readInFile(filePath);
    }

    /**
     * Creates a .tex file for every new user, and updates it with the new source code
     * when compiling.
     *
     * @param sourceCode String from the compiling text area
     * @throws LatexWritingException when the text has not be written successfully in the tex file
     */
    public void makeTexFile(String sourceCode) throws LatexWritingException {
        try (InputStream inputStream = getClass().getResourceAsStream("/template.txt")){
            File texFile = new File(Session.getInstance().getCurrentProject().getPath() + File.separator + Session.getInstance().getCurrentProject().getTitle() + ".tex");
            if (texFile.exists()) {
                writeInFile(texFile, sourceCode);
            } else {
                String temp, text = "";
                BufferedReader br;
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((temp = br.readLine()) != null) {
                    text = text.concat(temp + '\n');
                }
                writeInFile(texFile, text);
            }
        } catch (IOException e) {
            throw new LatexWritingException(e);
        }
    }

    /**
     * Writes template code in project LaTeX file.
     * @param project project to be saved
     * @throws IOException if project file couldn't be read/written
     */
    public void generateTexFile(Project project) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/template.txt");
        File texFile = new File(project.getPath() + File.separator + project.getTitle() + ".tex");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String temp, text = "";
            while ((temp = br.readLine()) != null) {
                text = text.concat(temp + '\n');
            }
            writeInFile(texFile, text);
        }
    }
}
