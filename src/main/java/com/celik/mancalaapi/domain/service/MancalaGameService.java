package com.celik.mancalaapi.domain.service;

import com.celik.mancalaapi.domain.exception.GameNotFoundException;
import com.celik.mancalaapi.domain.exception.InvalidPitException;
import com.celik.mancalaapi.domain.model.MancalaBoard;
import com.celik.mancalaapi.domain.model.MancalaGame;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import com.celik.mancalaapi.domain.ports.in.MancalaGameServicePort;
import com.celik.mancalaapi.domain.ports.out.MancalaGameRepositoryPort;
import com.celik.mancalaapi.infrastructure.exception.GameStateSaveException;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MancalaGameService implements MancalaGameServicePort {

    private static final Logger logger = Logger.getLogger(MancalaGameService.class.getName());

    private final ConcurrentHashMap<UUID, MancalaGame> gameCache = new ConcurrentHashMap<>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final MancalaGameRepositoryPort mancalaGameRepositoryPort;

    public MancalaGameService(MancalaGameRepositoryPort mancalaGameRepositoryPort) {
        this.mancalaGameRepositoryPort = mancalaGameRepositoryPort;
    }

    @Override
    public MancalaGameState createGame() {
        readWriteLock.writeLock().lock();
        try {
            MancalaGame game = new MancalaGame();
            MancalaGameState gameState = game.toGameState();
            if (saveGameState(gameState))
                gameCache.put(game.getId(), game);
            return gameState;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void makeMove(UUID gameId, int pitIdx) {
        readWriteLock.writeLock().lock();
        try {
            MancalaGame game = getGameFromCache(gameId);
            int serverPitIndex = convertClientPitIndexToServerPitIndex(game.getCurrentPlayer(), pitIdx);
            game.makeMove(serverPitIndex);
            MancalaGameState gameState = game.toGameState();
            saveGameState(gameState);
            if (game.isGameFinished()) {
                gameCache.remove(gameId);
                mancalaGameRepositoryPort.deleteGameStateById(gameId);
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private boolean saveGameState(MancalaGameState gameState) {
        try {
            mancalaGameRepositoryPort.saveGameState(gameState);
            return true;
        } catch (GameStateSaveException e) {
            logger.log(Level.SEVERE, "Problem occurred when saving the game: {0}", e.getMessage());
            return false;
        }
    }

    @Override
    public MancalaGameState getGameState(UUID gameId) {
        return mancalaGameRepositoryPort.findGameStateById(gameId);
    }

    private MancalaGame getGameFromCache(UUID gameId) {
        MancalaGame game = gameCache.get(gameId);
        if (Objects.isNull(game)) {
            throw new GameNotFoundException("Id: " + gameId.toString() + " of game not found in the cache");
        }
        return game;
    }

    private int convertClientPitIndexToServerPitIndex(MancalaPlayerType playerType, int clientPitIndex) {
        validateClientPitIndex(clientPitIndex);
        if (MancalaPlayerType.FIRST_PLAYER.equals(playerType)) {
            return clientPitIndex;
        } else {
            return MancalaBoard.REGULAR_PITS_PER_PLAYER + clientPitIndex + 1;
        }
    }

    private void validateClientPitIndex(int clientPitIndex) {
        if (clientPitIndex < 0)
            throw new InvalidPitException("Pit index cannot be negative.");
        else if (clientPitIndex > 5)
            throw new InvalidPitException("Pit index cannot be bigger than 5.");
    }

}
