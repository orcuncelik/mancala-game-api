package com.celik.mancalaapi.controller;

import com.celik.mancalaapi.application.dto.MakeMoveRequestDTO;
import com.celik.mancalaapi.application.dto.MancalaGameStateDTO;
import com.celik.mancalaapi.application.rest.controller.MancalaGameController;
import com.celik.mancalaapi.domain.model.MancalaGame;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.domain.service.MancalaGameService;
import com.celik.mancalaapi.infrastructure.mapper.MancalaGameMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MancalaGameController.class)
class MancalaGameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MancalaGameService gameService;

    private MancalaGameState gameState;

    private MancalaGameStateDTO gameStateDTO;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MancalaGame game = new MancalaGame();
        gameState = game.toGameState();
        gameStateDTO = MancalaGameMapper.mapGameStateToDTO(gameState);
    }

    @Test
    void givenService_whenCreateGame_thenGameCreatedSuccessfully() throws Exception {
        when(gameService.createGame()).thenReturn(gameState);
        MvcResult mvcResult = mockMvc.perform(post("/api/mancala")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        gameStateDTO = objectMapper.readValue(responseBody, MancalaGameStateDTO.class);
        assertEquals(gameState.getGameId(), gameStateDTO.getGameId());
        assertEquals(gameState.getCurrentPlayer(), gameStateDTO.getCurrentPlayer());
        verify(gameService, times(1)).createGame();
    }

    @Test
    void givenService_whenGetGameState_thenGameStateGot() throws Exception {
        UUID gameId = gameState.getGameId();
        when(gameService.getGameState(gameId)).thenReturn(gameState);
        MvcResult mvcResult = mockMvc.perform(get("/api/mancala/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        gameStateDTO = objectMapper.readValue(responseBody, MancalaGameStateDTO.class);
        assertEquals(gameState.getGameId(), gameStateDTO.getGameId());
        assertEquals(gameState.getCurrentPlayer(), gameStateDTO.getCurrentPlayer());
        verify(gameService, times(1)).getGameState(gameId);
    }

    @Test
    void givenService_whenMakeMove_thenMakeMoveSuccessful() throws Exception {
        UUID gameId = gameState.getGameId();
        MakeMoveRequestDTO makeMoveRequestDTO = new MakeMoveRequestDTO(1);
        when(gameService.getGameState(gameId)).thenReturn(gameState);
        doNothing().when(gameService).makeMove(gameId, makeMoveRequestDTO.getPitIndex());
        MvcResult mvcResult = mockMvc.perform(put("/api/mancala/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(makeMoveRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        gameStateDTO = objectMapper.readValue(responseBody, MancalaGameStateDTO.class);
        assertEquals(gameState.getGameId(), gameStateDTO.getGameId());
        assertEquals(gameState.getCurrentPlayer(), gameStateDTO.getCurrentPlayer());
        verify(gameService, times(1)).makeMove(gameId, makeMoveRequestDTO.getPitIndex());
    }
}
