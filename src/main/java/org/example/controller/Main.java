package org.example.controller;

import org.example.enumerator.Role;
import org.example.model.Card;
import org.example.model.User;
import org.example.repository.TransferRepository;
import org.example.service.CardService;
import org.example.service.CommissionService;
import org.example.service.TransferService;
import org.example.service.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

import static org.example.controller.CardController.cardMenu;
import static org.example.controller.CommissionConteroller.changeComission;
import static org.example.controller.TransferController.*;
import static org.example.controller.UserController.signIn;
import static org.example.controller.UserController.signUp;


public class Main {
    public static Scanner scanInt = new Scanner(System.in);
    public static Scanner scanStr = new Scanner(System.in);
    public static UserService userService = UserService.getInstance();
    public static TransferService transferService = TransferService.getInstance();
    public static CardService cardService = CardService.getInstance();
    public static CommissionService commissionService = CommissionService.getInstance();
    public static User currentUser;

    static {
//        cardService.add(new Card("45678", 770000D, UUID.randomUUID()));
//        cardService.add(new Card("8887776", 312000D, UUID.randomUUID()));
          userService.add(new User("admin", "admin", "55555", Role.ADMIN));

    }
    public static void main(String[] args) {

     welcomeMenu();
     //   p2p();
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
                    case 1 -> signIn();
                    case 2 -> signUp();
                    default -> System.out.println("Wrong input ❌");
                }
            } catch (InputMismatchException e) {
                scanInt = new Scanner(System.in);
                System.out.println("Error Entered?");
            }
        }
    }

    public static void mainMenu() {
        while (true) {
            System.out.println("""
                    1.CRUD
                    2.P2P
                    3.History
                    0.Exit
                    """);
            try {
                int command = scanInt.nextInt();
                switch (command) {
                    case 1 -> cardMenu();
                    case 2 -> p2p();
                    case 3 -> history();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("No command");
                }
            } catch (InputMismatchException e) {
                scanInt = new Scanner(System.in);
                System.out.println("Error Entered?");
            }
        }

    }

    public static void adminMenu(){
        System.out.println("""
                1.See all transfer
                2.. Change comission
                3..  Top 5 users with Outcoming tranfer               
                """);
        try {
            int command = scanInt.nextInt();
            switch (command) {
                case 1 -> seeAllTransfers();
                case 2 -> changeComission();
            //   case 3 -> top5UsersWithOutcomingTransfer();
                case 0 -> {return;}
                default -> System.out.println("No command");
            }
        } catch (InputMismatchException e) {
            scanInt = new Scanner(System.in);
            System.out.println("Error Entered?");
        }
    }


}
