package org.example.service;

import org.example.model.Card;
import org.example.repository.CardRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.example.controller.Main.cardService;
;

public class CardService extends BaseService<Card, CardRepository> {

   public  static final CardService cardService=new CardService();

    public static CardService getInstance() {
        return cardService;
    }

    public CardService() {
        super(CardRepository.getInstance());
    }

    @Override
    public boolean check(Card card) {
        Optional<Card> byNumber = repository.findByNumber(card.getCardNumber());
        return byNumber.isPresent();
    }

     public int transferP2P(Card receiver, Card sender, Double amount) {
        if (sender.getBalance() < amount) {
            return -1;
        }
        sender.setBalance(sender.getBalance() - amount + amount * 0.01);
        receiver.setBalance(receiver.getBalance() + amount);
        return 1;
    }
    public Optional<Card> getCardByNumber(String number) {
        return repository.findByNumber(number);
    }

    public ArrayList<Card> getAllActiveCardsByOwnerId(UUID ownerId) {
        return repository.getAllActivesByOwnerId(ownerId);
    }


}
