package com.celik.mancalaapi.domain.model;

import lombok.Data;

import java.util.stream.IntStream;

@Data
public class MancalaBoard {
    public static final int INITIAL_STONES_PER_PIT = 6;
    public static final int PITS_PER_PLAYER = 6;
    public static final int TOTAL_PLAYERS = 2;
    public static final int STORES_PER_PLAYER = 2;
    public static final int TOTAL_PITS = (PITS_PER_PLAYER * TOTAL_PLAYERS) + STORES_PER_PLAYER;

    private final MancalaPit[] pits;

    public MancalaBoard() {
        pits = new MancalaPit[TOTAL_PITS];
        IntStream.range(0, TOTAL_PITS).forEach(idx ->
                pits[idx] = new MancalaPit(idx)
        );
    }


}
