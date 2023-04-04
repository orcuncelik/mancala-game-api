package com.celik.mancalaapi.domain.model;

import com.celik.mancalaapi.domain.exception.InvalidPitException;
import com.celik.mancalaapi.domain.model.enums.MancalaPitType;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MancalaPitTest {
    @Test
    void givenValidPitIndex_whenCreating_thenNoExceptionThrown() {
        int validPitIndex = 3;
        assertDoesNotThrow(() -> new MancalaPit(validPitIndex));
    }

    @Test
    void givenInvalidPitIndexes_whenCreating_thenInvalidPitExceptionThrown() {
        int negativeIndex = -1;
        int outOfBoundIndex = MancalaBoard.TOTAL_PITS;
        assertThrows(InvalidPitException.class, () -> new MancalaPit(negativeIndex));
        assertThrows(InvalidPitException.class, () -> new MancalaPit(outOfBoundIndex));
    }

    @Test
    void givenFirstPlayerRegularPitIndex_whenCreating_thenInitialStonesSetCorrectly() {
        int player1FirstRegularPitIndex = 0;
        MancalaPit player1RegularPit = new MancalaPit(player1FirstRegularPitIndex);
        assertEquals(6, player1RegularPit.getStones());
    }

    @Test
    void givenSecondPlayerRegularPitIndex_whenCreating_thenInitialStonesSetCorrectly() {
        int player2FirstRegularPitIndex = 7;
        MancalaPit player2RegularPit = new MancalaPit(player2FirstRegularPitIndex);
        assertEquals(6, player2RegularPit.getStones());
    }

    @Test
    void givenFirstPlayerBigPitIndex_whenCreating_thenInitialStonesSetCorrectly() {
        int player1BigPitIndex = 6;
        MancalaPit bigPit = new MancalaPit(player1BigPitIndex);
        assertEquals(0, bigPit.getStones());
    }

    @Test
    void givenSecondPlayerBigPitIndex_whenCreating_thenInitialStonesSetCorrectly() {
        int player2BigPitIndex = 13;
        MancalaPit bigPit = new MancalaPit(player2BigPitIndex);
        assertEquals(0, bigPit.getStones());
    }


    @Test
    void givenRegularPit_whenCreating_thenPitTypeSetCorrectly() {
        MancalaPit regularPit = new MancalaPit(0);
        assertEquals(MancalaPitType.REGULAR_PIT, regularPit.getPitType());
    }

    @Test
    void givenBigPit_whenCreating_thenPitTypeSetCorrectly() {
        MancalaPit bigPit = new MancalaPit(MancalaBoard.REGULAR_PITS_PER_PLAYER);
        assertEquals(MancalaPitType.BIG_PIT, bigPit.getPitType());
    }

    @Test
    void givenPit_whenCreating_thenFirstPlayerTypeSetCorrectly() {
        MancalaPit firstPlayerPit = new MancalaPit(0);
        assertEquals(MancalaPlayerType.FIRST_PLAYER, firstPlayerPit.getPlayerType());
    }

    @Test
    void givenPit_whenCreating_theSecondPlayerTypeSetCorrectly() {
        MancalaPit secondPlayerPit = new MancalaPit(MancalaBoard.REGULAR_PITS_PER_PLAYER + 1);
        assertEquals(MancalaPlayerType.SECOND_PLAYER, secondPlayerPit.getPlayerType());
    }
}
