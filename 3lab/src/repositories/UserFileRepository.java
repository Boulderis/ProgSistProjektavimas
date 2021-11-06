package repositories;

import models.User;
import validators.TeammateUserValidator;
import validators.UserValidationResult;
import validators.UserValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserFileRepository implements UserRepository {

    private final File file;

    private final char separator = '~';

    private final UserValidator userValidator = new TeammateUserValidator();

    public UserFileRepository(File file) {
        this.file = file;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] split = data.split(String.valueOf(separator));
                User user = parseUser(split);
                users.add(user);
            }
        } catch(FileNotFoundException e) {
            System.err.println("File not found!");
        } catch(IndexOutOfBoundsException e) {
            System.err.println("Incorrect data format!");
            e.printStackTrace();
        }
        finally {
            closeResourcesAndDeleteTemporaryFiles(scanner, null, null);
        }
        return users;
    }

    public User getUser(long ID) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] split = data.split(String.valueOf(separator));
                long currentID = Integer.parseInt(split[0]);
                if(currentID == ID) {
                    User user = parseUser(split);
                    return user;
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
        } catch(IndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Incorrect data format!");
            e.printStackTrace();
        }
        finally {
            closeResourcesAndDeleteTemporaryFiles(scanner, null, null);
        }
        return null;
    }

    public UserValidationResult createUser(User user) {
        long ID = getHighestUserID() + 1;
        user.setUserID(ID);
        UserValidationResult userValidationResult = userValidator.validateUser(user);
        userValidationResult.printValidationResult();
        BufferedWriter writer = null;
        if(userValidationResult.success()) {
            try {
                writer = new BufferedWriter(new FileWriter(file, true));
                writer.append(buildUser(ID, user)).append(System.getProperty("line.separator"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally{
                closeResourcesAndDeleteTemporaryFiles(null, writer, null);
            }
        }
        return userValidationResult;
    }

    public UserValidationResult updateUser(long ID, User user) {
        File tempFile = createTempFile();
        UserValidationResult userValidationResult = userValidator.validateUser(user);
        userValidationResult.printValidationResult();
        Scanner scanner = null;
        BufferedWriter writer = null;
        if(userValidationResult.success()) {
            try {
                scanner = new Scanner(file);
                writer = new BufferedWriter(new FileWriter(tempFile));
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    String[] split = data.split(String.valueOf(separator));
                    long currentID = Integer.parseInt(split[0]);
                    if (currentID == ID) {
                        writer.append(buildUser(ID, user)).append(System.getProperty("line.separator"));
                    } else {
                        writer.append(data).append(System.getProperty("line.separator"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                System.err.println("Incorrect data format!");
                e.printStackTrace();
            }
            finally{
                closeResourcesAndDeleteTemporaryFiles(scanner, writer, tempFile);
            }
        }
        return userValidationResult;
    }

    public void deleteUser(long ID) {
        File tempFile = createTempFile();
        Scanner scanner = null;
        BufferedWriter writer = null;
        try {
            scanner = new Scanner(file);
            writer = new BufferedWriter(new FileWriter(tempFile));
            while(scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] split = data.split(String.valueOf(separator));
                long currentID = Integer.parseInt(split[0]);
                if(currentID == ID) continue;
                writer.append(data).append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(IndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Incorrect data format!");
            e.printStackTrace();
        }
        finally {
            closeResourcesAndDeleteTemporaryFiles(scanner, writer, tempFile);
        }
    }

    private long getHighestUserID() {
        long highestID = 0;
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] split = data.split(String.valueOf(separator));
                long currentID = Integer.parseInt(split[0]);
                if(currentID > highestID) {
                    highestID = currentID;
                }
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
        } catch(IndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Incorrect data format!");
            e.printStackTrace();
        }
        return highestID;
    }

    private User parseUser(String[] data) {
        User user = new User();
        long userID = Integer.parseInt(data[0]);
        user.setUserID(userID);
        user.setName(data[1]);
        user.setSurname(data[2]);
        user.setPhoneNumber(data[3]);
        user.setEmail(data[4]);
        user.setAddress(data[5]);
        user.setPassword(data[6]);
        return user;
    }

    private String buildUser(long ID, User user) {
        StringBuilder data = new StringBuilder();
        buildUserProperty(data, String.valueOf(ID));
        buildUserProperty(data, user.getName());
        buildUserProperty(data, user.getSurname());
        buildUserProperty(data, user.getPhoneNumber());
        buildUserProperty(data, user.getEmail());
        buildUserProperty(data, user.getAddress());
        buildUserProperty(data, user.getPassword());
        return data.toString();
    }

    private void buildUserProperty(StringBuilder data, String property) {
        data.append(property);
        data.append(separator);
    }

    private File createTempFile() {
        String parent = file.getParent();
        return parent == null || parent.isEmpty() ? new File("tempFile.txt") : new File(parent + "/tempFile.txt");
    }

    private void closeResourcesAndDeleteTemporaryFiles(Scanner scanner, BufferedWriter writer, File tempFile) {
        if(scanner != null) {
            scanner.close();
        }
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("Failed to close writer!");
                e.printStackTrace();
            }
        }
        if(tempFile != null) {
            file.delete();
            tempFile.renameTo(file);
        }
    }

}
