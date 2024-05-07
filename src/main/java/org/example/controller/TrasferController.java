package org.example.controller;

import org.example.model.Card;
import org.example.model.Transfer;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.example.controller.CardController.readCard;
import static org.example.controller.Main.*;

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

        ArrayList<Card> cards = readCard();

        System.out.print("Choose card: ");
        int choice = scanInt.nextInt() - 1;


        if (cardService.transferP2P(receiverCard, cards.get(choice), amount) == 1) {
             transferService.add(new Transfer(receiverCard.getId(),cards.get(choice).getId(), amount));
            System.out.println("success");
        } else {
            System.out.println("Not enough funds");
        }
    }
}
