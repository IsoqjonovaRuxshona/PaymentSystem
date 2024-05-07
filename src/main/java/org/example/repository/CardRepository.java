package org.example.repository;


import org.example.exception.DataNotFoundException;
import org.example.model.Card;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CardRepository extends BaseRepository<Card>{

    private static CardRepository cardRepository =new CardRepository();

    public static CardRepository getInstance() {
        return cardRepository;
    }
    public CardRepository() {
       super.path = "src/main/resources/cards.json";
        super.type = Card.class;
    }

    public Optional<Card> findByNumber(String cardNumber) {
        ArrayList<Card> cards = getAll();
        return cards.stream().filter(card -> Objects.equals(card.getCardNumber(), cardNumber)).findAny();
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
