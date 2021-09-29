package validators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordCheckerTest {

    static int minLength;
    static List<Character> specialSymbols;
    static PasswordChecker passwordChecker;

    @BeforeAll
    public static void setupEnv() {
        minLength = 8;
        specialSymbols = Arrays.asList('!', '&', ')', '.');
        passwordChecker = new PasswordChecker(minLength, specialSymbols);
    }

    @Test
    public void Should_Not_ThrowException_When_PasswordIsCorrect() {
        String password = "ASd123a)s_d123";
        assertDoesNotThrow(() -> passwordChecker.validate(password)); // Čia buvo pakeista, nes neteisingai padaryta.
    }

    @Test
    public void Should_ThrowException_When_PasswordIsNull() {
        String password = null;

        assertThrows(InvalidPasswordException.class, () -> {
            passwordChecker.validate(password);
        });
    }

    @Test
    public void Should_ThrowException_When_PasswordIsTooSmall() {
        String password = "_A8)";
        int minLength = 5;
        PasswordChecker passwordChecker = new PasswordChecker(minLength, specialSymbols);

        assertThrows(InvalidPasswordException.class, () -> {
            passwordChecker.validate(password);
        });
    }

    @Test
    public void Should_ThrowException_When_PasswordIsMissingUppercaseSymbol() {
        String password = "_qwe8)rsdaeqwr";

        assertThrows(InvalidPasswordException.class, () -> {
            passwordChecker.validate(password);
        });
    }

    @Test
    public void Should_ThrowException_When_PasswordIsMissingSpecialSymbol() {
        String password = "_qwe8Ersdaeq-r";

        assertThrows(InvalidPasswordException.class, () -> {
            passwordChecker.validate(password);
        });
    }

    @Test
    public void Should_ThrowException_When_PasswordContainsEmptySpaces() {
        String password = "a Sd123a)s_d123";

        assertThrows(InvalidPasswordException.class, () -> {
            passwordChecker.validate(password);
        });
    }
}