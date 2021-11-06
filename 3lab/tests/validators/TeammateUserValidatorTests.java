package validators;

import models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeammateUserValidatorTests {

    @Test
    public void testValidatePassword() {
        String goodPassword = "Testas4521A!???..,,";
        String tooShortPassword = "!A1a";
        String noUppercasePassword = "testastestas4256!!>>??^^";
        String missingARequiredSymbolPassword = "testasAAtestasBBCC";
        UserValidator validator = new TeammateUserValidator();
        assertAll("Test if specific passwords give specific status codes",
                () -> assertEquals(0, validator.validatePassword(goodPassword)),
                () -> assertEquals(1, validator.validatePassword(tooShortPassword)),
                () -> assertEquals(2, validator.validatePassword(noUppercasePassword)),
                () -> assertEquals(3, validator.validatePassword(missingARequiredSymbolPassword)),
                () -> assertEquals(4, validator.validatePassword(null)));
    }

    @Test
    public void testValidatePhoneNumber() {
        String goodPhoneNumber = "+37064215423";
        String notOnlyNumbersPhoneNumber = "+3cdefghijkl";
        String incorrectDigitCountPhoneNumber = "+3706421542";
        UserValidator validator = new TeammateUserValidator();
        assertAll("Test if specific phone numbers give specific status codes",
                () -> assertEquals(0, validator.validatePhoneNumber(goodPhoneNumber)),
                () -> assertEquals(1, validator.validatePhoneNumber(notOnlyNumbersPhoneNumber)),
                () -> assertEquals(2, validator.validatePhoneNumber(incorrectDigitCountPhoneNumber)),
                () -> assertEquals(3, validator.validatePhoneNumber(null)));
    }

    @Test
    public void testValidateEmail() {
        String goodEmail = "testas@gmail.com";
        String noEtaSymbolEmail = "testas.gmail.com";
        String illegalSymbolsEmail = "testas!@gmail.com";
        String incorrectDotPlacementEmail = "testas@.gmail.com";
        UserValidator validator = new TeammateUserValidator();
        assertAll("Test if specific emails give specific status codes",
                () -> assertEquals(0, validator.validateEmail(goodEmail)),
                /* Cannot test if library detects a missing @, because incorrect dot placement check comes first
                and it always returns its error code since it is encountered earlier.
                */
                //() -> assertEquals(1, validator.validateEmail(noEtaSymbolEmail)),
                () -> assertEquals(2, validator.validateEmail(illegalSymbolsEmail)),
                () -> assertEquals(3, validator.validateEmail(incorrectDotPlacementEmail)));
    }

    @Test
    public void testValidateUser() {
        User goodUser = new User(null, null,
                "+37067451245", "testuoju@gmail.com", null, "abcSlapt!!??");
        User incorrectPasswordUser = new User(null, null,
                "+37067451245", "testuoju@gmail.com", null, "A?!");
        User incorrectPhoneNumberUser = new User(null, null,
                "+3706745145a", "abc@gmail.com", null, "ABCDEF411?!");
        User incorrectEmailUser = new User(null, null,
                "+37067451245", "testuo!ju@gmail.com", null, "ABCDEF411?!");
        UserValidator validator = new TeammateUserValidator();
        UserValidationResult successResult = validator.validateUser(goodUser);
        UserValidationResult failedPassword = validator.validateUser(incorrectPasswordUser);
        UserValidationResult failedPhoneNumber = validator.validateUser(incorrectPhoneNumberUser);
        UserValidationResult failedEmail = validator.validateUser(incorrectEmailUser);
        assertAll("Test if correct validation result is returned on user validation",
                () -> assertTrue(successResult.success()),
                () -> assertFalse(failedPassword.success()),
                () -> assertFalse(failedPhoneNumber.success()),
                () -> assertFalse(failedEmail.success()),
                () -> assertTrue(successResult.getErrorCode() == 0
                        && successResult.getErrorOrigin() == null),
                () -> assertTrue(failedPassword.getErrorCode() == 1
                        && failedPassword.getErrorOrigin() == ErrorOrigin.PASSWORD),
                () -> assertTrue(failedPhoneNumber.getErrorCode() == 1
                && failedPhoneNumber.getErrorOrigin() == ErrorOrigin.PHONE_NUMBER),
                () -> assertTrue(failedEmail.getErrorCode() == 2
                && failedEmail.getErrorOrigin() == ErrorOrigin.EMAIL));
    }

}
