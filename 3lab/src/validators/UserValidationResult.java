package validators;

import models.User;

public class UserValidationResult {

    private User user;
    private ErrorOrigin errorOrigin;
    private int errorCode;

    public UserValidationResult(User user) {
        this.user = user;
    }

    public UserValidationResult(ErrorOrigin errorOrigin, int errorCode) {
        this.errorOrigin = errorOrigin;
        this.errorCode = errorCode;
    }

    public User getUser() {
        return user;
    }

    public ErrorOrigin getErrorOrigin() {
        return errorOrigin;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public boolean success() {
        return errorOrigin == null;
    }

    public void printValidationResult() {
        if(user != null) {
            System.out.println("Vartotojo validacija pavyko:");
            System.out.println(user.toString());
        }
        else {
            System.out.println("Vykdant vartotojo validaciją įvyko klaida:");
            System.out.println("Klaidos šaltinis: \"" + errorOrigin + "\", klaidos kodas: " + errorCode + "!");
        }
    }

}
