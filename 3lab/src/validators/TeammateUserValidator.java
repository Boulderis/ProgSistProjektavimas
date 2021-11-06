package validators;

import Validators.Email;
import Validators.Password;
import Validators.Phone;

public class TeammateUserValidator implements UserValidator{

    private final int minPasswordLength = 8;
    // At least one of the symbols below must be in a password for it to be valid.
    private final char[] requiredSymbols = "$%!?.,^*+".toCharArray();
    /* This implementation cannot set custom phone prefixes because the library used does not allow that.
    Only prefixes from predefined list of countries are allowed.*/
    private final String countryName = "LT";

    @Override
    public int validatePassword(String password) {
        Password passwordValidator = new Password(password);
        if(!passwordValidator.CheckIfValid()) return 4;
        if(!passwordValidator.contains(requiredSymbols)) return 3;
        if(!passwordValidator.hasUppercase()) return 2;
        if(!passwordValidator.compareLength(minPasswordLength)) return 1;
        return 0;
    }

    @Override
    public int validatePhoneNumber(String phoneNumber) {
        Phone phoneNumberValidator = new Phone(phoneNumber);
        if(!phoneNumberValidator.CheckIfValid()) return 3;
        // This actually checks if phone number digits' count is correct.
        if(!phoneNumberValidator.checkForeignPrefix(countryName)) return 2;
        if(!phoneNumberValidator.checkIfOnlyNumbers()) return 1;
        return 0;
    }

    @Override
    public int validateEmail(String email) {
        Email emailValidator = new Email(email);
        if(!emailValidator.CheckIfValid()) return 4;
        // This library actually does not checks domain and TLD. It only checks dot placement.
        if(!emailValidator.hasCorrectDomainAndTLD()) return 3;
        if(emailValidator.hasNotAllowedSymbols()) return 2;
        if(!emailValidator.hasAtSymbol()) return 1;
        return 0;
    }

}
