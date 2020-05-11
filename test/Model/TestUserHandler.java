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

public class TestUserHandler {

    @Test
    public void saveUser() {
        try {
            UserHandler userHandler = UserHandler.getInstance();
            userHandler.setSaveUserDirectory("./test/Model/TestUserHandler/SaveUserDirectory");
            User user1 = new User();
            user1.setUsername("ftrouill");
            user1.setLastName("Trouillez");
            user1.setFirstName("Franck");
            user1.setMail("ftrouill@ulb.ac.be");
            user1.setPassword("123456789");
            try {
                userHandler.createUserSave(user1);
            } catch (UserAlreadyExistsException e){
                /* If the file already exists, just update it */
                userHandler.saveUser(user1);
            }

            String readerTest = userHandler.readInFile("./test/Model/TestUserHandler/SaveUserDirectory/ftrouill.txt");
            assertEquals("last:Trouillez\n" +
                    "first:Franck\n" +
                    "username:ftrouill\n" +
                    "mail:ftrouill@ulb.ac.be\n" +
                    "password:123456789\n" +
                    "projects:\n", readerTest);
        } catch (SaveUserCreationException | IOException | SaveUserException e) {
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

            UserHandler userHandler = UserHandler.getInstance();
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
}