package Test;

import org.junit.jupiter.api.Test;
import users.MainCoordinator;

import static org.junit.jupiter.api.Assertions.*;

class MainCoordinatorTest {
    @Test
    void wordPatternNormal() {
        assertEquals(true, MainCoordinator.wordPattern("szymek"));

    }
    @Test
    void wordPatternWithSpecialCharacter() {

        assertEquals(false, MainCoordinator.wordPattern("!ASDA"));
    }
    @Test
    void wordPatternEmpty() {
        assertEquals(false, MainCoordinator.wordPattern(""));
    }
    @Test
    void wordPatternToLong() {
        assertEquals(false, MainCoordinator.wordPattern("to_loooong_teeeeeextt"));
    }
    @Test
    void wordPatternToShort() {
        assertEquals(false, MainCoordinator.wordPattern("S"));
    }
    @Test
    void wordPatternWithSpace() {
        assertEquals(true, MainCoordinator.wordPattern("Moje pudelko"));
    }
    @Test
    void wordPatternWithSpaceFirst() {
        assertEquals(false, MainCoordinator.wordPattern(" Moje pudelko"));
    }
    @Test
    void passwordPatternToShort() {
        assertEquals(false, MainCoordinator.passwordPattern("n!k@s"));
    }
    @Test
    void passwordPatternNoDigitAndUpperCase() {
        assertEquals(false, MainCoordinator.passwordPattern("szymon-SG"));
    }
    @Test
    void passwordPatternWrongSpecialCharacter() {
        assertEquals(false, MainCoordinator.passwordPattern("abcdFg45*"));
    }
    @Test
    void passwordPatternNoDigit() {
        assertEquals(false, MainCoordinator.passwordPattern("n!koabcD#AX"));
    }
    @Test
    void passwordPatternTooLong() {
        assertEquals(false, MainCoordinator.passwordPattern("szymongruszczynski1998sg"));
    }
    @Test
    void passwordPatternGood() {
        assertEquals(true, MainCoordinator.passwordPattern("Szymon2@"));
    }
}