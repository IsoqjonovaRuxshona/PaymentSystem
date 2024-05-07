package org.example.controller;

import org.example.model.User;
import org.example.repository.CardRepository;
import org.example.service.CardService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner scanInt = new Scanner(System.in);
    public static Scanner scanStr = new Scanner(System.in);
    public static CardService cardService = new CardService(CardRepository.getInstance());



    public static User currentUser = null;
    public static void main(String[] args) {
     welcomeMenu();
    }

    private static void welcomeMenu() {
        while (true){
            System.out.println("""
                    1. Sign In
                    2. Sign Up
                    """);
            try {
                int command = scanInt.nextInt();
                switch (command) {
                   // case 1 -> signIn();
                   // case 2 -> signUp();
                    default -> System.out.println("No command");
                }
            } catch (InputMismatchException e) {
                scanInt = new Scanner(System.in);
                System.out.println("Error Entered?");
            }
        }
    }
}