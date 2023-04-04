package com.celik.mancalaapi.domain.model;

import com.celik.mancalaapi.domain.exception.InvalidPitException;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MancalaBoardTest {
    private MancalaBoard board;

    @BeforeEach
    void setUp() {
        board = new MancalaBoard();
    }

    @Test
    void givenInitialBoard_whenCreated_thenBoardStateSetCorrectly() {
        for (int i = 0; i < MancalaBoard.TOTAL_PITS; i++) {
            if (i == MancalaBoard.REGULAR_PITS_PER_PLAYER || i == MancalaBoard.TOTAL_PITS - 1) {
                assertEquals(0, board.getStones(i), "Big pits should have 0 stones initially");
            } else {
                assertEquals(MancalaBoard.INITIAL_STONES_PER_PIT, board.getStones(i), "Regular pits should have 6 stones initially");
            }
        }
    }

    @Test
    void givenInitialBoard_whenGetRegularPitsByPlayerType_thenReturnPitsCorrectly() {
        List<Integer> firstPlayerPits = board.getRegularPitsByPlayerType(MancalaPlayerType.FIRST_PLAYER);
        List<Integer> secondPlayerPits = board.getRegularPitsByPlayerType(MancalaPlayerType.SECOND_PLAYER);

        assertNotNull(firstPlayerPits);
        assertNotNull(secondPlayerPits);

        assertEquals(MancalaBoard.REGULAR_PITS_PER_PLAYER, firstPlayerPits.size());
        assertEquals(MancalaBoard.REGULAR_PITS_PER_PLAYER, secondPlayerPits.size());

        for (int i = 0; i < MancalaBoard.REGULAR_PITS_PER_PLAYER; i++) {
            assertEquals(MancalaBoard.INITIAL_STONES_PER_PIT, firstPlayerPits.get(i));
            assertEquals(MancalaBoard.INITIAL_STONES_PER_PIT, secondPlayerPits.get(i));
        }
    }

    @Test
    void givenInitialBoard_whenGetBigPitByPlayerType_thenReturnBigPitCorrectly() {
        assertEquals(0, board.getBigPitByPlayerType(MancalaPlayerType.FIRST_PLAYER));
        assertEquals(0, board.getBigPitByPlayerType(MancalaPlayerType.SECOND_PLAYER));
    }

    @Test
    void givenInitialBoard_whenSetStones_thenPitStonesUpdated() {
        int index = 4;
        int newStoneCount = 8;
        board.setStones(index, newStoneCount);
        assertEquals(newStoneCount, board.getStones(index));
    }

    @Test
    void givenBoard_whenFirstPlayerTriesToSetStonesInSecondPlayersBigPit_thenThrowsException() {
        int firstPlayerBigPitIndex = MancalaBoard.REGULAR_PITS_PER_PLAYER;
        int secondPlayerBigPitIndex = MancalaBoard.TOTAL_PITS - 1;
        int newStones = 4;
        // When
        Throwable exception = assertThrows(InvalidPitException.class, () -> board.setStones(secondPlayerBigPitIndex, newStones));
        // Then
        assertEquals("Player cannot set stones in opponent's big pit", exception.getMessage());
    }
}
