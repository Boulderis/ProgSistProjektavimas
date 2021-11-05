package managers;

import models.User;
import repositories.UserFileRepository;
import repositories.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserRequestManager {

    UserRepository userRepository;
    Scanner scanner;

    public UserRequestManager(String datafilePath, Scanner scanner) {
        File file = openFile(datafilePath);
        userRepository = new UserFileRepository(file);
        this.scanner = scanner;
    }

    private File openFile(String filePath) {
        File file = new File(filePath);
        if(!file.exists()) {
            System.out.println("Failas neegzistuoja. Sukuriamas naujas failas.");
            try {
                boolean success = file.createNewFile();
                if(!success) {
                    System.out.println("Nepavyko sukurti failo!");
                    System.exit(0);
                }
            } catch (IOException e) {
                System.err.println("Nepavyko sukurti failo!");
                e.printStackTrace();
                System.exit(0);
            }
        }
        return file;
    }

    public void printUsers() {
        List<User> users = userRepository.getUsers();
        for(User user: users) System.out.println(user.toString());
    }

    public void printUser() {
        long userID = readUserIDInput();
        User user = userRepository.getUser(userID);
        System.out.println(user.toString());
    }

    public void addUser() {
        User user = createUser();
        userRepository.createUser(user);
    }

    public void updateUser() {
        long userID = readUserIDInput();
        User user = createUser();
        user.setUserID(userID);
        userRepository.updateUser(userID, user);
    }

    private User createUser() {
        User user = new User();
        System.out.println("Prašome įvesti vartotojo vardą:");
        user.setName(scanner.nextLine());
        System.out.println("Prašome įvesti vartotojo pavardę:");
        user.setSurname(scanner.nextLine());
        System.out.println("Prašome įvesti vartotojo telefono numerį:");
        user.setPhoneNumber(scanner.nextLine());
        System.out.println("Prašome įvesti vartotojo elektroninio paštą:");
        user.setEmail(scanner.nextLine());
        System.out.println("Prašome įvesti vartotojo adresą:");
        user.setAddress(scanner.nextLine());
        System.out.println("Prašome įvesti vartotojo slaptažodį:");
        user.setPassword(scanner.nextLine());
        return user;
    }

    public void deleteUser() {
        userRepository.deleteUser(readUserIDInput());
    }

    private long readUserIDInput() {
        String message = "Prašome įvesti vartotojo ID:";
        System.out.println(message);
        long userID;
        String input;
        while(true) {
            input = scanner.nextLine();
            try {
                userID = Long.parseLong(input);
                break;
            } catch(NumberFormatException e) {
                System.out.println("Netinkama įvestis!");
                System.out.println(message);
            }
        }
        return userID;
    }

}
