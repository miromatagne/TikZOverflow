package Model;

import Model.Exceptions.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class is used to handle projects. It uses a singleton design pattern. It allows different
 * actions on projects such as copy, deletion, save, share or renaming.
 */
public class ProjectHandler {
    /**
     * Single instance : singleton
     */
    private static ProjectHandler instance;

    private int idCounter;
    private final static String PROJECT_DIRECTORY = "projects";
    public final static String DATE_FORMAT = "E dd-MM-yyyy HH:mm:ss";

    /**
     * Private constructor  : singleton
     */
    private ProjectHandler(){

    }

    /**
     * Instance getter : singleton
     */
    public static ProjectHandler getInstance(){
        if (instance == null){
            instance = new ProjectHandler();
        }
        return instance;
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
    public Project createProject(User user) throws ProjectCreationException {
        try {
            int id = getNewId();
            Project project = new Project(id, user.getUsername());
            saveProject(project);
            return project;
        } catch (ProjectSaveException e) {
            throw new ProjectCreationException(e);
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
    public void saveProject(Project project) throws ProjectSaveException {
        try {
            String toWrite = generateSaveFromProject(project);
            writeInFile(new File(generatePath(project.getID())), toWrite);
        } catch (IOException e) {
            throw new ProjectSaveException(e);
        }

    }

    /**
     * Load a project based on its id
     *
     * @param id    id of the project
     * @return      corresponding project
     * @throws ProjectLoadException     if the load failed
     */
    public Project loadProject(int id) throws ProjectLoadException {
        try {
            String saveText = readInFile(new File(generatePath(id)));
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
    public Project createCopy(Project projectToCopy, User user) throws ProjectCopyException{
        try {
            Project projectCopy = createProject(user);
            projectCopy.setTitle(projectToCopy.getTitle());
            projectCopy.setCode(projectToCopy.getCode());
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
        File file = new File(generatePath(project.getID()));
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
    private String generatePath(int id) {
        return PROJECT_DIRECTORY+"/"+id;
    }

    /**
     * Generate a save text based on a project
     *
     * @param project   project to be saved
     * @return          save content
     */
    private String generateSaveFromProject(Project project){
        /*
        Choisir le bon format de la save du projet
        Exemple : Nom du file : id.txt
        """
        #ID
        #Username Creator
        #Titre
        #Date
        #Collaborator 1
        #Collaborator 2
        #...
        CODE
        """
         */
        String toWrite;
        final String ENDLINE = "\n";

        String idPart ="#"+project.getID()+ENDLINE;
        String creatorPart = "#"+project.getCreatorUsername()+ENDLINE;
        String titlePart = "#"+project.getTitle()+ENDLINE;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        String datePart = "#"+dateFormatter.format(project.getDate())+ENDLINE;
        String singleCollaboratorPart;
        StringBuilder collaboratorsPart = new StringBuilder();
        for (String collaboratorUsername : project.getCollaboratorsUsernames()){
            singleCollaboratorPart = "#"+collaboratorUsername+ENDLINE;
            collaboratorsPart.append(singleCollaboratorPart);
        }
        String codePart = project.getCode();

        toWrite = idPart + creatorPart + titlePart + datePart + collaboratorsPart + codePart;
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
        /*
        Choisir le bon format de la save du projet
        Exemple : Nom du file : id.txt
        """
        #ID
        #Username Creator
        #Titre
        #Date
        #Collaborator 1
        #Collaborator 2
        #...
        CODE
        """
         */
        try {
            String[] allLines = saveText.split("\n");
            String idString = allLines[0].substring(1);
            String creatorUsername = allLines[1].substring(1);
            String title = allLines[2].substring(1);
            String dateString = allLines[3].substring(1);
            ArrayList<String> collaboratorsUsernames = new ArrayList<>();
            StringBuilder code = new StringBuilder();
            for (int i = 4; i < allLines.length; i++){
                if (allLines[i].charAt(0) == '#'){
                    collaboratorsUsernames.add(allLines[i].substring(1));
                }
                else {
                    code.append(allLines[i]);
                    if (i < allLines.length - 1){
                        code.append("\n");
                    }
                }
            }
            int id = Integer.parseInt(idString);
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
            Date date = dateFormatter.parse(dateString);
            return new Project(id, creatorUsername,
                    title, date, collaboratorsUsernames, code.toString());
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
