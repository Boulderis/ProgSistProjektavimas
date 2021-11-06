package validators;

import models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationResultTests {

    @Test
    public void testUserValidationResult() {
        User user = new User();
        ErrorOrigin errorOrigin = ErrorOrigin.EMAIL;
        int errorCode = 3;
        UserValidationResult first = new UserValidationResult(user);
        UserValidationResult second = new UserValidationResult(errorOrigin, errorCode);
        assertAll("Tests the first constructor of UserValidationResult",
                () -> assertEquals(user, first.getUser()),
                () -> assertNull(first.getErrorOrigin()),
                () -> assertEquals(0, first.getErrorCode()));
        assertAll("Tests the second constructor of UserValidationResult",
                () -> assertNull(second.getUser()),
                () -> assertEquals(errorOrigin, second.getErrorOrigin()),
                () -> assertEquals(errorCode, second.getErrorCode()));
    }

}
