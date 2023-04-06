package com.celik.mancalaapi.infrastructure.mapper;

import com.celik.mancalaapi.application.dto.MancalaGameStateDTO;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.infrastructure.repository.entity.MancalaGameStateEntity;
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
                .player1BigPit(gameState.getPlayer1BigPit())
                .player2BigPit(gameState.getPlayer2BigPit())
                .currentPlayer(gameState.getCurrentPlayer())
                .gameStatus(gameState.getGameStatus())
                .build();
    }

    public static MancalaGameStateEntity mapGameStateToEntity(MancalaGameState gameState) {
        if (Objects.isNull(gameState)) return null;
        return MancalaGameStateEntity.builder()
                .gameId((gameState.getGameId()))
                .player1Pits(gameState.getPlayer1Pits())
                .player2Pits(gameState.getPlayer2Pits())
                .player1BigPit(gameState.getPlayer1BigPit())
                .player2BigPit(gameState.getPlayer2BigPit())
                .currentPlayer(gameState.getCurrentPlayer())
                .gameStatus(gameState.getGameStatus())
                .build();
    }

    public static MancalaGameState mapEntityToGameState(MancalaGameStateEntity gameStateEntity) {
        if (Objects.isNull(gameStateEntity)) return null;
        return MancalaGameState.builder()
                .gameId(gameStateEntity.getGameId())
                .player1Pits(gameStateEntity.getPlayer1Pits())
                .player2Pits(gameStateEntity.getPlayer2Pits())
                .player1BigPit(gameStateEntity.getPlayer1BigPit())
                .player2BigPit(gameStateEntity.getPlayer2BigPit())
                .currentPlayer(gameStateEntity.getCurrentPlayer())
                .gameStatus(gameStateEntity.getGameStatus())
                .build();
    }


}
