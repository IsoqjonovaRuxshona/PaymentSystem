package org.example.service;

import org.example.model.Card;
import org.example.repository.CardRepository;

public class CardService extends BaseService<Card, CardRepository> {
    public CardService() {
        super(new CardRepository());
    }

}
