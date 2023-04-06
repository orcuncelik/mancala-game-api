package com.celik.mancalaapi.domain.ports.out;

import com.celik.mancalaapi.domain.model.MancalaGameState;

import java.util.UUID;

public interface MancalaGameRepositoryPort {
    MancalaGameState saveGameState(MancalaGameState game);

    MancalaGameState findGameStateById(UUID gameId);

    MancalaGameState updateGameState(UUID gameId, MancalaGameState gameState);

    void deleteGameStateById(UUID gameId);
}
