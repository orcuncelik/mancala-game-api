package com.celik.mancalaapi.infrastructure.repository.mapper;

import com.celik.mancalaapi.application.dto.MancalaGameStateDTO;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MancalaGameMapper {
    public static MancalaGameStateDTO mapGameStateToDTO(MancalaGameState gameState) {
        if (Objects.isNull(gameState)) return null;
        return MancalaGameStateDTO.builder()
                .gameId(gameState.getGameId())
                .player1Pits(gameState.getPlayer1Pits())
                .player2Pits(gameState.getPlayer2Pits())
                .currentPlayer(gameState.getCurrentPlayer())
                .build();
    }


}
