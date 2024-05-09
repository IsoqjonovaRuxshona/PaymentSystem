package org.example.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.example.exception.DataNotFoundException;
import org.example.model.Transfer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.stream.Collectors;

import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class TransferRepository extends BaseRepository<Transfer>{

    private static final TransferRepository transferRepository =new TransferRepository();

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
                super.path = "src/main/resources/history/" + formatter + ".json";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return super.save(transfer);
    }

    public ArrayList<Transfer> findByCardId(UUID cardId) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        Optional<Transfer> any = transfers.stream().filter(transfer -> Objects.equals(transfer.getGiverId(), cardId)
                || Objects.equals(transfer.getReceiverId(), cardId)).findAny();
        if (any.isEmpty()) throw new DataNotFoundException("Data not found");
        any.get();
        return transfers;
    }

    public ArrayList<Transfer> getAll() {
        ArrayList<Transfer> transactions = new ArrayList<>();
        try {
            Files.list(Paths.get("src/main/resources/history")).filter(Files::isRegularFile).forEach(file -> {
                try {
                    List<Transfer> transfersFromFile = objectMapper.readValue(file.toFile(), new TypeReference<List<Transfer>>() {
                    });
                    transactions.addAll(transfersFromFile);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading transactions for period", e);
        }
        return transactions;
    }

    public ArrayList<Transfer> getAllUserTransfersByCard(UUID cardId) {
        ArrayList<Transfer> arrayList = getAll();
        return arrayList.stream().filter(transfer -> Objects.equals(transfer.getReceiverId(), cardId)
                || Objects.equals(transfer.getGiverId(), cardId)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Transfer> getOutcomeTransferByCard(UUID cardId) {
        ArrayList<Transfer> arrayList = getAll();
        return arrayList.stream().filter(transfer -> Objects.equals(transfer.getGiverId(), cardId)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Transfer> getIncomeTransferByCard(UUID cardId) {
        ArrayList<Transfer> arrayList = getAll();
        return arrayList.stream().filter(transfer -> Objects.equals(transfer.getReceiverId(), cardId)).collect(Collectors.toCollection(ArrayList::new));
    }

        public ArrayList<Transfer> getByPeriod (LocalDate startDate, LocalDate endDate){
            ArrayList<Transfer> transactions = new ArrayList<>();
            File directory = new File("src/main/resources/history");
            File[] files = directory.listFiles();
            Arrays.stream(files).forEach((file) -> {
                LocalDate transactionDate = extractDateFromFile(file);
                if (isWithinPeriod(transactionDate, startDate, endDate)) {
                    try {
                        transactions.addAll(objectMapper.readValue(file, new TypeReference<List<Transfer>>() {
                        }));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return transactions;
        }

        private LocalDate extractDateFromFile(File file){
            String dateString = file.getName().substring(0, 10);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(dateString, formatter);
        }

        private boolean isWithinPeriod(LocalDate transactionDate, LocalDate startDate, LocalDate endDate){
            return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
        }

        public ArrayList<Transfer> getTransfersByOwnerId(UUID userId) {
        ArrayList<Transfer> transferArrayList = getAll();
        return transferArrayList.stream().filter(transfer -> Objects.equals(transfer.getSenderPersonId(), userId)).
                collect(Collectors.toCollection(ArrayList::new));
        }
    }

