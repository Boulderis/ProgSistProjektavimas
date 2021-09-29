package validators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailValidatorTest {

    static EmailValidator emailValidator = new EmailValidator();

    @BeforeAll
    public static void setupEnv() {
        emailValidator = new EmailValidator();
    }

    @Test
    public void Should_ValidateEmail_When_EmailIsCorrect() {
        String email = "test.google4~_-09ASZ+56ual.proceed.com@example.com";

        assertDoesNotThrow(() -> emailValidator.validate(email)); // Teko pakeisti.
    }

    @Test
    public void Should_ThrowException_When_EmailIsNull() {
        String email = null;

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailIsEmpty() {
        String email = "";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailIsMissingAtSign() {
        String email = "testgmail.com";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailContainsInvalidSymbols() {
        String email = "mysite◘@gmail.com";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailTopLevelDomainStartsWithNumber() {
        String email = "test@gmail.1com";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailTopLevelDomainContainsUnsupportedSymbol() {
        String email = "test@gmail.c)om";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailTopLevelDomainStartsOrEndsWithHyphen() {
        String email = "test@gmail.-com-";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailDomainExceedsCharactersLimit() {
        String email = "test@gmailgmailgmailgmailgmailgmailgmailgmailgmailgmailgmailgmailgmk.com";
        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailUsernameExceedsCharactersLimit() {
        String email = "test123test123test123test123test123test123test123test123test123t@gmail.com";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailHasWrongFormat() {
        String email = "test123@gmail@gmail.com";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailContainsEmptySpaces() {
        String email = "test as@gmail.com";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailIsMissingDomain() {
        String email = "test123@";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailDomainNameContainsIncorrectCharacters() {
        String email = "test@gmail:.com";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }

    @Test
    public void Should_ThrowException_When_EmailDomainIsTooSmall() {
        String email = "test@google.x";

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });
    }
}