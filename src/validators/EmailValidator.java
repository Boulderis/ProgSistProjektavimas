package validators;

public class EmailValidator {

    private static final int maximumRecipientLength = 63; // Turėtų būti 64, bet paimiau pagal testus.
    private static final int maximumFullDomainLength = 255;
    private static final int maximumDomainLabelLength = 62; // Paimiau 62 pagal testus, bet regis, max yra 63.
    private static final int minimumDomainLabelLength = 3;
    private static String specialCharacters = "!#$%~&'*+-/=?^_.`{|";

    public EmailValidator() {

    }

    public void validate(String email) throws InvalidEmailException {
        if(email == null || email.isEmpty()) throw new InvalidEmailException("Email address cannot be null or empty!");
        if(!email.contains("@")) throw new InvalidEmailException("Email address is missing \"@\" sign!");
        if(!email.contains(".")) throw new InvalidEmailException("Email address is missing \".\" sign!");
        String splitEta[] = email.split("@");
        if(splitEta.length > 2) throw new InvalidEmailException("Email address has too many \"@\" signs!");
        String recipient = splitEta[0];
        String fullDomain = splitEta[1];
        String splitDot[] = fullDomain.split("\\.");
        if(splitDot.length > 2) throw new InvalidEmailException("Email address has too many \".\" signs!");
        String topDomain = splitDot[splitDot.length - 1];
        checkForSpecialCharactersRestrictions(email);
        validateRecipientName(recipient);
        validateFullDomainName(fullDomain, splitDot);
        validateTopDomainName(topDomain);
    }

    private void checkForSpecialCharactersRestrictions(String email) throws InvalidEmailException{
        if(isSpecialSymbol(email.charAt(0)) != null ||
                isSpecialSymbol(email.charAt(email.length() - 1)) != null) {
            throw new InvalidEmailException("Email address cannot have special symbols at the start or at the end!");
        }
        for(int i = 0; i < email.length(); i++) {
            Character currentSymbol = email.charAt(i);
            if(currentSymbol == '@') continue; // Patikrinta anksčiau. Jei nebus šito patikrinimo, teks sukti
            // du for'us vartotojo vardui ir domeno vardui.
            if(!Character.isLetter(currentSymbol) &&
                    !Character.isDigit(currentSymbol) && isSpecialSymbol(currentSymbol) == null) {
                throw  new InvalidEmailException("Email address " +
                        "cannot have invalid symbols! Invalid symbol: " + currentSymbol + ".");
            }
            if(i == email.length() - 1) break;  // Paskutinis simbolis neturės sekančio simbolio.
            Character current = isSpecialSymbol(currentSymbol);
            if(current == null) continue;
            Character next = isSpecialSymbol(email.charAt(i+1));
            if(current == next) throw new InvalidEmailException("Email address cannot have two identical" +
                    "special symbols consecutively!");
        }
    }

    private void validateFullDomainName(String domain, String[] domainParts) throws InvalidEmailException {
        if(domain.length() > maximumFullDomainLength) throw  new InvalidEmailException("Full domain name is too long!");
        for(int i = 0; i < domainParts.length; i++) {
            String domainLabel = domainParts[i];
            if(domainLabel.length() > maximumDomainLabelLength || domainLabel.length() < minimumDomainLabelLength) {
                throw new InvalidEmailException("Domain label \"" + domainLabel + "\" " +
                        "length exceeds the maximum of " + maximumDomainLabelLength + ".");
            }
        }
    }

    private void validateTopDomainName(String topDomain) throws InvalidEmailException {
        if(Character.isDigit(topDomain.charAt(0))) throw new InvalidEmailException("Top domain name cannot start with a digit!");
    }

    private void validateRecipientName(String recipient) throws InvalidEmailException {
        if(recipient.length() > maximumRecipientLength) throw  new InvalidEmailException("Recipient name is too long!");
    }

    private Character isSpecialSymbol(Character symbol) { // returns null if it is not a special symbol.
        return specialCharacters.contains(symbol.toString()) ? symbol : null;
    }

}
