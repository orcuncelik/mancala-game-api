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

    public int getBigPitByPlayerType(MancalaPlayerType playerType) {
        int bigPitIndex = playerType == MancalaPlayerType.FIRST_PLAYER ? REGULAR_PITS_PER_PLAYER : TOTAL_PITS - 1;
        return pits[bigPitIndex].getStones();
    }

    public MancalaPit getPit(int index) {
        return pits[index];
    }

    public void setStones(int index, int stones) {
        pits[index].setStones(stones);
    }

    public int getStones(int index) {
        return pits[index].getStones();
    }

    /*
    public MancalaPitType getPitType(int index) {
        return pits[index].getPitType();
    }

    public MancalaPlayerType getPlayerType(int index) {
        return pits[index].getPlayerType();
    }
     */

}
