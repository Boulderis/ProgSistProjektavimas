package validators;

import models.User;

public interface UserValidator {

    /**
     * Status codes:
     *      0 - OK.
     *      1 - Password is shorter than a specified length.
     *      2 - Password is missing an uppercase symbol.
     *      3 - Password is missing a required symbol.
     *      4 - Password is null.
     * @param password      password to be validated.
     * @return              0 if password is correct, an error code number otherwise.
     */
    int validatePassword(String password);


    /**
     * Status codes:
     *      0 - OK.
     *      1 - Phone number contains not only numbers (plus sign at the start does not cause this error code to fire).
     *      2 - Phone number violates a specific country's rules.
     *      3 - Phone number is null.
     * @param phoneNumber   phone number to be validated.
     * @return              0 if phone number is correct, an error code number otherwise.
     */
    int validatePhoneNumber(String phoneNumber);


    /**
     * Status codes:
     *      0 - OK.
     *      1 - Email address is missing @ symbol.
     *      2 - Email address has restricted symbols.
     *      3 - Email address has incorrect domain or top-level domain.
     *      4 - Email address is null.
     * @param email     email address to be validated.
     * @return          0 if email address is correct, an error code number otherwise.
     */
    int validateEmail(String email);

    default UserValidationResult validateUser(User user) {
        int passwordStatus = validatePassword(user.getPassword());
        if(passwordStatus != 0) return new UserValidationResult(ErrorOrigin.PASSWORD, passwordStatus);
        int phoneNumberStatus = validatePhoneNumber(user.getPhoneNumber());
        if(phoneNumberStatus != 0) return new UserValidationResult(ErrorOrigin.PHONE_NUMBER, phoneNumberStatus);
        int emailStatus = validateEmail(user.getEmail());
        if(emailStatus != 0) return new UserValidationResult(ErrorOrigin.EMAIL, emailStatus);
        return new UserValidationResult(user);
    }

}
