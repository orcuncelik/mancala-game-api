package com.celik.mancalaapi.domain.model;

import lombok.Data;

import java.util.stream.IntStream;

@Data
public class MancalaBoard {

    private final MancalaPit[] pits;

    public MancalaBoard(int numberOfPits, int initialStonesPerPit) {
        pits = new MancalaPit[numberOfPits];
        IntStream.range(0, numberOfPits + 2).forEach(i ->
            pits[i] = new MancalaPit(initialStonesPerPit)
        );
    }
}
