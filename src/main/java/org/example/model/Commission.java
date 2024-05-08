package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enumerator.CardRole;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commission extends BaseModel{

    private CardRole senderCardRole;
    private CardRole reseiverCardRole;
    private Double commission;
}
