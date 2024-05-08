package org.example.service;

import org.example.controller.Main;
import org.example.exception.AuthenticationException;
import org.example.exception.DataNotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.UserRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;


import static org.example.controller.Main.userService;

public class UserService extends BaseService<User,UserRepository> {

    private static final UserService userService = new UserService();
    private UserService() {
        super(UserRepository.getInstance());
    }
    public static UserService getInstance(){
        return userService;
    }
    @Override
    public boolean check(User user) throws DataNotFoundException {
        Optional<User> checkUser = repository.findByUsername(user.getUsername());
        return checkUser.isPresent() && checkUser.get().isActive();
    }

    public Optional<User> signIn(String username, String password)throws AuthenticationException  {
        try {
            Optional<User> logUser = repository.findByUsername(username);
            if(logUser.isEmpty()){
                throw new DataNotFoundException("data not found");
            }
            if(Objects.equals(logUser.get().getPassword(),password)){
                return logUser;
            }
            throw new AuthenticationException("Wrong username or password!");
        }catch (DataNotFoundException e){
            throw new AuthenticationException("Wrong username or password!");
        }
    }

    public ArrayList<User> getSimilarUserNamesForAdmin(String username) {
        return repository.getSimilarUserNames(username);
    }
}
