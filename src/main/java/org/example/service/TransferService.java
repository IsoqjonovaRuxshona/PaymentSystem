package org.example.service;

import org.example.model.Transfer;
import org.example.repository.TransferRepository;

import java.util.UUID;

public class TransferService extends BaseService<Transfer, TransferRepository> {

    public TransferService() {
        super(new TransferRepository());
    }

    @Override
    public boolean check(Transfer transaction) {
        return false;
    }

    public Transfer getCardTransactions(UUID cardId) {
        return repository.findByCardId(cardId);
    }


}
