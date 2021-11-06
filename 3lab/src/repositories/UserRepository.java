package repositories;

import models.User;
import validators.UserValidationResult;

import java.util.List;

public interface UserRepository {

    List<User> getUsers();
    User getUser(long ID);
    UserValidationResult createUser(User user);
    UserValidationResult updateUser(long ID, User user);
    void deleteUser(long ID);

}
