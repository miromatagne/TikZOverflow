package Model;

import Model.Exceptions.LatexErrorsHandler.LogErrorException;
import Model.Exceptions.UserAlreadyExistsException;
import Model.Exceptions.UserHandler.SaveUserCreationException;
import Model.Exceptions.UserHandler.SaveUserException;
import Model.Exceptions.UserHandler.UserFromSaveCreationException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestUserHandler {

    @Test
    public void saveUser() {
        try {
            File userFile;
            if((userFile = new File("./test/Model/TestUserHandler/SaveUserDirectory/ftrouill.txt")).exists()){
                if(!userFile.delete()){
                    fail("Could not delete existing user file");
                }
            }
            UserHandler userHandler = new UserHandler();
            userHandler.setSaveUserDirectory("./test/Model/TestUserHandler/SaveUserDirectory");
            User user1 = new User();
            user1.setUsername("ftrouill");
            user1.setLastName("Trouillez");
            user1.setFirstName("Franck");
            user1.setMail("ftrouill@ulb.ac.be");
            user1.setPassword("123456789");
            userHandler.createUserSave(user1);
            userHandler.saveUser(user1);

            String readerTest = userHandler.readInFile("./test/Model/TestUserHandler/SaveUserDirectory/ftrouill.txt");
            assertEquals("last:Trouillez\n" +
                    "first:Franck\n" +
                    "username:ftrouill\n" +
                    "mail:ftrouill@ulb.ac.be\n" +
                    "password:123456789\n" +
                    "projects:\n", readerTest);
        } catch (SaveUserCreationException | IOException | SaveUserException | UserAlreadyExistsException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getUserFromSave(){
        try {
            /* We first create a save */
            FileHandler fileHandler = new FileHandler();
            String content = "last:Trouillez\n" +
                    "first:Franck\n" +
                    "username:ftrouill\n" +
                    "mail:ftrouill@ulb.ac.be\n" +
                    "password:123456789\n" +
                    "projects:\n";
            fileHandler.writeInFile(new File("./test/Model/TestUserHandler/GetUserFromSaveDirectory/ftrouill.txt"), content);

            /* Now, we check if the getUserFromSave method works */

            UserHandler userHandler = new UserHandler();
            userHandler.setSaveUserDirectory("./test/Model/TestUserHandler/GetUserFromSaveDirectory");

            User user = userHandler.getUserFromSave("ftrouill");
            assertEquals("ftrouill", user.getUsername());
            assertEquals("Franck", user.getFirstName());
            assertEquals("Trouillez", user.getLastName());
            assertEquals("ftrouill@ulb.ac.be", user.getMail());
            assertEquals("123456789", user.getPassword());
        } catch (UserFromSaveCreationException | IOException e) {
            e.printStackTrace();
            fail();
        }

    }



    @Test
    void errorLogs() {
        try {
            User user = new User();
            user.setUsername("logFileTest");
            LatexErrorsHandler latexErrorsHandler = new LatexErrorsHandler();
            latexErrorsHandler.errorLogs("./test/Model/" + user.getUsername() + ".log");
            int errorsCounterTest = 7;
            String errors = "line 1: LaTeX Error: Missing \\begin{document}.\n" +
                    "line 6: Paragraph ended before \\@fileswith@ptions was complete\n" +
                    "line 9: LaTeX Error: Environment tikzpicture undefined.\n" +
                    "line 10: Undefined control sequence.\n" +
                    "line 11: Undefined control sequence.\n" +
                    "line 12: Undefined control sequence.\n" +
                    "line 13: LaTeX Error: \\begin{document} ended by \\end{tikzpictu\n";
            assertEquals(errorsCounterTest, latexErrorsHandler.getErrorsCounter());//check if we have the same number of errors
            assertEquals(errors, latexErrorsHandler.getErrors());//check if we have the same errors
        } catch (LogErrorException e) {
            e.printStackTrace();
            fail();
        }
    }
}