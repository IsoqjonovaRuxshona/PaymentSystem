package org.example.controller;

import org.example.model.Card;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


import static org.example.controller.Main.*;

public class CardController {

    public static void cardMenu() {
        while (true) {
            System.out.println("1) Add card\t2) Delete card\t3 Read cards\t0) Exit\nEnter ->  ");
            String command = scanStr.nextLine();
            switch (command) {
                case "1" -> addCard();
                case "2" -> deleteCard();
                case "3" -> readCard();
                default -> System.out.println("No command found ❌");
                case "0" -> {
                    return;
                }
            }
        }
    }

    public static void history(){
        System.out.println("1) All\t2) Income\t3) Outcome\t0)Exit");
        String  command = scanStr.nextLine();
        switch (command){
            case "1" -> allHistoryByCard();
         //   case "2" -> IncomeHistoryByCard();
         //   case "3" -> OutcomeHistoryByCard();
         //   default -> System.out.println("No command found ❌");
            case "0" -> {
                return;
            }
        }
    }

    private static void allHistoryByCard() {
        ArrayList<Card> cards = readCard();
        System.out.println("Choose card: ");
        Card choosenCard;
        try {
            int choice = scanInt.nextInt();
            choosenCard = cards.get(choice - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong input");
            return;
        } catch (InputMismatchException e) {
            System.out.println("Enter only number :");
            scanInt = new Scanner(System.in);
            return;
        }


    }



    public static ArrayList<Card> readCard() {
        ArrayList<Card> cards = cardService.getAllActiveCardsByOwnerId(currentUser.getId());
        if(cards.isEmpty()) {
            System.out.println("No cards 🦕");
            return cards;
        }
        int i = 1;
        for (Card card : cards) {
            System.out.println(i++ + ". " + card.toString());
        }
        return cards;
    }

    private static void deleteCard() {
        ArrayList<Card> cards = cardService.getAllActiveCardsByOwnerId(currentUser.getId());
        if(cards.isEmpty()) {
            System.out.println("No cards 🦕");
            return;
        }
        int i = 1;
        for (Card card : cards) {
            System.out.println(i++ + ". " + card.toString());
        }
        System.out.print("Choose one for delete ->  ");
        Card chosenCard;
        try {
            int choose = input();
            chosenCard = cards.get(choose - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong input");
            return;
        }
        cardService.delete(chosenCard.getId());
        System.out.println(" Successfully deleted ✅");
    }

    private static void addCard() {
        System.out.print("Enter card number ->  ");
        String number = scanStr.nextLine();

        System.out.print("Enter balance: ");
        double balance = inputDouble();

        if (cardService.add(new Card(number, balance, currentUser.getId())) == -1) {
            System.out.println("\tCard already registered 🦕\n");
        } else System.out.println("\tCard successfully added ✅\n");
    }

    public static int input() {
        try {
            return scanInt.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter only number");
            scanInt = new Scanner(System.in);
            return input();
        }
    }

    public static double inputDouble() {
        try {
            return scanInt.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Enter only number");
            scanInt = new Scanner(System.in);
            return inputDouble();
        }
    }


}
