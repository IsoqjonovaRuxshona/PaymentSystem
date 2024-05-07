package org.example.service;

import org.example.model.Transaction;
import org.example.repository.TransferRepository;

import static org.example.controller.Main.transferService;

public class TransferService extends BaseService<Transaction, TransferRepository> {
    protected TransferService() {
        super(new TransferRepository());
    }
   public static TransferService getInstance(){
        return transferService;
   }
    @Override
    public boolean check(Transaction transaction) {
        return false;
    }
}
