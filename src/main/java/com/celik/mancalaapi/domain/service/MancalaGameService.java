package com.celik.mancalaapi.domain.service;

import com.celik.mancalaapi.domain.exception.GameNotFoundException;
import com.celik.mancalaapi.domain.model.MancalaBoard;
import com.celik.mancalaapi.domain.model.MancalaGame;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import com.celik.mancalaapi.domain.ports.in.MancalaGameServicePort;
import com.celik.mancalaapi.domain.ports.out.MancalaGameRepositoryPort;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MancalaGameService implements MancalaGameServicePort {

    private final ConcurrentHashMap<UUID, MancalaGame> gameCache = new ConcurrentHashMap<>();

    private final MancalaGameRepositoryPort mancalaGameRepositoryPort;

    public MancalaGameService(MancalaGameRepositoryPort mancalaGameRepositoryPort) {
        this.mancalaGameRepositoryPort = mancalaGameRepositoryPort;
    }

    @Override
    public MancalaGameState createGame() {
        MancalaGame game = new MancalaGame();
        gameCache.put(game.getId(), game);
        MancalaGameState gameState = game.toGameState();
        mancalaGameRepositoryPort.saveGameState(gameState);
        return gameState;
    }

    @Override
    public MancalaGameState getGameState(UUID gameId) {
        return mancalaGameRepositoryPort.findGameStateById(gameId);
    }

    @Override
    public void makeMove(UUID gameId, int pitIdx) {
        MancalaGame mancalaGame = gameCache.get(gameId);
        if (Objects.isNull(mancalaGame))
            throw new GameNotFoundException("Id: " + gameId.toString() + " of game not found");
        int serverPitIndex = convertClientPitIndexToServerPitIndex(mancalaGame.getCurrentPlayer(), pitIdx);
        mancalaGame.makeMove(serverPitIndex);
        MancalaGameState updatedGameState = mancalaGame.toGameState();
        mancalaGameRepositoryPort.saveGameState(updatedGameState);
        if (mancalaGame.isGameFinished())
            gameCache.remove(gameId);
    }

    private int convertClientPitIndexToServerPitIndex(MancalaPlayerType playerType, int clientIndex) {
        if (MancalaPlayerType.FIRST_PLAYER.equals(playerType)) {
            return clientIndex;
        } else {
            return MancalaBoard.REGULAR_PITS_PER_PLAYER + clientIndex + 1;
        }
    }

}
