package com.celik.mancalaapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MancalaGameState {
    private String gameId;
    private List<Integer> pits;
    private int player1BigPit;
    private List<Integer> player2Pits;
    private int player2BigPit;
    private String currentPlayer;

    //public MancalaGameState(MancalaGame game) {
    //
    //}

}
