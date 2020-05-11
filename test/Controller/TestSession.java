package Controller;

import Controller.Exceptions.Session.SessionOpeningException;
import Model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class test the session class with some of its methods
 */
class TestSession {
    User testUser;

    public void testSetup() {
        testUser = new User();
        testUser.setUsername("ftrouill");
        testUser.setLastName("Trouillez");
        testUser.setFirstName("Franck");
        testUser.setMail("ftrouill@ulb.ac.be");
        testUser.setPassword("123456789");
        Session.getInstance().setUser(testUser);
    }

    @Test
    public void userNotRegisteredTest(){
        try {
            testSetup();
            int res1 = Session.getInstance().openSessionWithDirectory("ftrouuuuil", "123456789", "./test/Controller/TestSession");
            assertEquals(Session.USER_NOT_REGISTERED, res1);
        } catch (SessionOpeningException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void wrongPasswordTest(){
        try {
            testSetup();
            int res2 = Session.getInstance().openSessionWithDirectory("ftrouill", "oups", "./test/Controller/TestSession");
            assertEquals(Session.INVALID_PASSWORD, res2);
        } catch (SessionOpeningException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void successfulConnectionTest() {
        try {
            testSetup();
            int res3 = Session.getInstance().openSessionWithDirectory("ftrouill", "123456789", "./test/Controller/TestSession");
            assertEquals(Session.CONNECTION_ESTABLISHED, res3);
        } catch (SessionOpeningException e) {
            e.printStackTrace();
            fail();
        }
    }
}
