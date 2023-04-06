package com.celik.mancalaapi.domain.ports.in;

import com.celik.mancalaapi.domain.model.MancalaGameState;

import java.util.UUID;

public interface MancalaGameServicePort {
    MancalaGameState createGame();
    MancalaGameState getGameState(UUID gameId);
    void makeMove(UUID gameId, int pitIndex);
}
