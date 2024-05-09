package org.example.controller;

import org.example.enumerator.CardRole;
import org.example.model.Card;
import org.example.model.Transfer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


import static org.example.controller.Main.*;
import static org.example.controller.TransferController.outputTransfers;

public class CardController {

    public static void cardMenu() {
        while (true) {
            System.out.println("1) Add card\t2) Delete card\t3 Read cards\t0) Exit\nEnter ->  ");
            String command = scanStr.nextLine();
            switch (command) {
                case "1" -> addCard();
                case "2" -> deleteCard();
                case "3" -> readCard( cardService.getAllActiveCardsByOwnerId(currentUser.getId()));
                default -> System.out.println("No command found ❌");
                case "0" -> {
                    return;
                }
            }
        }
    }


    public static ArrayList<Card> readCard(ArrayList<Card> cardArrayList) {
        int i = 1;
        for (Card card : cardArrayList) {
            System.out.println(i++ + ". " + card.toString());
        }
        return cardArrayList;
    }

    private static void deleteCard() {
        ArrayList<Card> cards = cardService.getAllActiveCardsByOwnerId(currentUser.getId());
        if(cards.isEmpty()) {
            System.out.println("No cards 🦕");
            return;
        }
       readCard(cards);
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
        System.out.println("""
                1.HUMO
                2.UZCARD
                3.VISA
                """);
        String command = scanStr.nextLine();
        CardRole role = null;
        switch (command) {
            case "1" -> {
                role = CardRole.HUMO;
            }
            case "2" -> {
                role = CardRole.UZCARD;
            }
            case "3" -> {
                role = CardRole.VISA;
            }
            default -> System.out.println("Wrong input");
        }
        System.out.print("Enter card number ->  ");
        String number = scanStr.nextLine();

        System.out.print("Enter balance: ");
        double balance = inputDouble();

        if (cardService.add(new Card(number, balance, currentUser.getId(),role)) == -1) {
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
