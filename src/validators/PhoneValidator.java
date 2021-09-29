package validators;

import java.util.Map;

public class PhoneValidator {

    private static Map<String, Integer> rules;
    private static final int phoneNumberLength = 12;

    public PhoneValidator(Map rules) {
        this.rules = rules;
    }

    public String validate(String phoneNumber) throws InvalidPhoneNumberException {
        if(phoneNumber == null || phoneNumber.isEmpty()) {
            throw new InvalidPhoneNumberException("Phone number cannot be null or empty.");
        }
        if(phoneNumberHasInvalidSymbols(phoneNumber)) {
            throw new InvalidPhoneNumberException("Phone number must contain only digits (may contain plus sign)!");
        }
        phoneNumber = changePrefix(phoneNumber);
        if(phoneNumber.length() < 12) throw new InvalidPhoneNumberException("Phone number is too short.");
        return phoneNumber;
    }

    private boolean phoneNumberHasInvalidSymbols(String phoneNumber) {
        char firstChar = phoneNumber.charAt(0);
        if(!Character.isDigit(firstChar) && !(firstChar == '+')) return true;
        for(int i = 1; i < phoneNumber.length(); i++) {
            if(!Character.isDigit(phoneNumber.charAt(i))) return true;
        }
        return false;
    }

    private String changePrefix(String phoneNumber) throws InvalidPhoneNumberException{
        for (Map.Entry<String, Integer> entry : rules.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(phoneNumber.startsWith(key)) return phoneNumber;
            if(phoneNumber.startsWith(String.valueOf(value))) {
                return phoneNumber.replaceFirst(String.valueOf(value), key);
            }
        }
        throw new InvalidPhoneNumberException("Phone number does not meet any prefix rules!");
    }

}
