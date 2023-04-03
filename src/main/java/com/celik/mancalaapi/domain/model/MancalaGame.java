package com.celik.mancalaapi.domain.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MancalaGame {
    private final String id;
    private final MancalaBoard board;
}
