package org.example.controller;

import org.example.enumerator.Role;
import org.example.exception.AuthenticationException;
import org.example.model.User;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static org.example.controller.Main.*;

public class UserController {
    public static void signUp() {

        System.out.println("Enter name -> ");
        String name = scanStr.nextLine();

        System.out.println("Enter username -> ");
        String username = scanStr.nextLine();

        System.out.println("Enter password -> ");
        String password = scanStr.nextLine();

        if(userService.add(new User(name,username,password, Role.USER))==1){
            System.out.println(" - User successfully added");
        }else{
            System.out.println(" - Wrong username or password!");
        }
    }

   public static void signIn() {
        System.out.println("Enter username -> ");
        String username = scanStr.nextLine();

        System.out.println("Enter password -> ");
        String password = scanStr.nextLine();

        try {
            Optional<User> user = userService.signIn(username, password);
            user.ifPresent(value -> currentUser = value);
            System.out.println("Welcome " + currentUser.getName().toUpperCase() + "\n\n");
            if (Objects.equals(currentUser.getRole(), Role.ADMIN)) {
                  Main.adminMenu();
            } else mainMenu();
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}

