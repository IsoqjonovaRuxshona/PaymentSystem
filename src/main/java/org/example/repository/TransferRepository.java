package org.example.repository;

import org.example.model.Transaction;

public class TransferRepository extends BaseRpository<Transaction>{
    public TransferRepository() {
        super.path = "C:\\java\\PayMe\\src\\main\\resources\\transfers.json";
    }

}
