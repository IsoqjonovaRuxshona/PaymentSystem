package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.example.enumerator.CardRole;

import java.time.LocalDateTime;
import java.util.UUID;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Card extends BaseModel{
    private String cardNumber;
    private Double balance;
    private UUID ownerId;
    private CardRole cardRole;



   public String toString() {
        return
                "number (" + cardNumber + ")  " +
                        "balance -> " + balance + "\n" +
                                "carRole -> "  + cardRole + "\n";
    }
}
