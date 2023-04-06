package com.celik.mancalaapi.domain.model;

import com.celik.mancalaapi.domain.model.enums.MancalaGameStatus;
import com.celik.mancalaapi.domain.model.enums.MancalaPitType;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MancalaGame {
    private final UUID id;
    private final MancalaBoard board;
    private MancalaGameStatus gameStatus;
    private MancalaPlayerType currentPlayer;

    public MancalaGame() {
        this.id = UUID.randomUUID();
        this.board = new MancalaBoard();
        this.gameStatus = MancalaGameStatus.CONTINUING;
        this.currentPlayer = MancalaPlayerType.FIRST_PLAYER;
    }

    public void makeMove(int pitIndex) {
        validatePit(pitIndex);
        int stones = board.getStones(pitIndex);
        board.clearPit(pitIndex);
        int lastPitIndex = distributeStones(pitIndex, stones);
        if (isGameEnded()) {
            initGameResult();
            return;
        }
        if (!didLastStoneLandInCurrentPlayerBigPit(lastPitIndex))
            switchCurrentPlayer();
    }

    private void initGameResult() {
        int firstPlayerScore = board.getBigPitStonesByPlayerType(MancalaPlayerType.FIRST_PLAYER);
        int secondPlayerScore = board.getBigPitStonesByPlayerType(MancalaPlayerType.SECOND_PLAYER);
        if (firstPlayerScore > secondPlayerScore)
            gameStatus = MancalaGameStatus.FIRST_PLAYER_WON;
        else if (firstPlayerScore < secondPlayerScore)
            gameStatus = MancalaGameStatus.SECOND_PLAYER_WON;
        else
            gameStatus = MancalaGameStatus.DRAW;
    }

    private boolean isGameEnded() {
        List<Integer> player1Pits = board.getRegularPitsByPlayerType(MancalaPlayerType.FIRST_PLAYER);
        List<Integer> player2Pits = board.getRegularPitsByPlayerType(MancalaPlayerType.SECOND_PLAYER);
        boolean player1Empty = player1Pits.stream().allMatch(pit -> pit == 0);
        boolean player2Empty = player2Pits.stream().allMatch(pit -> pit == 0);
        return player1Empty || player2Empty;
    }

    private int distributeStones(int pitIndex, int stones) {
        int currentIndex = pitIndex;
        for (int i = 1; i <= stones; i++) {
            currentIndex = (pitIndex + i) % MancalaBoard.TOTAL_PITS;
            if (isOpponentsBigPit(currentIndex)) {
                stones++;
                continue;
            }
            board.incrementPitStones(currentIndex);
        }
        if (canMoveOpponentStonesToBigPit(currentIndex)) {
            moveStonesToBigPit(currentIndex);
        }
        return currentIndex;
    }

    public void moveStonesToBigPit(int pitIndex) {
        int playerStonesToMove = board.getStones(pitIndex);
        int opponentStonesToMove = board.getOpponentSideStones(pitIndex);
        int totalStonesToMove = playerStonesToMove + opponentStonesToMove;
        board.clearPit(pitIndex);
        board.clearPit(board.getOpponentSidePitIndex(pitIndex));
        int bigPitIndex = board.getBigPitIndexByPlayerType(currentPlayer);
        board.addStones(bigPitIndex, totalStonesToMove);
    }

    private boolean canMoveOpponentStonesToBigPit(int index) {
        return board.getPlayerType(index) == currentPlayer
                && board.getPitType(index) == MancalaPitType.REGULAR_PIT
                && board.getOpponentSideStones(index) != 0
                && isCurrentPitHasOneStone(index);
    }

    private boolean isCurrentPitHasOneStone(int index) {
        return board.getStones(index) == 1;
    }

    private boolean isOpponentsBigPit(int pitIndex) {
        return MancalaPitType.BIG_PIT.equals(board.getPitType(pitIndex))
                && !currentPlayer.equals(board.getPlayerType(pitIndex));
    }

    private boolean didLastStoneLandInCurrentPlayerBigPit(int pitIndex) {
        return MancalaPitType.BIG_PIT.equals(board.getPitType(pitIndex))
                && currentPlayer.equals(board.getPlayerType(pitIndex));
    }

    private void validatePit(int pitIndex) {
        if (!board.getPlayerType(pitIndex).equals(currentPlayer)) {
            throw new IllegalArgumentException("Selected pit does not belong to the current player.");
        }
        if (board.getStones(pitIndex) == 0) {
            throw new IllegalArgumentException("Selected pit does not have any stones to move. Select again.");
        }
    }

    public void switchCurrentPlayer() {
        currentPlayer = (currentPlayer == MancalaPlayerType.FIRST_PLAYER)
                ? MancalaPlayerType.SECOND_PLAYER
                : MancalaPlayerType.FIRST_PLAYER;
    }

    public boolean isGameFinished() {
        return gameStatus == MancalaGameStatus.FIRST_PLAYER_WON || gameStatus == MancalaGameStatus.SECOND_PLAYER_WON;
    }

    public MancalaGameState toGameState() {
        return MancalaGameState.builder()
                .gameId(id.toString())
                .player1Pits(board.getRegularPitsByPlayerType(MancalaPlayerType.FIRST_PLAYER))
                .player1BigPit(board.getBigPitStonesByPlayerType(MancalaPlayerType.FIRST_PLAYER))
                .player2Pits(board.getRegularPitsByPlayerType(MancalaPlayerType.SECOND_PLAYER))
                .player2BigPit(board.getBigPitStonesByPlayerType(MancalaPlayerType.SECOND_PLAYER))
                .gameStatus(gameStatus)
                .currentPlayer(currentPlayer)
                .build();
    }

}
