package com.celik.mancalaapi.domain.model;

import com.celik.mancalaapi.domain.exception.InvalidPitException;
import lombok.Getter;

import static com.celik.mancalaapi.domain.model.MancalaBoard.PITS_PER_PLAYER;

public class MancalaPit {
    private final int index;
    @Getter
    private int stones;

    public MancalaPit(int index) {
        validatePit(index);
        this.index = index;
        this.stones = getInitialStones();
    }

    public void validatePit(int index) {
        if (index >= MancalaBoard.TOTAL_PITS || index < 0)
            throw new InvalidPitException("Invalid pit index: " + index);
    }

    private int getInitialStones() {
        return MancalaPitType.REGULAR_PIT.equals(getPitType()) ? MancalaBoard.INITIAL_STONES_PER_PIT : 0;
    }

    public MancalaPitType getPitType() {
        return index % (PITS_PER_PLAYER + 1) == PITS_PER_PLAYER ? MancalaPitType.BIG_PIT : MancalaPitType.REGULAR_PIT;
    }

    public MancalaPlayerType getPlayerType() {
        return index / PITS_PER_PLAYER < 1 ? MancalaPlayerType.FIRST_PLAYER : MancalaPlayerType.SECOND_PLAYER;
    }
}
