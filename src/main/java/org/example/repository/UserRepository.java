package org.example.repository;

import org.example.exception.DataNotFoundException;
import org.example.model.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class UserRepository extends BaseRepository<User> {


    public static final UserRepository  userRepository=new UserRepository();

    public static UserRepository getInstance() {
        return userRepository;
    }

    public UserRepository() {
        super.path="src/main/resources/users.json";
        super.type=User.class;
    }

    /* {
        path = "src/main/resources/users.json";
        type = User.class;
    }*/

    /*private static UserRepository userRepository;
    public static UserRepository getInstance() {
        if (Objects.isNull(userRepository)) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }*/

    public Optional<User> findByUsername(String username)throws DataNotFoundException {
        ArrayList<User> data = readFromFile();
        return data.stream().filter(user -> Objects.equals(user.getUsername(),username)).findFirst();
    }
}
