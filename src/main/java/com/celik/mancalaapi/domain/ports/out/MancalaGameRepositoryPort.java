package com.celik.mancalaapi.domain.ports.out;

import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.infrastructure.exception.GameStateSaveException;

import java.util.UUID;

public interface MancalaGameRepositoryPort {
    MancalaGameState saveGameState(MancalaGameState game) throws GameStateSaveException;

    MancalaGameState findGameStateById(UUID gameId);

    void deleteGameStateById(UUID gameId);
}
