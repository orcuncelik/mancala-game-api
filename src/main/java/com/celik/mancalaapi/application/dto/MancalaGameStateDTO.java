package com.celik.mancalaapi.application.dto;

import com.celik.mancalaapi.domain.model.enums.MancalaGameStatus;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
public class MancalaGameStateDTO {
    private UUID gameId;
    private List<Integer> player1Pits;
    private int player1BigPit;
    private List<Integer> player2Pits;
    private int player2BigPit;
    private MancalaGameStatus gameStatus;
    private MancalaPlayerType currentPlayer;
}