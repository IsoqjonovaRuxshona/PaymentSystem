package org.example.repository;

import org.example.exception.DataNotFoundException;
import org.example.model.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
            super.path = "C:\\java\\PayMe\\src\\main\\resources\\users.json";
    }

    public Optional<User> findByUsername(String username)throws DataNotFoundException {
        ArrayList<User> data = readFromFile();
        return data.stream().filter(user -> Objects.equals(user.getUsername(),username)).findFirst();
    }
}
