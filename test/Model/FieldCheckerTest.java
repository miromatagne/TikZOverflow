package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldCheckerTest {

    @Test
    void isValidUsername() {
        FieldChecker fieldChecker = new FieldChecker();
        assertTrue(fieldChecker.isValidUsername("ftrouill"));
        assertTrue(fieldChecker.isValidUsername("LeBGdu65"));
        assertTrue(fieldChecker.isValidUsername("Olababy85yo"));
        assertFalse(fieldChecker.isValidUsername("Franck TROUILLEZ"));
        assertFalse(fieldChecker.isValidUsername("Franck?"));
    }

    @Test
    void isValidName() {
        FieldChecker fieldChecker = new FieldChecker();
        assertTrue(fieldChecker.isValidName("ftrouill"));
        assertFalse(fieldChecker.isValidName("LeBGdu65"));
        assertFalse(fieldChecker.isValidName("Olababy85yo"));
        assertTrue(fieldChecker.isValidName("Franck TROUILLEZ"));
        assertTrue(fieldChecker.isValidName("Franck-Einstein"));
    }

    @Test
    void isValidMail() {
        FieldChecker fieldChecker = new FieldChecker();
        assertTrue(fieldChecker.isValidMail("ftrouill@ulb.ac.be"));
        assertFalse(fieldChecker.isValidMail("ftrouill.ulb.ac.be"));
        assertFalse(fieldChecker.isValidMail("ftrouill@ulb@ac.be"));
        assertFalse(fieldChecker.isValidMail("ftrouill @ulb.ac.be"));
        assertFalse(fieldChecker.isValidMail("ftrouill@ulb"));
        assertFalse(fieldChecker.isValidMail("ftrouill@ulb."));
        assertTrue(fieldChecker.isValidMail("ftrouill@ulb.ac"));
        assertFalse(fieldChecker.isValidMail("ftrouill@ulb.ac."));
    }

    @Test
    void isValidNumber() {
        FieldChecker fieldChecker = new FieldChecker();
        assertTrue(fieldChecker.isValidNumber("321.7"));
        assertTrue(fieldChecker.isValidNumber("0.3217"));
        assertTrue(fieldChecker.isValidNumber("3217"));
        assertFalse(fieldChecker.isValidNumber("12."));
        assertFalse(fieldChecker.isValidNumber("a"));
        assertFalse(fieldChecker.isValidNumber(".56"));
        assertFalse(fieldChecker.isValidNumber("0.5.6"));
        assertFalse(fieldChecker.isValidNumber("5.6a"));
    }

    @Test
    void isValidAccount() {
        FieldChecker fieldChecker = new FieldChecker();
        assertTrue(fieldChecker.isValidAccount("ftrouill12", "Franck", "TROUILLEZ", "ftrouill@ulb.ac.be", "aaaaa", "aaaaa"));
        assertTrue(fieldChecker.isValidAccount("rtodesco", "Raffaele", "TODESCO", "rtodesco@ulb.ac.be", "rto", "rto"));
        assertFalse(fieldChecker.isValidAccount("ftrouill   ", "Franck", "TROUILLEZ", "ftrouill@ulb.ac.be", "aaaaa", "aaaaa"));
        assertFalse(fieldChecker.isValidAccount("ftrouill", "Franck4444", "TROUILLEZ", "ftrouill@ulb.ac.be", "aaaaa", "aaaaa"));
        assertFalse(fieldChecker.isValidAccount("ftrouill", "Franck", "TROUILLEZ7567", "ftrouill@ulb.ac.be", "aaaaa", "aaaaa"));
        assertFalse(fieldChecker.isValidAccount("ftrouill", "Franck", "TROUILLEZ", "ftrouill@ulb.ac.", "aaaaa", "aaaaa"));
        assertFalse(fieldChecker.isValidAccount("ftrouill", "Franck", "TROUILLEZ", "ftrouill@ulb.ac.be", "bbb", "aaaaa"));
        assertFalse(fieldChecker.isValidAccount("ftrouill", "Franck", "TROUILLEZ", "ftrouill@ulb.ac.be", "bbb", "ccccccc"));
    }
}