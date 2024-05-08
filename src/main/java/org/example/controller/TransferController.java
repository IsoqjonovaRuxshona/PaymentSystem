package org.example.controller;

import org.example.model.Card;
import org.example.model.Transfer;
import org.example.service.CommissionService;

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

        ArrayList<Card> cards = readCard();

        System.out.print("Choose card -> ");
        int choice = scanInt.nextInt() - 1;


        Double commission = commissionService.getByRoles(receiverCard.getCardRole(),cards.get(choice).getCardRole());
        if (cardService.transferP2P(receiverCard, cards.get(choice), amount,commission) == 1) {
             transferService.add(new Transfer(receiverCard.getId(),cards.get(choice).getId(), amount));
            System.out.println("success");
        } else {
            System.out.println("Not enough funds");
        }
    }
}
