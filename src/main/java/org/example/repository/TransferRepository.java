package org.example.repository;

import org.example.exception.DataNotFoundException;
import org.example.model.Card;
import org.example.model.Transfer;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class TransferRepository extends BaseRepository<Transfer>{
    public TransferRepository() {
        super.path = "C:\\java\\PayMe\\src\\main\\resources\\transfers.json";
    }

    public Transfer findByCardId(UUID cardId) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        Optional<Transfer> any = transfers.stream().filter(transfer -> Objects.equals(transfer.getGiverId(), cardId) || Objects.equals(transfer.getReceiverId(), cardId)).findAny();
        if (any.isEmpty()) throw new DataNotFoundException("Data not found");
        return any.get();
    }

}
