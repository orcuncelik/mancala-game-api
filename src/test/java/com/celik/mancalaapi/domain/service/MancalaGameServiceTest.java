package com.celik.mancalaapi.domain.service;

import com.celik.mancalaapi.domain.exception.GameNotFoundException;
import com.celik.mancalaapi.domain.exception.InvalidPitException;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.domain.ports.out.MancalaGameRepositoryPort;
import com.celik.mancalaapi.infrastructure.exception.GameStateSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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
    void givenService_whenCreateGame_thenGameCreatedCorrectly() throws GameStateSaveException {
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
    void givenService_whenMakeMove_thenUpdateGameAndSave() throws GameStateSaveException {
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

    @Test
    void givenGameService_whenPlayerTriesToSetStonesToIn_thenThrowsException() {
        int index1 = -5;
        int index2 = 7;
        MancalaGameState gameState = gameService.createGame();
        Throwable exception1 = assertThrows(InvalidPitException.class, () -> gameService.makeMove(gameState.getGameId(), index1));
        assertEquals("Pit index cannot be negative.", exception1.getMessage());
        Throwable exception2 = assertThrows(InvalidPitException.class, () -> gameService.makeMove(gameState.getGameId(), index2));
        assertEquals("Pit index cannot be bigger than 5.", exception2.getMessage());
    }

}
