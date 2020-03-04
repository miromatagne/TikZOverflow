package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldCheckerTest {

    @Test
    void setupFieldChecker() {
        FieldChecker fc = new FieldChecker();
        fc.setupFieldChecker();
        assertTrue(fc.userCharCollection.contains('a'));
        assertTrue(fc.userCharCollection.contains('0'));
        assertTrue(fc.userCharCollection.contains('a'));
        assertTrue(fc.userCharCollection.contains('Z'));
        assertTrue(fc.userCharCollection.contains('B'));
        assertFalse(fc.userCharCollection.contains(' '));
        assertFalse(fc.userCharCollection.contains('*'));
        assertTrue(fc.alphaCharCollection.contains('Z'));
        assertTrue(fc.alphaCharCollection.contains('e'));
        assertFalse(fc.alphaCharCollection.contains('8'));
        assertFalse(fc.alphaCharCollection.contains('_'));
        assertFalse(fc.alphaCharCollection.contains(' '));
        assertTrue(fc.numericCharCollection.contains('0'));
        assertTrue(fc.numericCharCollection.contains('9'));
        assertFalse(fc.numericCharCollection.contains('w'));
    }

    @Test
    void isValidUsername() {
        FieldChecker fc = new FieldChecker();
        fc.setupFieldChecker();
        assertTrue(fc.isValidUsername("ftrouill"));
        assertTrue(fc.isValidUsername("LeBGdu65"));
        assertTrue(fc.isValidUsername("Olababy85yo"));
        assertFalse(fc.isValidUsername("Franck TROUILLEZ"));
        assertFalse(fc.isValidUsername("Franck?"));
    }

    @Test
    void isValidName() {
        FieldChecker fc = new FieldChecker();
        fc.setupFieldChecker();
        assertTrue(fc.isValidName("ftrouill"));
        assertFalse(fc.isValidName("LeBGdu65"));
        assertFalse(fc.isValidName("Olababy85yo"));
        assertTrue(fc.isValidName("Franck TROUILLEZ"));
        assertTrue(fc.isValidName("Franck-Einstein"));
    }

    @Test
    void isValidMail() {
        FieldChecker fc = new FieldChecker();
        fc.setupFieldChecker();
        assertTrue(fc.isValidMail("ftrouill@ulb.ac.be"));
        assertFalse(fc.isValidMail("ftrouill.ulb.ac.be"));
        assertFalse(fc.isValidMail("ftrouill@ulb@ac.be"));
        assertFalse(fc.isValidMail("ftrouill @ulb.ac.be"));
    }
}