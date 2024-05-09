package org.example.model;

import lombok.*;
import org.example.enumerator.CardRole;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Commission extends BaseModel{

    private CardRole senderCardRole;
    private CardRole reseiverCardRole;
    private double commission;
}
