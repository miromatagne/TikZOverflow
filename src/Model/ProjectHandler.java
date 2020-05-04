package Model;

import Controller.Session;
import Model.Exceptions.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;


/**
 * This class is used to handle projects. It uses a singleton design pattern. It allows different
 * actions on projects such as copy, deletion, save, share or renaming.
 */
public class ProjectHandler extends FileHandler{
    public final static String DATE_FORMAT = "E dd-MM-yyyy HH:mm:ss";

    /**
     * Constructor
     */
    public ProjectHandler(){

    }

    /**
     * Create a new project for the user
     *
     * @param user  creator
     * @return      project created
     * @throws ProjectCreationException if creation failed
     */
    public Project createProject(User user, String path,String title) throws ProjectCreationException, DirectoryCreationException, ProjectAlreadyExistsException {
        try {
            Project project = new Project(user.getUsername(), path,title);
            setupProjectDirectory(project.getPath());
            saveProjectInfo(project);
            return project;
        } catch (ProjectSaveException e) {
            throw new ProjectCreationException(e);
        } catch (DirectoryCreationException e) {
            throw new DirectoryCreationException();
        }
    }

    /**
     * Create a new project for the user
     * @param user creator
     * @param title project title
     * @param collaborators collaborators
     * @param code TikZ code
     * @return project created
     * @throws ProjectCreationException if creation failed
     */
    public Project createProject(User user, String title, ArrayList<String> collaborators, String code) throws ProjectCreationException {
        Project project = createProject(user);
        project.setTitle(title);
        project.setCollaboratorsUsernames(collaborators);
        project.setCode(code);
        project.setDate(new Date());
        return project;
    }

    /**
     * Save a project
     *
     * @param project       project to save
     * @throws ProjectSaveException     if save failed
     */
    public void saveProjectInfo(Project project) throws ProjectSaveException {
        try {
            String toWrite = generateSaveFromProject(project);
            String pathProperties = project.getPath() + File.separator +"project.properties";
            writeInFile(new File(pathProperties), toWrite);
        } catch (IOException e) {
            throw new ProjectSaveException(e);
        }

    }

    private void setupProjectDirectory(String path) throws DirectoryCreationException, ProjectAlreadyExistsException {
        File file = new File(path+File.separator+"project.properties");
        if (file.exists()) { //Project with the same path already exists
            throw new ProjectAlreadyExistsException();
        }
        file = new File(path);
        if(file.exists() && file.isDirectory()){ //Path given does not contain project.properties file
            return;
        }
        if (!file.mkdirs()) { //Path given is not a directory yet so we create it
            throw new DirectoryCreationException();
        }
    }

    /**
     * Load a project based on its path
     *
     * @return corresponding project
     * @throws ProjectLoadException     if the load failed
     */
    public Project loadProject(String path) throws ProjectLoadException {
        try {
            String saveText = super.readInFile(new File(path + File.separator+"project.properties"));
            return generateProjectFromSave(saveText, path);
        } catch (IOException | ProjectFromSaveGenerationException e) {
            throw new ProjectLoadException(e);
        }
    }

    /**
     * Create a copy of a project for a user
     *
     * @param projectToCopy     project to copy
     * @param user              creator of the new project
     * @return                  copy of the project
     * @throws ProjectCopyException     if copy failed
     */
    public Project createCopy(Project projectToCopy, User user, String new_path) throws ProjectCopyException, DirectoryCreationException, ProjectAlreadyExistsException {
        try {
            Project projectCopy = createProject(user, new_path,projectToCopy.getTitle());
            return projectCopy;
        } catch (ProjectCreationException e) {
            throw new ProjectCopyException(e);
        }
    }


    /**
     * Delete a project and its save
     *
     * @param project       project to delete
     * @throws ProjectDeletionException if deletion failed
     */
    public void deleteProject(Project project) throws ProjectDeletionException {
        String pathProperties = project.getPath() + File.separator+ "project.properties";
        File file = new File(pathProperties);
        if (!file.delete()){
            throw new ProjectDeletionException();
        }
    }

    /**
     * Generate a save text based on a project
     *
     * @param project   project to be saved
     * @return          save content
     */
    private String generateSaveFromProject(Project project){
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
        toWrite+="title:"+project.getTitle()+ENDLINE;
        toWrite+= "creator:"+project.getCreatorUsername()+ENDLINE;
        toWrite+= "collaborators:";
        String collaborators = String.join(", ", project.getCollaboratorsUsernames());
        toWrite+=collaborators+ENDLINE;
        toWrite+="creation_date:"+new SimpleDateFormat(DATE_FORMAT,Locale.ENGLISH).format(project.getDate())+ENDLINE;
        toWrite+="modification_date:"+new SimpleDateFormat(DATE_FORMAT,Locale.ENGLISH).format(new Date())+ENDLINE;

        return toWrite;
    }

    /**
     * Generate the project from a text save
     *
     * @param saveText  content of the save
     * @param path      path to the project directory
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
            try {
                collaboratorsUsernames = new ArrayList<>(Arrays.asList(allLines[2].split("collaborators:")[1].split(",")));
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Project has no collaborators");
            }
            String dateString = allLines[3].split("creation_date:")[1];
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
            Date date = dateFormatter.parse(dateString);
            return new Project(creatorUsername,
                    title, date, collaboratorsUsernames, path);
        } catch (ParseException e) {
            throw new ProjectFromSaveGenerationException(e);
        }
    }

    public String getProjectCode() throws IOException {
        String filePath = Controller.Session.getInstance().getCurrentProject().getPath()+ Session.getInstance().getCurrentProject().getTitle() + ".tex";
        return super.readInFile(new File(filePath));
    }

    /**
     * Creates a .tex file for every new user, and updates it with the new source code
     * when compiling.
     *
     * @param sourceCode String from the compiling text area
     * @throws LatexWritingException when the text has not be written successfully in the tex file
     */
    public void makeTexFile(String sourceCode) throws LatexWritingException {
        try {
            File texFile = new File(Session.getInstance().getCurrentProject().getPath()+ Session.getInstance().getCurrentProject().getTitle() + ".tex");
            if (texFile.exists()) {
                writeInFile(texFile, sourceCode);
            } else {
                File template_file = new File("./Latex/template.txt");
                String temp, text = "";
                BufferedReader br;
                br = new BufferedReader(new FileReader(template_file));
                while ((temp = br.readLine()) != null) {
                    text = text.concat(temp + '\n');
                }
                writeInFile(texFile, text);
            }
        } catch (IOException e) {
            throw new LatexWritingException(e);
        }
    }
}
