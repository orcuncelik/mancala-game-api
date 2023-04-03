package com.celik.mancalaapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.celik.mancalaapi.domain.model.MancalaBoard.PITS_PER_PLAYER;

@AllArgsConstructor
@Data
public class MancalaPit {
    private int index;
    private int stones;

    public MancalaPit(int index) {
        this.index = index;
        this.stones = getInitialStones();
    }

    private int getInitialStones() {
        return MancalaPitType.REGULAR_PIT.equals(getPitType()) ? MancalaBoard.INITIAL_STONES_PER_PIT : 0;
    }

    public MancalaPitType getPitType() {
        return index % PITS_PER_PLAYER != 0 ? MancalaPitType.REGULAR_PIT : MancalaPitType.BIG_PIT;
    }

    public MancalaPlayerType getPlayerType() {
        return index / PITS_PER_PLAYER < 1 ? MancalaPlayerType.FIRST_PLAYER : MancalaPlayerType.SECOND_PLAYER;
    }
}
