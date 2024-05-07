package org.example.service;

import org.example.exception.AuthenticationException;
import org.example.exception.DataNotFoundException;
import org.example.model.User;
import org.example.repository.BaseRpository;
import org.example.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

public class UserService extends BaseService<User,UserRepository> {

    public int add(User user) {

    }

    public Optional<User> signIn(String username, String password)throws AuthenticationException {
        try {
            Optional<User> logUser = repository.findByUsername(username);
            if(logUser.isEmpty()){
                throw new DataNotFoundException("data not found")
            }
            if(Objects.equals(logUser.get().getPassword(),password){
                return logUser;
            }
            throw new AuthenticationException("Wrong username or password!");
        }catch (DataNotFoundException e){
            throw new AuthenticationException("Wrong username or password!");
        }
    }
}
