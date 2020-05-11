package Controller;

import Controller.Exceptions.SessionOpeningException;
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
    }
    @Test
    public void userNotRegisteredTest(){
        try {
            testSetup();
            int res1 = Session.getInstance().openSession("ftrouuuuil", "123456789");
            assertEquals(res1, Session.USER_NOT_REGISTERED);
        } catch (SessionOpeningException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void wrongPasswordTest(){
        try {
            testSetup();
            int res2 = Session.getInstance().openSession("ftrouill", "oups");
            assertEquals(res2, Session.INVALID_PASSWORD);
        } catch (SessionOpeningException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void successfulConnectionTest() {
        try {
            testSetup();
            int res3 = Session.getInstance().openSession("ftrouill", "123456789");
            assertEquals(res3, Session.CONNECTION_ESTABLISHED);
        } catch (SessionOpeningException e) {
            e.printStackTrace();
            fail();
        }
    }
}
