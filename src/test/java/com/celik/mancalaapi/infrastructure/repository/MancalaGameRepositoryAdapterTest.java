package com.celik.mancalaapi.infrastructure.repository;

import com.celik.mancalaapi.domain.model.MancalaGame;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.infrastructure.exception.GameStateNotFoundException;
import com.celik.mancalaapi.infrastructure.mapper.MancalaGameMapper;
import com.celik.mancalaapi.infrastructure.repository.entity.MancalaGameStateEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class MancalaGameRepositoryAdapterTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MancalaGameRepository gameRepository;

    private MancalaGameRepositoryAdapter mancalaGameRepositoryAdapter;

    private MancalaGameStateEntity gameStateEntity;

    @BeforeEach
    void setUp() {
        MancalaGame game = new MancalaGame();
        gameStateEntity = MancalaGameMapper.mapGameStateToEntity(game.toGameState());
        mancalaGameRepositoryAdapter = new MancalaGameRepositoryAdapter(gameRepository);
    }

    @Test
    void givenGameState_whenSaved_thenGetFromDatabaseCorrectly() {
        MancalaGameStateEntity savedGameStateEntity = testEntityManager.persistFlushFind(gameStateEntity);
        MancalaGameState savedGameState = MancalaGameMapper.mapEntityToGameState(savedGameStateEntity);
        assert savedGameState != null;
        MancalaGameState foundGameState = mancalaGameRepositoryAdapter.findGameStateById(savedGameState.getGameId());
        assertThat(foundGameState).isEqualTo(savedGameState);
    }

    @Test
    void givenGameState_whenFindNonExistedId_thenThrowException() {
        UUID nonExistedGameId = UUID.randomUUID();
        assertThrows(GameStateNotFoundException.class, () ->
            mancalaGameRepositoryAdapter.findGameStateById(nonExistedGameId)
        );
    }

    @Test
    void givenGameState_whenDeleteGameById_thenDeleteCorrectly() {
        MancalaGameStateEntity savedGameStateEntity = testEntityManager.persistAndFlush(gameStateEntity);
        MancalaGameState savedGameState = MancalaGameMapper.mapEntityToGameState(savedGameStateEntity);
        assert savedGameState != null;
        mancalaGameRepositoryAdapter.deleteGameStateById(savedGameState.getGameId());
        MancalaGameStateEntity deletedGameStateEntity = testEntityManager.find(MancalaGameStateEntity.class, savedGameState.getGameId());
        assertThat(deletedGameStateEntity).isNull();
    }


}