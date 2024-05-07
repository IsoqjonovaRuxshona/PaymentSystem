package org.example.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.example.exception.DataNotFoundException;
import org.example.model.Card;
import org.example.model.Transfer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class TransferRepository extends BaseRepository<Transfer>{
    static LocalDate nowDate;
    static {
       nowDate = LocalDate.now();
       path = "src\\main\\resources\\history\\transactions.json";
    }

    {
        if (LocalDate.now().isBefore(nowDate.plusDays(1))){
            nowDate = LocalDate.now();
            path = "src\\main\\resources\\history" + nowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".json";
        }
    }
    public TransferRepository() {
        type = Transfer.class;
    }




    public Transfer findByCardId(UUID cardId) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        Optional<Transfer> any = transfers.stream().filter(transfer -> Objects.equals(transfer.getGiverId(), cardId) || Objects.equals(transfer.getReceiverId(), cardId)).findAny();
        if (any.isEmpty()) throw new DataNotFoundException("Data not found");
        return any.get();
    }

}
