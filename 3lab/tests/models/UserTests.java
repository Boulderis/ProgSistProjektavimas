package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {

    @Test
    public void testUser() {
        long ID = 1;
        String name = "Testas";
        String surname = "Testaitis";
        String phoneNumber = "+37065124532";
        String email = "netikras@gmail.com";
        String address = "upės g., 15-32, Vilnius";
        String password = "slaptAs!??";
        User user = new User();
        user.setUserID(ID);
        user.setName(name);
        user.setSurname(surname);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setAddress(address);
        user.setPassword(password);
        assertAll("User model test",
                () -> assertEquals(ID, user.getUserID()),
                () -> assertEquals(name, user.getName()),
                () -> assertEquals(surname, user.getSurname()),
                () -> assertEquals(phoneNumber, user.getPhoneNumber()),
                () -> assertEquals(email, user.getEmail()),
                () -> assertEquals(address, user.getAddress()),
                () -> assertEquals(password, user.getPassword()));
    }

}
