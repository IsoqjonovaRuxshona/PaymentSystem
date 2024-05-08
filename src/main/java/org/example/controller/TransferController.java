package org.example.controller;

import org.example.exception.DataNotFoundException;
import org.example.model.Card;
import org.example.model.Transfer;
import org.example.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

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
        if(userCards.isEmpty()) {
            System.out.println("No user's cards 🦕");
            return;
        }
        ArrayList<Card> cards = readCard(userCards);

        System.out.print("Choose card -> ");
        int choice = scanInt.nextInt() - 1;


        if (cardService.transferP2P(receiverCard, cards.get(choice), amount) == 1) {
             transferService.add(new Transfer(receiverCard.getId(),cards.get(choice).getId(), amount, currentUser.getId()));
            System.out.println("success");
        } else {
            System.out.println("Not enough funds");
        }
    }
  
    public static void history() {
        while (true) {
            System.out.println("1) All\t2) Income\t3) Outcome\t0)Exit");
            String command = scanStr.nextLine();
            switch (command) {
                case "1" -> allHistoryByCard();
                case "2" -> IncomeHistoryByCard();
                case "3" -> OutcomeHistoryByCard();
                default -> System.out.println("No command found ❌");

    public static void seeAllTransfers() {
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

    private static void OutcomeHistoryByCard() {
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
        ArrayList<Transfer> cardTransaction = transferService.getOutcomeTransferByCard(choosenCard.getId());
        if(cardTransaction.isEmpty()){
            System.out.println("No outcome transaction in this card 🦕");
        }
        outputTransfers(cardTransaction);
    }

    private static void IncomeHistoryByCard() {
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
        ArrayList<Transfer> cardTransaction = transferService.getIncomeTransactionByCard(choosenCard.getId());
        if(cardTransaction.isEmpty()){
            System.out.println("No income transactions in this card 🦕");
        }
        outputTransfers(cardTransaction);
      
  

    private static void showAllTransfers() {
        ArrayList<Transfer> all = transferService.getAll();
        if(all.isEmpty()) {
            System.out.println("No transactions 🦕");
            return;
        }
            outputTransfers(all);
    }
      
    public static void outputTransfers(ArrayList<Transfer> arrayList)  {
        int i = 1;
        for (Transfer transaction : arrayList) {
            User user1 = userService.findById(transaction.getSenderPersonId());
            System.out.println(i++ + ". Sender <" + user1.getUsername() + ">" +
                    "  (amount = " + transaction.getAmount() + ")\t\tto ➡️  [" + transaction.getReceiverId() + "]");
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
        ArrayList<Transfer> cardTransactions = transferService.getAllUsersTransfersByCard(choosenCard.getId());
        if(cardTransactions.isEmpty()){
            System.out.println(" No transaction in this card 🦕");
            return;
        }
        outputTransfers(cardTransactions);
    }


    public static void showTransactionsInPeriod() {
        System.out.print("Enter first date (dd-MM-yyyy) ->  ");
        LocalDate locDate1 = inputForParseException();

        System.out.print("Enter second date (dd-MM-yyyy) ->  ");
        LocalDate locDate2 = inputForParseException();

        ArrayList<Transfer> transactions = transferService.getAllByPeriod(locDate1, locDate2);
        if(transactions.isEmpty()) {
            System.out.println("No transactions 🦕");
            return;
        }
        outputTransfers(transactions);
    }

    public static LocalDate inputForParseException() {
        try {
            String parse = scanStr.nextLine();
            return LocalDate.parse(parse,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Wrong format");
            scanStr = new Scanner(System.in);
            System.out.print("Enter again (dd-MM-yyyy) ->  ");
            return inputForParseException();
        }
    }
}
