package org.example.repository;

import org.example.exception.DataNotFoundException;
import org.example.model.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository extends BaseRepository<User> {


    public static final UserRepository  userRepository=new UserRepository();

    public static UserRepository getInstance() {
        return userRepository;
    }

    public UserRepository() {
        super.path="src/main/resources/users.json";
        super.type=User.class;
    }



    public Optional<User> findByUsername(String username)throws DataNotFoundException {
        ArrayList<User> data = readFromFile();
        return data.stream().filter(user -> Objects.equals(user.getUsername(),username)).findFirst();
    }

    public ArrayList<User> getSimilarUserNames(String username) {
        ArrayList<User> allUsers = getAll();
        return allUsers.stream().filter(user -> user.getUsername().contains(username)).collect(Collectors.toCollection(ArrayList::new));
    }
}
