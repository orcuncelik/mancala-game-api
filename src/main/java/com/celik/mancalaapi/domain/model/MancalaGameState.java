package com.celik.mancalaapi.domain.model;

import com.celik.mancalaapi.domain.model.enums.MancalaGameStatus;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Data
public class MancalaGameState {
    private UUID gameId;
    private List<Integer> player1Pits;
    private int player1BigPit;
    private List<Integer> player2Pits;
    private int player2BigPit;
    private MancalaGameStatus gameStatus;
    private MancalaPlayerType currentPlayer;
}
