package com.celik.mancalaapi.domain.model;

import com.celik.mancalaapi.domain.exception.InvalidPitException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MancalaPitTest {
    @Test
    void givenValidPitIndex_whenValidatingPit_thenNoExceptionThrown() {
        MancalaPit pit = new MancalaPit(0);
        pit.validatePit(5);
    }

    @Test
    void givenInvalidPitIndex_whenValidatingPit_thenInvalidPitExceptionThrown() {
        MancalaPit pit = new MancalaPit(0);
        int negativeIndex = -1;
        int outOfBoundIndex = MancalaBoard.TOTAL_PITS;
        assertThrows(InvalidPitException.class, () -> pit.validatePit(negativeIndex));
        assertThrows(InvalidPitException.class, () -> pit.validatePit(outOfBoundIndex));
    }

    @Test
    void givenFirstPlayerRegularPit_whenCreating_thenInitialStonesSetCorrectly() {
        int player1FirstRegularPitIndex = 0;
        MancalaPit player1RegularPit = new MancalaPit(player1FirstRegularPitIndex);
        assertEquals(6, player1RegularPit.getStones());
    }

    @Test
    void givenSecondPlayerRegularPit_whenCreating_thenInitialStonesSetCorrectly() {
        int player2FirstRegularPitIndex = 7;
        MancalaPit player2RegularPit = new MancalaPit(player2FirstRegularPitIndex);
        assertEquals(6, player2RegularPit.getStones());
    }

    @Test
    void givenFirstPlayerBigPit_whenCreating_thenInitialStonesSetCorrectly() {
        int player1BigPitIndex = 6;
        MancalaPit bigPit1 = new MancalaPit(player1BigPitIndex);
        assertEquals(0, bigPit1.getStones());
    }

    @Test
    void givenSecondPlayerBigPit_whenCreating_thenInitialStonesSetCorrectly() {
        int player2BigPitIndex = 13;
        MancalaPit bigPit2 = new MancalaPit(player2BigPitIndex);
        assertEquals(0, bigPit2.getStones());
    }


    @Test
    void givenRegularPit_whenCreating_thenPitTypeSetCorrectly() {
        MancalaPit regularPit = new MancalaPit(0);
        assertEquals(MancalaPitType.REGULAR_PIT, regularPit.getPitType());
    }

    @Test
    void givenBigPit_whenCreating_thenPitTypeSetCorrectly() {
        MancalaPit bigPit = new MancalaPit(MancalaBoard.PITS_PER_PLAYER);
        assertEquals(MancalaPitType.BIG_PIT, bigPit.getPitType());
    }

    @Test
    void givenPit_whenCreating_thenFirstPlayerTypeSetCorrectly() {
        MancalaPit firstPlayerPit = new MancalaPit(0);
        assertEquals(MancalaPlayerType.FIRST_PLAYER, firstPlayerPit.getPlayerType());
    }

    @Test
    void givenPit_whenCreating_theSecondPlayerTypeSetCorrectly() {
        MancalaPit secondPlayerPit = new MancalaPit(MancalaBoard.PITS_PER_PLAYER + 1);
        assertEquals(MancalaPlayerType.SECOND_PLAYER, secondPlayerPit.getPlayerType());
    }
}
