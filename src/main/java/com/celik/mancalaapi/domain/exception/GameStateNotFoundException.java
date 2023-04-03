package com.celik.mancalaapi.domain.exception;

public class GameStateNotFoundException extends RuntimeException {

    public GameStateNotFoundException(String message) {
        super(message);
    }

}