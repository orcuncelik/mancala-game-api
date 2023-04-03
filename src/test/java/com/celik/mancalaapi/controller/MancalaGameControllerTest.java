package com.celik.mancalaapi.controller;

import com.celik.mancalaapi.application.dto.MancalaGameStateDTO;
import com.celik.mancalaapi.application.rest.controller.MancalaGameController;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.domain.service.MancalaGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(MancalaGameController.class)
class MancalaGameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MancalaGameService gameService;
    private MancalaGameState gameState;
    private MancalaGameStateDTO gameStateDTO;

    @BeforeEach
    void setUp() {
        gameState = new MancalaGameState("1", Arrays.asList(6, 6, 6, 6, 6, 6), 0, Arrays.asList(6, 6, 6, 6, 6, 6), 0, "player1");
        gameStateDTO = new MancalaGameStateDTO("1", Arrays.asList(6, 6, 6, 6, 6, 6), 0, Arrays.asList(6, 6, 6, 6, 6, 6), 0, "player1");
    }

    @Test
    void testCreateGame() throws Exception {
        when(gameService.createGame()).thenReturn(gameState);

        mockMvc.perform(post("/api/mancala")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameId").value("1"));
    }

    @Test
    void testPlayGame() {
        int pitId = 1;
        String gameId = "1";

        doReturn(gameState)
                .when(gameService)
                .makeMove(null,1);

    }
}
