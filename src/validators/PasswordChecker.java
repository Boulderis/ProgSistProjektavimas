package validators;

import java.util.Arrays;
import java.util.List;

public class PasswordChecker {

    private static int minLength;
    private static List<Character> specialSymbols;

    public PasswordChecker(int minLength, List<Character> specialSymbols) {
        this.minLength = minLength;
        this.specialSymbols = specialSymbols;
    }

    public void validate(String password) throws InvalidPasswordException {
        if(password == null) throw new InvalidPasswordException("Password cannot be null!");
        if(password.length() < minLength) throw new InvalidPasswordException("Password too small: minimal length is " +
                minLength + "!");
        if(!containsUppercase(password)) throw new InvalidPasswordException("Password must have at least one uppercase letter.");
        if(!containsSpecialSymbols(password)) throw  new InvalidPasswordException("Password must have" +
                " at least one of these special symbols:" + Arrays.toString(specialSymbols.toArray()) + "!");
        if(password.contains(" ")) throw new InvalidPasswordException("Password cannot contain empty spaces!");// exactly spaces, not tabs or anything else.
    }

    private boolean containsSpecialSymbols(String password) {
        for(int i = 0; i < specialSymbols.size(); i++) {
            if(password.contains(specialSymbols.get(i).toString())) return true;
        }
        return false;
    }

    private boolean containsUppercase(String password) {
        for(int i = 0; i < password.length(); i++) {
            if(Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
