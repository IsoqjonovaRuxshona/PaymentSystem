package org.example.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.example.exception.DataNotFoundException;
import org.example.model.Card;
import org.example.model.Transfer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class TransferRepository extends BaseRepository<Transfer>{

    private static TransferRepository transferRepository =new TransferRepository();

    public static TransferRepository getInstance() {
        return transferRepository;
    }


    public TransferRepository() {
        String format = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        super.path="src/main/resources/history/"+format+".json";
        type = Transfer.class;
    }

    @Override
    public int save(Transfer transfer) {
        String formatter = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        File file = new File("src/main/resources/history/"+formatter+".json");
        if(!file.exists()){
            try {
                file.createNewFile();
                Files.write(file.toPath(), "[]".getBytes());
                super.path = "src/main/resources/history/"+formatter+".json";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return super.save(transfer);
    }

    public Transfer findByCardId(UUID cardId) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        Optional<Transfer> any = transfers.stream().filter(transfer -> Objects.equals(transfer.getGiverId(), cardId)
                || Objects.equals(transfer.getReceiverId(), cardId)).findAny();
        if (any.isEmpty()) throw new DataNotFoundException("Data not found");
        return any.get();
    }

}
