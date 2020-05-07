package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestFieldChecker {

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
        TextTypeChecker textTypeChecker = new TextTypeChecker();
        assertTrue(textTypeChecker.isValidNumber("321.7"));
        assertTrue(textTypeChecker.isValidNumber("0.3217"));
        assertTrue(textTypeChecker.isValidNumber("3217"));
        assertFalse(textTypeChecker.isValidNumber("12."));
        assertFalse(textTypeChecker.isValidNumber("a"));
        assertFalse(textTypeChecker.isValidNumber(".56"));
        assertFalse(textTypeChecker.isValidNumber("0.5.6"));
        assertFalse(textTypeChecker.isValidNumber("5.6a"));
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