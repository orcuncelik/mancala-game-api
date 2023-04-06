package com.celik.mancalaapi.domain.model;

import com.celik.mancalaapi.domain.exception.InvalidPitException;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void givenInitialBoard_whenGetBigPitIndexByPlayerType_thenReturnBigPitIndexCorrectly() {
        int player1BigPitIndex = 6;
        int player2BigPitIndex = 13;
        assertEquals(player1BigPitIndex, board.getBigPitIndexByPlayerType(MancalaPlayerType.FIRST_PLAYER));
        assertEquals(player2BigPitIndex, board.getBigPitIndexByPlayerType(MancalaPlayerType.SECOND_PLAYER));
    }

    @Test
    void givenInitialBoard_whenGetBigPitStonesByPlayerType_thenReturnBigPitStonesCorrectly() {
        int firstPlayerBigPitStones = board.getBigPitStonesByPlayerType(MancalaPlayerType.FIRST_PLAYER);
        int secondPlayerBigPitStones = board.getBigPitStonesByPlayerType(MancalaPlayerType.SECOND_PLAYER);
        assertEquals(0, firstPlayerBigPitStones);
        assertEquals(0, secondPlayerBigPitStones);
    }

    @Test
    void givenInitialBoard_whenAddStones_thenPitStonesAddedCorrectly() {
        int index = 4;
        int stonesToAdd = 8;
        int expectedStoneCount = board.getStones(index) + stonesToAdd;
        board.addStones(index, stonesToAdd);
        assertEquals(expectedStoneCount, board.getStones(index));
    }

    @Test
    void givenInitialBoard_whenIncrementPitStones_thenPitStonesIncrementedCorrectly() {
        int index = 2;
        int initialStones = board.getStones(index);
        board.incrementPitStones(index);
        assertEquals(initialStones + 1, board.getStones(index));
    }


    @Test
    void givenInitialBoard_whenGetOpponentSidePitIndex_thenGetOpponentIndexCorrectly() {
        int playerIndex = 0;
        int expectedOpponentSideIndex = 12;
        assertEquals(expectedOpponentSideIndex, board.getOpponentSidePitIndex(playerIndex));
    }

    @Test
    void givenInitialBoard_whenGetOpponentSideStones_thenGetOpponentStonesCorrectly() {
        int playerIndex = 3;
        int expectedOpponentStones = 6;
        assertEquals(expectedOpponentStones, board.getOpponentSideStones(playerIndex));
    }


}