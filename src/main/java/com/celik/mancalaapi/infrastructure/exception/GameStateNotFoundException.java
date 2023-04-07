package com.celik.mancalaapi.infrastructure.exception;

public class GameStateNotFoundException extends RuntimeException {

    public GameStateNotFoundException(String message) {
        super(message);
    }

}