package com.celik.mancalaapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MancalaPit {
    private int stones;
    private MancalaPitType type;
}
