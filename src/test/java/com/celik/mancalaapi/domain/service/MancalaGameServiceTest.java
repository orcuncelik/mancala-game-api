package com.celik.mancalaapi.domain.service;

import com.celik.mancalaapi.domain.exception.GameNotFoundException;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.domain.model.enums.MancalaGameStatus;
import com.celik.mancalaapi.domain.ports.out.MancalaGameRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MancalaGameServiceTest {

    private MancalaGameRepositoryPort repositoryPort;
    private MancalaGameService gameService;

    @BeforeEach
    void setUp() {
        repositoryPort = mock(MancalaGameRepositoryPort.class);
        gameService = new MancalaGameService(repositoryPort);
    }

    @Test
    void givenService_whenCreateGame_thenGameCreatedCorrectly() {
        MancalaGameState createdGameState = gameService.createGame();
        assertNotNull(createdGameState);
        assertNotNull(createdGameState.getGameId());
        verify(repositoryPort, times(1)).saveGameState(any(MancalaGameState.class));
    }

    @Test
    void givenService_whenGetGameState_thenRetrieveGameStateFromRepository() {
        UUID gameId = UUID.randomUUID();
        MancalaGameState expectedGameState = MancalaGameState.builder().gameId(gameId).build();
        when(repositoryPort.findGameStateById(gameId)).thenReturn(expectedGameState);
        MancalaGameState actualGameState = gameService.getGameState(gameId);
        assertEquals(expectedGameState, actualGameState);
        verify(repositoryPort, times(1)).findGameStateById(gameId);
    }

    @Test
    void givenService_whenMakeMove_thenUpdateGameAndSave() {
        MancalaGameState gameState = gameService.createGame();
        UUID gameId = gameState.getGameId();
        int pitIdx = 0;
        when(repositoryPort.findGameStateById(gameId)).thenReturn(gameState);
        gameService.makeMove(gameId, pitIdx);
        verify(repositoryPort, times(2)).saveGameState(any(MancalaGameState.class));
    }

    @Test
    void givenService_whenGetNotExistedGameState_thenThrowGameNotFoundException() {
        UUID gameId = UUID.randomUUID();
        int pitIdx = 0;
        when(repositoryPort.findGameStateById(gameId)).thenReturn(null);
        assertThrows(GameNotFoundException.class, () -> gameService.makeMove(gameId, pitIdx));
    }

}
