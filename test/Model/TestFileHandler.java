package Model;

import Controller.Session;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestFileHandler {

    @Test
    public void setupSaveUserDirectory(){
        FileHandler fh = new FileHandler();
        if (fh.setupSaveUserDirectory("save user")){
            System.out.println("OK");
        }
        else {
            System.out.println("KO");
        }
        //Check if the directory is made (if not already made)
    }


    @Test
    public void test() {
        /* After execution, a folder should be made containing
         * a save file of "ftrouill" user
         */
        FileHandler fh = new FileHandler();
        fh.setupSaveUserDirectory("save user");
        User user1 = new User();
        user1.setUsername("ftrouill");
        user1.setLastName("Trouillez");
        user1.setFirstName("Franck");
        user1.setMail("ftrouill@ulb.ac.be");
        user1.setPassword("123456789");
        System.out.println(fh.createUserSave(user1)); //Indicate if the save was already existing
        User user2 = fh.getUserFromSave("ftrouill");
        assertEquals("ftrouill", user2.getUsername());
        assertEquals("Franck", user2.getFirstName());
        assertEquals("Trouillez", user2.getLastName());
        assertEquals("ftrouill@ulb.ac.be", user2.getMail());
        assertEquals("123456789", user2.getPassword());

        String readerTest = fh.readInFile("./save user/ftrouill.txt");
        assertEquals("last:Trouillez" + "\n" + "first:Franck" + "\n" + "username:ftrouill" + "\n" +
                              "mail:ftrouill@ulb.ac.be" + "\n" + "password:123456789" + "\n", readerTest);
    }
    @Test
    public void makeTexFile(){
        User user = new User();
        user.setUsername("test1");
        FileHandler fh = new FileHandler();
        fh.setupSaveUserDirectory("save user");
        fh.createUserSave(user);
        File texFile = new File("./Latex/" + user.getUsername() + ".tex");
        fh.writeInFile(texFile, "");
        fh.makeTexFile(user, "testing");
        assertTrue(texFile.exists());
        String temp, text = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(texFile));
            while ((temp = br.readLine()) != null) {
                text = text.concat(temp);
            }
        } catch (IOException e) {
            System.err.println("There was an error while reading the sample file\n");
            e.printStackTrace();
        }
        assertEquals(text, "testing");
    }

    @Test
    void errorLogs() throws IOException {
        User user = new User();
        user.setUsername("logFileTest");
        FileHandler fileHandler = new FileHandler();
        fileHandler.errorLogs("./test/Model/" + user.getUsername() + ".log", user.getUsername());
        int errorsCounterTest = 7;
        String errors = "line 1: LaTeX Error: Missing \\begin{document}.\n" +
                "line 6: Paragraph ended before \\@fileswith@ptions was complete\n" +
                "line 9: LaTeX Error: Environment tikzpicture undefined.\n" +
                "line 10: Undefined control sequence.\n" +
                "line 11: Undefined control sequence.\n" +
                "line 12: Undefined control sequence.\n" +
                "line 13: LaTeX Error: \\begin{document} ended by \\end{tikzpictu\n";
        assertEquals(errorsCounterTest, fileHandler.getErrorsCounter());//check if we have the same number of errors
        assertEquals(errors, fileHandler.getErrors());//check if we have the same errors
    }
}