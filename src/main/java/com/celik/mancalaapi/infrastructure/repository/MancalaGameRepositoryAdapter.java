package com.celik.mancalaapi.infrastructure.repository;

import com.celik.mancalaapi.domain.exception.GameStateNotFoundException;
import com.celik.mancalaapi.domain.exception.GameStateSaveException;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.domain.ports.out.MancalaGameRepositoryPort;
import com.celik.mancalaapi.infrastructure.repository.entity.MancalaGameStateEntity;
import com.celik.mancalaapi.infrastructure.repository.mapper.MancalaGameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.Optional;

@Service
public class MancalaGameRepositoryAdapter implements MancalaGameRepositoryPort {

    private final MancalaGameRepository gameRepository;

    @Autowired
    public MancalaGameRepositoryAdapter(MancalaGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public MancalaGameState saveGameState(MancalaGameState gameState) {
        MancalaGameStateEntity gameStateEntity = MancalaGameMapper.mapGameStateToEntity(gameState);
        if (Objects.isNull(gameStateEntity))
            throw new GameStateSaveException("Failed to save game state");
        MancalaGameStateEntity gameStateEntitySaved = gameRepository.save(gameStateEntity);
        return MancalaGameMapper.mapEntityToGameState(gameStateEntitySaved);
    }

    @Override
    public MancalaGameState findGameStateById(UUID gameId) {
        Optional<MancalaGameStateEntity> gameStateEntity = gameRepository.findById(gameId);
        return gameStateEntity.map(MancalaGameMapper::mapEntityToGameState)
                .orElseThrow(() -> new GameStateNotFoundException("Game state not found for ID: " + gameId));
    }

    @Override
    public MancalaGameState updateGameState(UUID gameId, MancalaGameState gameState) {
        if (Objects.isNull(findGameStateById(gameId)))
            return null;
        return saveGameState(gameState);
    }

    @Override
    public void deleteGameStateById(UUID gameId) {
        gameRepository.deleteById(gameId);
    }

}
