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

}
