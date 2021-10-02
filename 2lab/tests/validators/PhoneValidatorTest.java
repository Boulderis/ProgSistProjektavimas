package validators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PhoneValidatorTest {

    static Map<String, Integer> rules;
    static PhoneValidator phoneValidator;

    @BeforeAll
    public static void setupEnv() {
        rules = new HashMap<String, Integer>(){{
            put("+370", 8); // Kam dėti Integer kaip value?
        }};
        phoneValidator = new PhoneValidator(rules);
    }

    @Test
    public void Should_ValidatePhoneNumber_When_PhoneNumberIsCorrect() { // Teko keisti testą.
        String phoneNumber = "868123456";
        String newPhoneNumber = null;
        try {
            newPhoneNumber = phoneValidator.validate(phoneNumber);
        } catch(InvalidPhoneNumberException e) {
            e.printStackTrace();
        }
        assertEquals("+37068123456", newPhoneNumber);
    }

    @Test
    public void Should_ValidatePhoneNumber_When_PhoneNumberIsCorrectWithCompleteForm() { // Teko keisti testą.
        String phoneNumber = "+37068123678";
        String newPhoneNumber = null;
        try {
            newPhoneNumber = phoneValidator.validate(phoneNumber);
        } catch(InvalidPhoneNumberException e) {
            e.printStackTrace();
        }
        assertEquals("+37068123678", newPhoneNumber);
    }

    @Test
    public void Should_ThrowException_When_PhoneNumberIsNull() {
        String phoneNumber = null;

        assertThrows(InvalidPhoneNumberException.class, () -> {
            phoneValidator.validate(phoneNumber);
        });
    }

    @Test
    public void Should_ThrowException_When_PhoneNumberHasNoRules() {
        String phoneNumber = "0768123678";

        assertThrows(InvalidPhoneNumberException.class, () -> {
            phoneValidator.validate(phoneNumber);
        });
    }

    @Test
    public void Should_ThrowException_When_PhoneNumberConsistsOfNotOnlyDigits() {
        String phoneNumber = "86*123678";

        assertThrows(InvalidPhoneNumberException.class, () -> {
            phoneValidator.validate(phoneNumber);
        });
    }

    @Test
    public void Should_ThrowException_When_PhoneNumberWithCompleteFormIsTooSmall() {
        String phoneNumber = "+3706812345";

        assertThrows(InvalidPhoneNumberException.class, () -> {
            phoneValidator.validate(phoneNumber);
        });
    }

    @Test
    public void Should_ThrowException_When_PhoneNumberIsTooSmall() {
        String phoneNumber = "86812345";

        assertThrows(InvalidPhoneNumberException.class, () -> {
            phoneValidator.validate(phoneNumber);
        });
    }
}