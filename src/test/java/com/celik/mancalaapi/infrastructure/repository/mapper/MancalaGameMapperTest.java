package com.celik.mancalaapi.infrastructure.repository.mapper;

import com.celik.mancalaapi.application.dto.MancalaGameStateDTO;
import com.celik.mancalaapi.domain.model.MancalaGame;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.infrastructure.mapper.MancalaGameMapper;
import com.celik.mancalaapi.infrastructure.repository.entity.MancalaGameStateEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MancalaGameMapperTest {

    private MancalaGameState gameState;

    @BeforeEach
    void setUp() {
        MancalaGame game = new MancalaGame();
        gameState = game.toGameState();
    }

    @Test
    void testMapGameStateToGameStateDTO() {
        MancalaGameStateDTO gameStateDTO = MancalaGameMapper.mapGameStateToDTO(gameState);
        assert gameStateDTO != null;
        assertEquals(gameState.getGameId(), gameStateDTO.getGameId());
        assertEquals(gameState.getPlayer1Pits(), gameStateDTO.getPlayer1Pits());
        assertEquals(gameState.getPlayer2Pits(), gameStateDTO.getPlayer2Pits());
        assertEquals(gameState.getPlayer1BigPit(), gameStateDTO.getPlayer1BigPit());
        assertEquals(gameState.getPlayer2BigPit(), gameStateDTO.getPlayer2BigPit());
        assertEquals(gameState.getCurrentPlayer(), gameStateDTO.getCurrentPlayer());
        assertEquals(gameState.getGameStatus(), gameStateDTO.getGameStatus());
        assertNull(MancalaGameMapper.mapGameStateToDTO(null));
    }

    @Test
    void testMapGameStateToGameEntity() {
        MancalaGameStateEntity gameStateEntity = MancalaGameMapper.mapGameStateToEntity(gameState);
        assert gameStateEntity != null;
        assertEquals(gameState.getGameId(), gameStateEntity.getGameId());
        assertEquals(gameState.getPlayer1Pits(), gameStateEntity.getPlayer1Pits());
        assertEquals(gameState.getPlayer2Pits(), gameStateEntity.getPlayer2Pits());
        assertEquals(gameState.getPlayer1BigPit(), gameStateEntity.getPlayer1BigPit());
        assertEquals(gameState.getPlayer2BigPit(), gameStateEntity.getPlayer2BigPit());
        assertEquals(gameState.getCurrentPlayer(), gameStateEntity.getCurrentPlayer());
        assertEquals(gameState.getGameStatus(), gameStateEntity.getGameStatus());
        assertNull(MancalaGameMapper.mapGameStateToEntity(null));
    }

}
