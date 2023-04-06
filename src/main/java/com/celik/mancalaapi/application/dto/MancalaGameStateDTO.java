package com.celik.mancalaapi.application.dto;

import com.celik.mancalaapi.domain.model.enums.MancalaGameStatus;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MancalaGameStateDTO {
    private String gameId;
    private List<Integer> player1Pits;
    private int player1BigPit;
    private List<Integer> player2Pits;
    private int player2BigPit;
    private MancalaGameStatus gameStatus;
    private MancalaPlayerType currentPlayer;
}