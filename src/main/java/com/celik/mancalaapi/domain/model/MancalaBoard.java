package com.celik.mancalaapi.domain.model;

import com.celik.mancalaapi.domain.model.enums.MancalaPitType;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Data
public class MancalaBoard {
    public static final int INITIAL_STONES_PER_PIT = 6;
    public static final int REGULAR_PITS_PER_PLAYER = 6;
    public static final int BIG_PITS_PER_PLAYER = 2;
    public static final int TOTAL_PLAYERS = 2;
    public static final int TOTAL_PITS = (REGULAR_PITS_PER_PLAYER * TOTAL_PLAYERS) + BIG_PITS_PER_PLAYER;

    private final MancalaPit[] pits;

    public MancalaBoard() {
        pits = new MancalaPit[TOTAL_PITS];
        IntStream.range(0, TOTAL_PITS).forEach(idx ->
                pits[idx] = new MancalaPit(idx)
        );
    }

    public List<Integer> getRegularPitsByPlayerType(MancalaPlayerType playerType) {
        return Arrays.stream(pits)
                .filter(pit -> MancalaPitType.REGULAR_PIT.equals(pit.getPitType()) && pit.getPlayerType().equals(playerType))
                .map(MancalaPit::getStones)
                .toList();
    }

    public int getBigPitIndexByPlayerType(MancalaPlayerType playerType) {
        return MancalaPlayerType.FIRST_PLAYER.equals(playerType) ? REGULAR_PITS_PER_PLAYER : TOTAL_PITS - 1;
    }

    public int getBigPitStonesByPlayerType(MancalaPlayerType playerType) {
        return pits[getBigPitIndexByPlayerType(playerType)].getStones();
    }

    public void addStones(int index, int stones) {
        pits[index].addStones(stones);
    }

    public void incrementPitStones(int index) {
        pits[index].incrementStones();
    }

    public int getStones(int index) {
        return pits[index].getStones();
    }

    public int getOpponentSideStones(int index) {
        return pits[getOpponentSidePitIndex(index)].getStones();
    }

    public int getOpponentSidePitIndex(int index) {
        return (REGULAR_PITS_PER_PLAYER * TOTAL_PLAYERS) - index;
    }

    public void clearPit(int index) {
        pits[index].setStones(0);
    }

    public MancalaPitType getPitType(int index) {
        return pits[index].getPitType();
    }

    public MancalaPlayerType getPlayerType(int index) {
        return pits[index].getPlayerType();
    }

}
