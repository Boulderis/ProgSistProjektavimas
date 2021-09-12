package validation;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(Theories.class)
public class ValidatorTests {

    private Validation validation;

    @BeforeEach
    private void setup() {
        validation = new Validation();
    }

    @DataPoints("passwords")
    public static String[] passwords() {
        return new String[]{
                "testAs145", "testUOoju124", "-123456AA7", "sgGSDGsggvxbvcx", "10BfdssfdsfB", "TESTASTTESTAS"};
    }

    @DataPoints("special symbols")
    public static String[] specialSymbols() {
        return new String[]{
                "!+-~^$"};
    }

    @DataPoints("phone numbers")
    public static String[] phoneNumbers() {
        return new String[]{
                "+37064488423", "865542322", "868888888"};
    }

    @DataPoints("emails")
    public static String[] emails() {
        return new String[]{
                "pranas.pranauskas@gmail.com", "stasys.stasiulis@gmail.com",
                "jonas.jonaitis@gmail.com", "petras.petraitis@gmail.com"};
    }

    @DataPoints("restricted email symbols")
    public static String[] restrictedEmailSymbolList() {
        return new String[]{
                "&$^!#%"};
    }

    @DataPoints("top level domains")
    public static String[][] allowedTopLevelDomains() {
        return new String[][] {
                new String[] {"com", "org", "net", "gov", "lt", "en", "de"}
        };
    }


    @Theory
    private void TestPasswordLengthCheck(@FromDataPoints("passwords") String password) {
        int minLength = 8;
        PasswordPolicy passwordPolicy = new PasswordPolicy();
        passwordPolicy.setMinLength(minLength);
        int[] status = validation.checkPassword(password, passwordPolicy); // if password check fails, error code array is returned, otherwise its empty.
        findError(status, 1); // let "password too short error code" be 1.
    }

    @Theory
    private void TestPasswordUppercaseLetterExistance(@FromDataPoints("passwords") String password) {
        PasswordPolicy passwordPolicy = new PasswordPolicy();
        passwordPolicy.requireUppercase(true);
        int[] status = validation.checkPassword(password, passwordPolicy);
        findError(status, 2); // 2 for password with no uppercase letter.
    }

    @Theory
    private void TestPasswordSpecialSymbolExistance(@FromDataPoints("passwords") String password, @FromDataPoints("special symbols") String specialSymbols) {
        PasswordPolicy passwordPolicy = new PasswordPolicy();
        passwordPolicy.requireSpecialSymbols(true);
        passwordPolicy.setSpecialSymbols(specialSymbols);
        int[] status = validation.checkPassword(password, passwordPolicy);
        findError(status, 3); // 3 for passwords those have none of the required special symbols.
    }

    @Theory
    private void TestIfNumberIsPlusAndNumbersOnly(@FromDataPoints("phone numbers") String phoneNumber) {
        PhoneNumberPolicy phoneNumberPolicy = new PhoneNumberPolicy(); // Default setup. Default phone number policy specifies, that only the plus sign and numbers are allowed.
        int[] status = validation.checkPhoneNumber(phoneNumber, phoneNumberPolicy);
        findError(status, 1); // 1 if phone number has other symbols than the plus sign and numbers.
    }

    @Theory
    private void TestIfEightIsChangedToLithuaniaNationalNumberCode(@FromDataPoints("phone numbers") String phoneNumbers) {
        PhoneNumberPolicy phoneNumberPolicy = new PhoneNumberPolicy(Country.LITHUANIA);
        String nationalPhoneNumber = validation.applyNationalPhoneNumberCode(phoneNumber, phoneNumberPolicy);
        Assert.assertTrue(nationalPhoneNumber.startsWith("+370"));
    }

    @Theory
    private void TestPhoneNumberNationalNumberCodeApplication(@FromDataPoints("phone numbers") String phoneNumber) {
        PhoneNumberPolicy phoneNumberPolicy = new PhoneNumberPolicy(Country.LITHUANIA);
        String nationalPhoneNumber = validation.applyNationalPhoneNumberCode(phoneNumber, phoneNumberPolicy);
        int[] status = validation.checkPhoneNumber(nationalPhoneNumber, phoneNumberPolicy); // Check phone number does not recognize for example: 86 as correct, so method above must be used first.
        findError(status, 2); // 2 if number does not follow country's rules.
    }

    @Theory
    private void TestIfEmailHasEta(@FromDataPoints("emails") String email) {
        EmailPolicy emailPolicy = new EmailPolicy(); // This is the default setup. Default email policy specifies that eta must exist.
        int[] status = validation.checkEmail(email, emailPolicy);
        findError(status, 1); // 1 if email does not have eta symbol.
    }

    @Theory
    private void TestIfEmailDoesNotHaveRestrictedSymbols(@FromDataPoints("emails") String email, @FromDataPoints("restricted email symbols") String restrictedSymbols) {
        EmailPolicy emailPolicy = new EmailPolicy();
        emailPolicy.haveRestrictedSymbols(true);
        emailPolicy.setRestrictedSymbols(restrictedSymbols);
        int[] status = validation.checkEmail(email, emailPolicy);
        findError(status, 2); // 2 if email contains a restricted symbol.
    }

    @Theory
    private void TestIfEmailHasCorrectDomain(@FromDataPoints("emails") String email, @FromDataPoints("top level domains") String[] topLevelDomains) {
        EmailPolicy emailPolicy = new EmailPolicy();
        emailPolicy.haveAllowedTopLevelDomains(true);
        emailPolicy.setValidTopLevelDomains(topLevelDomains);
        int[] status = validation.checkEmail(email, emailPolicy);
        findError(status, 3); // 3 if top level domain is wrong (if not found in the list).
        findError(status, 4); // 4 if email violates other email rules (for example: A@b@c@example.com).
    }

    private void findError(int[] status, int errorCode) {
        Assert.assertFalse(Arrays.toString(status).contains(String.valueOf(errorCode)));
    }


    @AfterEach
    private void close() {

    }

}
