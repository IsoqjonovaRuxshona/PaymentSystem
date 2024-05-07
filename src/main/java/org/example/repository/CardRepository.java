package org.example.repository;


import org.example.exception.DataNotFoundException;
import org.example.model.Card;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class CardRepository extends BaseRepository<Card>{
    public Card findByNumber(String cardNumber) {
        ArrayList<Card> cards = new ArrayList<>();
        Optional<Card> any = cards.stream().filter(card -> Objects.equals(card.getCardNumber(), cardNumber)).findAny();
        if(any.isEmpty()) throw  new DataNotFoundException("Data not found");
        return any.get();
        }

    public CardRepository() {
        super.path = "src/main/resources/cards.json";
        type = Card.class;
    }

    private static CardRepository cardRepository;


    public static CardRepository getInstance() {
        if (Objects.isNull(cardRepository)) {
            cardRepository = new CardRepository();
        }
        return cardRepository;
    }

    public Optional<Card> findByNumber(String number) {
        ArrayList<Card> data = super.getAll();
        for (Card card : data) {
            if(Objects.equals(card.getCardNumber(), number)) {
                return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    public ArrayList<Card> getAllActivesByOwnerId(UUID ownerId) {
        ArrayList<Card> data = super.getAll();
        ArrayList<Card> result = new ArrayList<>();
        for (Card card : data) {
            if(Objects.equals(card.getOwnerId(), ownerId) && card.isActive()) {
                result.add(card);
            }
        }
        return result;
    }

}
