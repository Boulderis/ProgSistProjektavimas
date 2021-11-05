package repositories;

import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validators.UserValidationResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserFileRepositoryTests {

    private final char separator = '~';
    private final String[] fileContents = new String[] {
            "1" + separator + "Petras" + separator + "Petraitis" +
            separator + "+37062125325" + separator + "petraitis@gmail.com"
            + separator + "adresas, Vilnius" + separator + "TestasABC!?^%",
            "2" + separator + "Jonas" + separator + "Jonaitis" +
                    separator + "+37064525825" + separator + "jonaitis@gmail.com"
                    + separator + "adresas, Vilnius" + separator + "DEF$A!?^%",
            "3" + separator + "Stasys" + separator + "Stasiulis" +
                    separator + "+37061225555" + separator + "stasys.stasiulis@gmail.com"
                    + separator + "adresas, Vilnius" + separator + "TestasDEF$A!?^%"};
    private File testFile;
    private UserFileRepository userFileRepository;

    @BeforeEach
    public void start() {
        testFile = new File("testUsers.txt");
        if(testFile.exists()) {
            fail("Cannot test, file already exists!");
        }
        try {
            boolean success = testFile.createNewFile();
            if(!success) {
                fail("Failed to create a test file!");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
            for(String line: fileContents) {
                writer.write(line + System.getProperty("line.separator"));
            }
            writer.close();
            userFileRepository = new UserFileRepository(testFile);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to create a test file!");
        }
    }

    @AfterEach
    public void end() {
        testFile.delete();
        userFileRepository = null;
    }

    @Test
    public void testGetUsers() {
        List<User> gotUsers = userFileRepository.getUsers();
        assertEquals(fileContents.length, gotUsers.size());
    }

    @Test
    public void testGetUser() {
        long ID = 2;
        User user = userFileRepository.getUser(ID);
        assertAll("Test if user with a correct ID is retrieved from the file",
                () -> assertEquals(ID, user.getUserID()),
                () -> assertEquals("Jonas", user.getName()),
                () -> assertEquals("Jonaitis", user.getSurname()),
                () -> assertEquals("+37064525825", user.getPhoneNumber()),
                () -> assertEquals("jonaitis@gmail.com", user.getEmail()),
                () -> assertEquals("adresas, Vilnius", user.getAddress()),
                () -> assertEquals("DEF$A!?^%", user.getPassword()));
    }

    @Test
    public void testCreateUser() {
        User user = new User("Testas", "Testaitis", "+37061245222",
                "testas@gmail.com", "Adresas, Vilnius", "Slaptas?!aa");
        UserValidationResult result = userFileRepository.createUser(user);
        List<User> users = userFileRepository.getUsers();
        assertAll(
                () -> assertEquals(fileContents.length + 1, users.size()),
                () -> assertEquals(user.toString(), users.get(users.size() - 1).toString()),
                () -> assertEquals(user, result.getUser()),
                () -> assertNull(result.getErrorOrigin()),
                () -> assertEquals(0, result.getErrorCode())
        );
    }

    @Test
    public void testUpdateUser() {
        long ID = 1;
        User user = new User("Testas", "Testaitis", "+37061245222",
                "testas@gmail.com", "Adresas, Vilnius", "Slaptas?!aa");
        user.setUserID(ID);
        UserValidationResult result = userFileRepository.updateUser(ID, user);
        List<User> users = userFileRepository.getUsers();
        assertAll(
                () -> assertEquals(fileContents.length, users.size()),
                () -> assertEquals(user.toString(), userFileRepository.getUser(ID).toString()),
                () -> assertEquals(user, result.getUser()),
                () -> assertNull(result.getErrorOrigin()),
                () -> assertEquals(0, result.getErrorCode())
        );
    }

    @Test
    public void testDeleteUser() {
        long ID = 2;
        User userBeforeDeletion = userFileRepository.getUser(ID);
        userFileRepository.deleteUser(ID);
        User userAfterDeletion = userFileRepository.getUser(ID);
        assertAll("Test if user deletion is successful",
                () -> assertNotNull(userBeforeDeletion),
                () -> assertNull(userAfterDeletion));
    }

}
