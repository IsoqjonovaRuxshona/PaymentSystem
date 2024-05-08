package org.example.service;

import org.example.model.Transfer;
import org.example.repository.TransferRepository;
import org.example.repository.TransferRepository;

import java.util.ArrayList;
import java.util.UUID;

public class TransferService extends BaseService<Transfer, TransferRepository> {

    public  static final TransferService  transferService=new TransferService();

    public static TransferService getInstance() {
        return transferService;
    }

    public TransferService() {
        super(TransferRepository.getInstance());
    }

    /*public static final TransferService transferService=new TransferService();
    private TransferService() {
        super(TransferRepository.getInstance());
    }
    public static TransferService getInstance(){
        return transferService;
    }*/

    @Override
    public boolean check(Transfer transaction) {
        return false;
    }

    public ArrayList<Transfer> getCardTransactions(UUID cardId) {
        return repository.findByCardId(cardId);
    }


}
