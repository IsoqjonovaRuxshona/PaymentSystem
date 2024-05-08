package org.example.repository;


import org.example.model.Card;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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



    public void transferUpdate(Card card1, Card card2) {
        ArrayList<Card> cards = getAll();
        List<Card> list = cards.stream().filter(card -> !Objects.equals(card.getCardNumber(), card1)
                        && !Objects.equals(card.getCardNumber(), card2))
                .collect(Collectors.toList());
        list.add(card1);
        list.add(card2);
        writeToFile(list);

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
