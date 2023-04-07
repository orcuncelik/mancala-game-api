package com.celik.mancalaapi.domain.model;

import com.celik.mancalaapi.domain.exception.InvalidPitException;
import com.celik.mancalaapi.domain.model.enums.MancalaGameStatus;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MancalaGameTest {

    private MancalaGame game;

    @BeforeEach
    void setUp() {
        game = new MancalaGame();
    }

    @Test
    void givenGame_whenCreated_thenNewGameInitializedCorrectly() {
        assertNotNull(game.getId());
        assertEquals(MancalaGameStatus.CONTINUING, game.getGameStatus());
        assertEquals(MancalaPlayerType.FIRST_PLAYER, game.getCurrentPlayer());
    }

    @Test
    void givenInitialGame_whenMakeMoveWithSwitchPlayerRound_thenGameUpdatedCorrectly() {
        int indexToSwitchPlayerRound = 2;
        game.makeMove(indexToSwitchPlayerRound);
        assertEquals(0, game.getBoard().getStones(2));
        assertEquals(7, game.getBoard().getStones(3));
        assertEquals(7, game.getBoard().getStones(4));
        assertEquals(7, game.getBoard().getStones(5));
        assertEquals(MancalaPlayerType.SECOND_PLAYER, game.getCurrentPlayer());
    }

    @Test
    void givenGame_whenMakeMoveWithLastStoneLandInCurrentPlayerBigPit_thenGameUpdatedCorrectly() {
        int indexToMakeCurrentPlayerPlayAgain = 0;
        game.makeMove(indexToMakeCurrentPlayerPlayAgain);
        assertEquals(0, game.getBoard().getStones(0));
        assertEquals(7, game.getBoard().getStones(1));
        assertEquals(7, game.getBoard().getStones(2));
        assertEquals(7, game.getBoard().getStones(3));
        assertEquals(7, game.getBoard().getStones(4));
        assertEquals(7, game.getBoard().getStones(5));
        assertEquals(1, game.getBoard().getStones(6));
        assertEquals(6, game.getBoard().getStones(7));
        assertEquals(6, game.getBoard().getStones(8));
        assertEquals(6, game.getBoard().getStones(9));
        assertEquals(6, game.getBoard().getStones(10));
        assertEquals(6, game.getBoard().getStones(11));
        assertEquals(6, game.getBoard().getStones(12));
        assertEquals(0, game.getBoard().getStones(13));
        assertEquals(MancalaPlayerType.FIRST_PLAYER, game.getCurrentPlayer());
    }

    @Test
    void givenGame_whenMakeMoveWithCurrentBigPitIsOpponents_thenSkipTheOpponentBigPit() {
        int firstPlayerMoveIndexToSkipOpponentBigPit = 5;
        assertEquals(MancalaPlayerType.FIRST_PLAYER, game.getCurrentPlayer());
        int opponentBigPitIndex = game.getBoard().getBigPitIndexByPlayerType(MancalaPlayerType.SECOND_PLAYER);
        int stoneCountToSkip = 8;
        game.getBoard().getPit(firstPlayerMoveIndexToSkipOpponentBigPit).setStones(stoneCountToSkip);
        game.makeMove(firstPlayerMoveIndexToSkipOpponentBigPit);
        assertEquals(7, game.getBoard().getStones(0));
        assertEquals(6, game.getBoard().getStones(1));
        assertEquals(6, game.getBoard().getStones(2));
        assertEquals(6, game.getBoard().getStones(3));
        assertEquals(6, game.getBoard().getStones(4));
        assertEquals(0, game.getBoard().getStones(firstPlayerMoveIndexToSkipOpponentBigPit));
        assertEquals(1, game.getBoard().getStones(6));
        assertEquals(7, game.getBoard().getStones(7));
        assertEquals(7, game.getBoard().getStones(8));
        assertEquals(7, game.getBoard().getStones(9));
        assertEquals(7, game.getBoard().getStones(10));
        assertEquals(7, game.getBoard().getStones(11));
        assertEquals(7, game.getBoard().getStones(12));
        assertEquals(0, game.getBoard().getStones(opponentBigPitIndex));
    }

    @Test
    void givenGame_whenMakeMoveToCurrentPlayersEmptyPit_thenCaptureOpponentStones() {
        game.getBoard().getPit(0).setStones(1);
        game.getBoard().getPit(1).setStones(0);
        game.makeMove(0);
        int expectedStonesInFirstPlayerBigPit = 7;
        assertEquals(expectedStonesInFirstPlayerBigPit,
                game.getBoard().getBigPitStonesByPlayerType(MancalaPlayerType.FIRST_PLAYER));
    }

    @Test
    void givenGame_whenMakeMoveToOpponentPlayersEmptyPit_thenDoNotCaptureStones() {
        int opponentSidePitIndexToBeCleared = 8;
        int firstPlayerMoveIndex = 5;
        int expectedStonesInFirstPlayersBigPit = 1;
        game.getBoard().clearPit(opponentSidePitIndexToBeCleared);
        game.getBoard().getPit(firstPlayerMoveIndex).setStones(3);
        assertEquals(MancalaPlayerType.FIRST_PLAYER, game.getCurrentPlayer());
        game.makeMove(firstPlayerMoveIndex);
        assertEquals(MancalaPlayerType.SECOND_PLAYER, game.getCurrentPlayer());
        assertEquals(expectedStonesInFirstPlayersBigPit,
                game.getBoard().getBigPitStonesByPlayerType(MancalaPlayerType.FIRST_PLAYER));
    }

    @Test
    void givenGame_whenAnyOfThePlayerSideStonesEmpty_thenGameWillEnd() {
        int startIndexOfSecondPlayerPits = MancalaBoard.REGULAR_PITS_PER_PLAYER + 1;
        for (int i = startIndexOfSecondPlayerPits; i < MancalaBoard.TOTAL_PITS; i++) {
            game.getBoard().clearPit(i);
        }
        int firstPlayerMoveIndex = MancalaBoard.REGULAR_PITS_PER_PLAYER - 1;
        int stonesToMove = 1;
        game.getBoard().getPit(firstPlayerMoveIndex).setStones(stonesToMove);
        game.makeMove(firstPlayerMoveIndex);
        assertEquals(MancalaGameStatus.FIRST_PLAYER_WON, game.getGameStatus());
        assertTrue(game.isGameFinished());
    }

//TODO: Exceptions
    @Test
    void givenGame_whenPlayerTriesToSetStonesInOpponentPlayersPit_thenThrowsException() {
        assertEquals(MancalaPlayerType.FIRST_PLAYER, game.getCurrentPlayer());
        int bigPitIndex = MancalaBoard.REGULAR_PITS_PER_PLAYER;
        int secondPlayerPitIndex = MancalaBoard.REGULAR_PITS_PER_PLAYER + 1;

        Throwable exception1 = assertThrows(InvalidPitException.class, () -> game.makeMove(bigPitIndex));
        assertEquals("Cannot select from big pit.", exception1.getMessage());
        Throwable exception2 = assertThrows(InvalidPitException.class, () -> game.makeMove(secondPlayerPitIndex));
        assertEquals("Selected pit does not belong to the current player.", exception2.getMessage());
    }
}
