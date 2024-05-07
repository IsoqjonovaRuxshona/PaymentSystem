package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;

public class UserService extends BaseService<User,UserRepository> {

    public UserService() {
        super(new UserRepository());
    }

    @Override
    public boolean check(User user) {
        return false;
    }
}
