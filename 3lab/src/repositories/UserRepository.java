package repositories;

import model.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers();
    User getUser(long ID);
    User createUser(User user);
    void updateUser(long ID, User user);
    void deleteUser(long ID);

}
