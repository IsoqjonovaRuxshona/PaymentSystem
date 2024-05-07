package org.example.service;

import org.example.model.Card;
import org.example.repository.CardRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class CardService extends BaseService<Card, CardRepository> {
    public CardService() {
        super(new CardRepository());
    }

    @Override
    public boolean check(Card card) {
        return false;
    }

     public int transferP2P(Card receiver, Card sender, Double amount) {
        if (sender.getBalance() < amount) {
            return -1;
        }
        sender.setBalance(sender.getBalance() - amount + amount*0.01);
        receiver.setBalance(receiver.getBalance() + amount);
        return 1;
    }

    //controllerdan chaqiraman bu metodni;
    public Card getCardByNumber(String number) {
        return repository.findByNumber(number);
    }
    public CardService(CardRepository repository) {
        super(repository);
    }

    @Override
    public boolean check(Card card) {
        Optional<Card> byNumber = repository.findByNumber(card.getCardNumber());
        return byNumber.isPresent();
    }

    public ArrayList<Card> getAllActiveCardsByOwnerId(UUID ownerId) {
        return repository.getAllActivesByOwnerId(ownerId);
    }

    public Optional<Card> getByNumber(String number) {
        return repository.findByNumber(number);
    }
}
