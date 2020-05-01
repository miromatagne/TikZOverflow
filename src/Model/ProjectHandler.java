package Model;

import Model.Exceptions.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * This class is used to handle projects. It uses a singleton design pattern. It allows different
 * actions on projects such as copy, deletion, save, share or renaming.
 */
public class ProjectHandler {
    /**
     * Single instance : singleton
     */
    private int idCounter;
    public final static String PROJECT_DIRECTORY = "projects";
    public final static String DATE_FORMAT = "E dd-MM-yyyy HH:mm:ss";

    /**
     * Constructor
     */
    public ProjectHandler(){

    }


    /**
     * Get a new id (unique identifier)
     *
     * @return      new id
     */
    private int getNewId() {
        /*
        We have to find a way to generate a unique id and not forget to take in account deletions
         */
        return idCounter++;
    }

    /**
     * Create a new project for the user
     *
     * @param user  creator
     * @return      project created
     * @throws ProjectCreationException if creation failed
     */
    public Project createProject(User user, String path,String title) throws ProjectCreationException, DirectoryCreationException {
        try {
            String pathProperties = path + title + ".properties";
            if(isAlreadyProject(pathProperties)) {
                return null;
            }
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

    public boolean isAlreadyProject(String path){
        File file = new File(path);
        if(file.exists()){
            return true;
        }
        return false;
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
            String pathProperties = project.getPath() + project.getTitle() + ".properties";
            writeInFile(new File(pathProperties), toWrite);
        } catch (IOException e) {
            throw new ProjectSaveException(e);
        }

    }

    private void setupProjectDirectory(String path) throws DirectoryCreationException {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return;
        }
        if (!file.mkdir()) {
            throw new DirectoryCreationException();
        }
    }

    /**
     * Load a project based on its id
     *
     * @return      corresponding project
     * @throws ProjectLoadException     if the load failed
     */
    public Project loadProject(String path) throws ProjectLoadException {
        try {
            String saveText = readInFile(new File(path));
            return generateProjectFromSave(saveText);
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
    public Project createCopy(Project projectToCopy, User user, String new_path) throws ProjectCopyException, DirectoryCreationException {
        try {
            Project projectCopy = createProject(user, new_path,projectToCopy.getTitle());
            projectCopy.setCode(projectToCopy.getCode());
            return projectCopy;
        } catch (ProjectCreationException e) {
            throw new ProjectCopyException(e);
        } catch (DirectoryCreationException e) {
            throw new DirectoryCreationException();
        }
    }


    /**
     * Delete a project and its save
     *
     * @param project       project to delete
     * @throws ProjectDeletionException if deletion failed
     */
    public void deleteProject(Project project) throws ProjectDeletionException {
        String pathProperties = project.getPath() + project.getTitle() + ".properties";
        File file = new File(pathProperties);
        if (!file.delete()){
            throw new ProjectDeletionException();
        }
    }

    /**
     * Share a project with a given user
     *
     * @param project       project to share
     * @param user          new collaborator to add to the project
     */
    public void shareProject(Project project, User user){
        project.addCollaborator(user.getUsername());
    }


    /**
     * Rename the project given in parameter
     *
     * @param project   project to rename
     * @param newTitle  new title
     */
    public void renameProject(Project project, String newTitle) {
        project.setTitle(newTitle);
    }

    /**
     * Generate the path to the project based on its id
     *
     * @param id    project id
     * @return      path
     */
    private String generateProjectInfoPath(int id) {
        return PROJECT_DIRECTORY+"/"+id;
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
        modification_date: TODO
        path:
         */
        String toWrite = "";
        final String ENDLINE = "\n";
        toWrite+="title:"+project.getTitle()+ENDLINE;
        toWrite+= "creator:"+project.getCreatorUsername()+ENDLINE;
        toWrite+= "collaborators:" + " ";
        String collaborators = project.getCollaboratorsUsernames().stream().collect(Collectors.joining(", "));
        toWrite+=collaborators+ENDLINE;
        toWrite+="creation_date:"+new SimpleDateFormat(DATE_FORMAT).format(project.getDate())+ENDLINE;
        toWrite+="modification_date:"+ENDLINE;
        toWrite+="path:"+project.getPath()+ENDLINE;

        return toWrite;
    }

    /**
     * Generate the project from a text save
     *
     * @param saveText  content of the save
     * @return project created
     * @throws ProjectFromSaveGenerationException if the parsing for the date failed
     */
    private Project generateProjectFromSave(String saveText) throws ProjectFromSaveGenerationException {
        /* PROJECT INFO FILE FORMAT
        title:
        creator:
        collaborators:c1,c2,c3,...
        creation_date:
        modification_date: TODO
        path:
         */
        try {
            String[] allLines = saveText.split("\n");
            String title = allLines[0].split("title:")[1];
            String creatorUsername = allLines[1].split("creator:")[1];
            //collaborators //TODO : collab quand champ vide => array de taille null => que faire ?
            //ArrayList<String> collaboratorsUsernames = (ArrayList<String>) Arrays.asList(allLines[2].split("collaborators:")[1].split(","));
            String dateString = allLines[3].split("creation_date:")[1];
            //TODO : modification date
            String path = allLines[5].split("path:")[1];
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
            Date date = dateFormatter.parse(dateString);
            return new Project(creatorUsername,
                    title, date, new ArrayList<String>(), path);
        } catch (ParseException e) {
            throw new ProjectFromSaveGenerationException(e);
        }
    }


    /**
     * Writes text into file.
     *
     * @param file File written
     * @param text Content to write
     * @throws IOException when a IO error occurs
     */
    private void writeInFile(File file, String text) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(text);
        bw.close();
    }


    /**
     * Read the text in a File
     *
     * @param file file.
     * @return text in the file
     * @throws IOException if error in IO interactions
     */
    private String readInFile(File file) throws IOException {
        String textToRead;
        StringBuilder builder = new StringBuilder();

        FileReader reader = new FileReader(file);
        BufferedReader buffer = new BufferedReader(reader);
        while ((textToRead = buffer.readLine()) != null) {
            builder.append(textToRead).append("\n");
        }

        return builder.toString();
    }
}
