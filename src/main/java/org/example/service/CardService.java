package org.example.service;

import org.example.model.Card;
import org.example.repository.CardRepository;

import static org.example.controller.Main.cardService;

public class CardService extends BaseService<Card, CardRepository> {


    protected CardService() {
        super(new CardRepository());
    }
    public static CardService getInstance(){
        return cardService;
    }
    @Override
    public boolean check(Card card) {
        return false;
    }
}
