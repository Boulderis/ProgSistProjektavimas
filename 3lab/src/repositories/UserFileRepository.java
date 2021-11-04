package repositories;

import model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserFileRepository implements UserRepository {

    private File file;

    private char separator = '~';

    public UserFileRepository(File file) {
        this.file = file;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] split = data.split(String.valueOf(separator));
                User user = parseUser(split);
                users.add(user);
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            System.err.println("File not found!");
        } catch(IndexOutOfBoundsException e) {
            System.err.println("Incorrect data format!");
            e.printStackTrace();
        }
        return users;
    }

    public User getUser(long ID) {
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] split = data.split(String.valueOf(separator));
                long currentID = Integer.parseInt(split[0]);
                if(currentID == ID) {
                    User user = parseUser(split);
                    return user;
                }
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
        } catch(IndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Incorrect data format!");
            e.printStackTrace();
        }
        return null;
    }

    public User createUser(User user) {
        long ID = getHighestUserID() + 1;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(buildUser(ID, user) + System.getProperty("line.separator"));
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
        return user;
        // patobulinti.
    }

    public void updateUser(long ID, User user) {
        File tempFile = new File(file.getParent() + "/tempFile.txt");
        try {
            Scanner scanner = new Scanner(file);
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            while(scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] split = data.split(String.valueOf(separator));
                long currentID = Integer.parseInt(split[0]);
                if(currentID == ID) {
                    writer.write(buildUser(ID, user) + System.getProperty("line.separator"));
                }
                else {
                    writer.write(data + System.getProperty("line.separator"));
                }
            }
            writer.close();
            scanner.close();
            file.delete();
            tempFile.renameTo(file);
            file = tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        } catch(IndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Incorrect data format!");
            e.printStackTrace();
        }
    }

    public void deleteUser(long ID) {
        File tempFile = new File(file.getParent() + "/tempFile.txt");
        try {
            Scanner scanner = new Scanner(file);
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            while(scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] split = data.split(String.valueOf(separator));
                long currentID = Integer.parseInt(split[0]);
                if(currentID == ID) continue;
                writer.write(data + System.getProperty("line.separator"));
            }
            writer.close();
            scanner.close();
            file.delete();
            tempFile.renameTo(file);
            file = tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        } catch(IndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Incorrect data format!");
            e.printStackTrace();
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

}
