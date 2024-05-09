package org.example.service;

import org.example.model.Transfer;
import org.example.repository.TransferRepository;
import org.example.repository.TransferRepository;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.UUID;

public class TransferService extends BaseService<Transfer, TransferRepository> {

    public static final TransferService transferService = new TransferService();

    public static TransferService getInstance() {
        return transferService;
    }

    public TransferService() {
        super(TransferRepository.getInstance());
    }

    @Override
    public boolean check(Transfer transaction) {
        return false;
    }

    public Transfer getCardTransactions(UUID cardId) {
        return repository.findByCardId(cardId);
    }


    public ArrayList<Transfer> getAllUsersTransfersByCard(UUID cardId) {
        return repository.getAllUserTransfersByCard(cardId);
    }

    public ArrayList<Transfer> getIncomeTransactionByCard(UUID cardId) {
        return repository.getIncomeTransferByCard(cardId);
    }

    public ArrayList<Transfer> getOutcomeTransferByCard(UUID cardId) {
        return repository.getOutcomeTransferByCard(cardId);
    }
        public ArrayList<Transfer> getAll() {
            return repository.getAll();
        }
        public ArrayList<Transfer> getAllByPeriod (LocalDate first, LocalDate second){
            return repository.getByPeriod(first, second);
        }
        public ArrayList<Transfer> getTransfersByUserId(UUID userId) {
        return repository.getTransfersByOwnerId(userId);
        }

        public ArrayList<Transfer> transfersInLastWeekByUserId(UUID userId) {
        return repository.transfersInLastWeekByUser(userId);
        }
    }
