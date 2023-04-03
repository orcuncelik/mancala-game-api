package com.celik.mancalaapi.domain.model;

import lombok.Data;

import java.util.stream.IntStream;

@Data
public class MancalaBoard {
    public static final int INITIAL_STONES_PER_PIT = 6;
    public static final int PITS_PER_PLAYER = 6;
    public static final int TOTAL_PLAYERS = 2;
    public static final int STORES_PER_PLAYER = 2;

    private final MancalaPit[] pits;

    public MancalaBoard() {
        int totalPits = (PITS_PER_PLAYER * TOTAL_PLAYERS) + STORES_PER_PLAYER;
        pits = new MancalaPit[totalPits];
        IntStream.range(0, totalPits).forEach(idx ->
                pits[idx] = new MancalaPit(idx)
        );
    }


}
