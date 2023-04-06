package com.celik.mancalaapi.application.rest.controller;

import com.celik.mancalaapi.application.dto.MakeMoveRequestDTO;
import com.celik.mancalaapi.application.dto.MancalaGameStateDTO;
import com.celik.mancalaapi.domain.model.MancalaGameState;
import com.celik.mancalaapi.domain.ports.in.MancalaGameServicePort;
import com.celik.mancalaapi.infrastructure.mapper.MancalaGameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/mancala")
public class MancalaGameController {

    private final MancalaGameServicePort gameService;

    @Autowired
    public MancalaGameController(MancalaGameServicePort gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<MancalaGameStateDTO> createGame() {
        MancalaGameState gameState = gameService.createGame();
        MancalaGameStateDTO gameStateDTO = MancalaGameMapper.mapGameStateToDTO(gameState);
        return new ResponseEntity<>(gameStateDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<MancalaGameStateDTO> getGameState(@PathVariable @NotNull UUID gameId) {
        MancalaGameState gameState = gameService.getGameState(gameId);
        return new ResponseEntity<>(MancalaGameMapper.mapGameStateToDTO(gameState), HttpStatus.OK);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<MancalaGameStateDTO> makeMove(@PathVariable @NotNull UUID gameId,
                                                        @Valid @RequestBody MakeMoveRequestDTO requestDTO) {
        gameService.makeMove(gameId, requestDTO.getPitIndex());
        MancalaGameState gameState = gameService.getGameState(gameId);
        return new ResponseEntity<>(MancalaGameMapper.mapGameStateToDTO(gameState), HttpStatus.OK);
    }

}
