package org.example.repository;

import org.example.model.User;

public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
            super.path = "C:\\java\\PayMe\\src\\main\\resources\\users.json";
    }
}