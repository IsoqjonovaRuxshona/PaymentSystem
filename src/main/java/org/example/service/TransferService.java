package org.example.service;

import org.example.model.Card;
import org.example.model.Transaction;
import org.example.repository.TransferRepository;

public class TransferService extends BaseService<Transaction, TransferRepository> {

    public TransferService() {
        super(new TransferRepository());
    }

   /* public int transferP2P(Card receiver, Card sender, Double amount) {
        if (sender.getBalance() < amount) {
            return -1;
        }
        sender.setBalance(sender.getBalance() - amount + amount*0.01);
        receiver.setBalance(receiver.getBalance() + amount);
        return 1;
    }*/


}
