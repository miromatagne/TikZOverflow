package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
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
        System.out.println(fh.createUserSave(testUser));
    }
    @Test
    public void userNotRegisteredTest(){
        testSetup();
        Session session = new Session();
        int res1 = session.openSession("ftrouuuuil", "123456789");
        assertEquals(res1, Session.USER_NOT_REGISTERED);
    }

    @Test
    public void wrongPasswordTest(){
        testSetup();
        Session session = new Session();
        int res2 = session.openSession("ftrouill", "oups");
        assertEquals(res2, Session.INVALID_PASSWORD);
    }

    @Test
    public void successfulConnectionTest(){
        testSetup();
        Session session = new Session();
        int res3 = session.openSession("ftrouill", "123456789");
        assertEquals(res3, Session.CONNECTION_ESTABLISHED);
    }
}