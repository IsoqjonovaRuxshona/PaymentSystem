package org.example.controller;

import org.example.exception.DataNotFoundException;
import org.example.model.Card;
import org.example.model.Commission;
import org.example.model.Transfer;
import org.example.service.CommissionService;
import org.example.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.example.controller.CardController.readCard;
import static org.example.controller.Main.*;

public class TransferController {
    public static void p2p() {
        System.out.print("Enter receiver number -> ");
        String receiver = scanStr.nextLine();

        Card receiverCard;
        if(cardService.getCardByNumber(receiver).isPresent()) {
            receiverCard = cardService.getCardByNumber(receiver).get();
        } else {
            System.out.println("No such card 🦕");
            return;
        }

        System.out.print("Enter amount -> ");
        double amount = scanInt.nextDouble();

        ArrayList<Card> userCards = cardService.getAllActiveCardsByOwnerId(currentUser.getId());
        if (userCards.isEmpty()) {
            System.out.println("No user's cards 🦕");
            return;
        }
        ArrayList<Card> cards = readCard(userCards);

        System.out.print("Choose card -> ");
        int choice = scanInt.nextInt() - 1;


        double commission = 0;
        try {
            Optional<Commission> byRoles = commissionService.getByRoles(receiverCard.getCardRole(), cards.get(choice).getCardRole());
            commission = byRoles.map(Commission::getCommission).orElse(0.0);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

        if (cardService.transferP2P(receiverCard, cards.get(choice), amount,commission) == 1) {
             transferService.add(new Transfer(receiverCard.getId(),cards.get(choice).getId(), amount,currentUser.getId(),
                     commission*amount/100));
            System.out.println("success");
        } else {
            System.out.println("Not enough funds");
        }
    }

    public static void history() {
        while (true) {
            System.out.println("1)\n All\t2) Income\t3) Outcome\t0)Exit");
            String command = scanStr.nextLine();
            switch (command) {
                case "1" -> allHistoryByCard();
                case "2" -> IncomeHistoryByCard();
                case "3" -> OutcomeHistoryByCard();
                case "0" -> {mainMenu();}
                default -> System.out.println("No command found ❌");
            }
        }
    }
        public static void seeAllTransfers () {
            while (true) {
                System.out.println("\n1) All\t2) In period\t 3) By user\t0) Exit");
                String s = scanStr.nextLine();
                switch (s) {
                    case "1" -> showAllTransfers();
                    case "2" -> showTransactionsInPeriod();
                    case "3" -> showTransfersByUser();
                    default -> System.out.println("No command found 🤷‍♀️");
                    case "0" -> {
                        return;
                    }
                }
            }
        }

    private static void showTransfersByUser() {
        while (true) {
            System.out.println("1)\n Search\t2) Choose\t0) Exit");
            String s = scanStr.nextLine();
            switch (s) {
                case "1" -> searchUserForShowHisTransfers();
                case "2" -> chooseUserForShowHisTransfers();
                default -> System.out.println("No command found 🤷‍♀️");
                case "0" -> {
                    return;
                }
            }
        }
    }

    private static void chooseUserForShowHisTransfers() {
    ArrayList<User> users = userService.getAllUsers();
    if(users.isEmpty()) {
        System.out.println("No users 🦕");
        return;
    }
    int i = 1;
        for (User user : users) {
            System.out.println(i++ + ") " + user.getUsername());
        }
        System.out.println("\nChoose user ->  ");
        User chosenUser;
        try{
            int c = input();
            chosenUser = users.get(c - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong input ❌");
            return;
        }
        ArrayList<Transfer> userTransfers = transferService.getTransfersByUserId(chosenUser.getId());
        if(userTransfers.isEmpty()) {
            System.out.println("No outcome transfers!\n\t(P.S. this method shows only outcome transactions) ↗");
            return;
        }
        outputTransfers(userTransfers);
        System.out.println("\n\t(P.S. this method shows only outcome transactions) ↗");
    }

    private static void searchUserForShowHisTransfers() {
        System.out.print("Enter username ->  ");
        String username = scanStr.nextLine();
        ArrayList<User> users = userService.getSimilarUserNamesForAdmin(username);
        if(users.isEmpty()) {
            System.out.println("No such users 🦕");
            return;
        }
       int i = 1;
        for (User user : users) {
            System.out.println(i++ + ". " + user.getUsername());
        }
        System.out.print("Choose user ->  ");
        User chosenUser;
        try{
            int c = input();
            chosenUser = users.get(c - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong input ❌");
            return;
        }
       ArrayList<Transfer> userTransfers = transferService.getTransfersByUserId(chosenUser.getId());
        if(userTransfers.isEmpty()) {
            System.out.println("No outcome transfers!\n\t(P.S. this method shows only outcome transactions) ↗");
            return;
        }
        outputTransfers(userTransfers);
        System.out.println("\n\t(P.S. this method shows only outcome transactions) ↗");
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

    private static void OutcomeHistoryByCard () {
            ArrayList<Card> cards = cardService.getAllActiveCardsByOwnerId(currentUser.getId());
            if(cards.isEmpty()) {
                System.out.println("No cards 🦕");
                return;
            }
            readCard(cards);
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
            ArrayList<Transfer> cardTransaction = transferService.getOutcomeTransferByCard(choosenCard.getId());
            if (cardTransaction.isEmpty()) {
                System.out.println("No outcome transaction in this card 🦕");
            }
            outputTransfers(cardTransaction);
        }

        private static void IncomeHistoryByCard () {
            ArrayList<Card> cards = cardService.getAllActiveCardsByOwnerId(currentUser.getId());
            if(cards.isEmpty()) {
                System.out.println("No cards 🦕");
                return;
            }
            readCard(cards);
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
            ArrayList<Transfer> cardTransaction = transferService.getIncomeTransactionByCard(choosenCard.getId());
            if (cardTransaction.isEmpty()) {
                System.out.println("No income transactions in this card 🦕");
            }
            outputTransfers(cardTransaction);
        }


        private static void showAllTransfers () {
            ArrayList<Transfer> all = transferService.getAll();
            if (all.isEmpty()) {
                System.out.println("No transactions 🦕");
                return;
            }
            outputTransfers(all);
        }

        public static void outputTransfers (ArrayList < Transfer > arrayList) {
            int i = 1;
            for (Transfer transaction : arrayList) {
                try {
                    User user1 = userService.findById(transaction.getSenderPersonId());
                    System.out.println(i++ + ". Sender <" + user1.getUsername() + ">" +
                            "  (amount = " + transaction.getAmount() + ")\t\tto ➡️  [" + transaction.getReceiverId() + "]");
                }catch (DataNotFoundException e){
                    System.out.println("..........");
                }

            }
        }


        private static void allHistoryByCard () {
            ArrayList<Card> cards = cardService.getAllActiveCardsByOwnerId(currentUser.getId());
            if(cards.isEmpty()) {
                System.out.println("No cards 🦕");
                return;
            }
            readCard(cards);
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
            ArrayList<Transfer> cardTransactions = transferService.getAllUsersTransfersByCard(choosenCard.getId());
            if (cardTransactions.isEmpty()) {
                System.out.println(" No transaction in this card 🦕");
                return;
            }
            outputTransfers(cardTransactions);
        }


        public static void showTransactionsInPeriod () {
            System.out.print("Enter first date (dd-MM-yyyy) ->  ");
            LocalDate locDate1 = inputForParseException();

            System.out.print("Enter second date (dd-MM-yyyy) ->  ");
            LocalDate locDate2 = inputForParseException();

            ArrayList<Transfer> transactions = transferService.getAllByPeriod(locDate1, locDate2);
            if (transactions.isEmpty()) {
                System.out.println("No transactions 🦕");
                return;
            }
            outputTransfers(transactions);
        }

        public static LocalDate inputForParseException () {
            try {
                String parse = scanStr.nextLine();
                return LocalDate.parse(parse, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("Wrong format");
                scanStr = new Scanner(System.in);
                System.out.print("Enter again (dd-MM-yyyy) ->  ");
                return inputForParseException();
            }
        }
    }