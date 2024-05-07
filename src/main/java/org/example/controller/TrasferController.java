package org.example.controller;

import org.example.model.Card;

import java.util.ArrayList;

import static org.example.Main.*;

public class TrasferController {
    public static void p2p() {
        System.out.print("Enter receiver number: ");
        String receiver = scanStr.nextLine();

        Card receiverCard = cardService.getCardByNumber(receiver);
        if (receiverCard == null) {
            System.out.println("Card not found");
            return;
        }

        System.out.print("Enter amount: ");
        double amount = scanInt.nextDouble();

       // Card[] cards = showUserCards();
        System.out.print("Choose card: ");
        int choice = scanInt.nextInt() - 1;



  /*      if (cardService.transferP2P(receiverCard, cards[choice], amount) == 1) {
            transactionService.add(new Transaction(cards[choice].getId(), receiverCard.getId(), amount));
            System.out.println("success");
        } else {
            System.out.println("Not enough funds");
        }*/
    }
}
