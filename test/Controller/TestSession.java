package Controller;

import Controller.Exceptions.SessionOpeningException;
import Model.FileHandler;
import Model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestSession {
    User testUser;


    @Test
    public void testSetup(){
        FileHandler fh = new FileHandler();
        fh.setupSaveUserDirectory("save user");
        testUser = new User();
        testUser.setUsername("ftrouill");
        testUser.setLastName("Trouillez");
        testUser.setFirstName("Franck");
        testUser.setMail("ftrouill@ulb.ac.be");
        testUser.setPassword("123456789");
    }
    @Test
    public void userNotRegisteredTest() throws SessionOpeningException {
        testSetup();
        int res1 = Session.getInstance().openSession("ftrouuuuil", "123456789");
        assertEquals(res1, Session.USER_NOT_REGISTERED);
    }

    @Test
    public void wrongPasswordTest() throws SessionOpeningException {
        testSetup();
        int res2 = Session.getInstance().openSession("ftrouill", "oups");
        assertEquals(res2, Session.INVALID_PASSWORD);
    }

    @Test
    public void successfulConnectionTest() throws SessionOpeningException {
        testSetup();
        int res3 = Session.getInstance().openSession("ftrouill", "123456789");
        assertEquals(res3, Session.CONNECTION_ESTABLISHED);
    }
}
