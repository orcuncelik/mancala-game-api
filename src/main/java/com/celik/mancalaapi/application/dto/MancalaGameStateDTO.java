package com.celik.mancalaapi.application.dto;

import com.celik.mancalaapi.domain.model.MancalaBoard;
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
    private String currentPlayer;
}