package com.celik.mancalaapi.domain.service;

import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.domain.ports.in.MancalaGameServicePort;
import com.celik.mancalaapi.domain.ports.out.MancalaGameRepositoryPort;

import java.util.UUID;

public class MancalaGameService implements MancalaGameServicePort {

    MancalaGameRepositoryPort mancalaGameRepositoryPort;

    public MancalaGameService(MancalaGameRepositoryPort mancalaGameRepositoryPort) {
        this.mancalaGameRepositoryPort = mancalaGameRepositoryPort;
    }

    @Override
    public MancalaGameState createGame() {
        return new MancalaGameState();
    }

    @Override
    public MancalaGameState getGameState(UUID gameId) {
        return new MancalaGameState();
    }

    @Override
    public void makeMove(UUID gameId, int pitId) {

    }

}
