package com.celik.mancalaapi.domain.model;

import com.celik.mancalaapi.domain.exception.InvalidPitException;
import com.celik.mancalaapi.domain.model.enums.MancalaPitType;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import lombok.Data;

import static com.celik.mancalaapi.domain.model.MancalaBoard.REGULAR_PITS_PER_PLAYER;

@Data
public class MancalaPit {
    private final int index;
    private final MancalaPlayerType playerType;
    private final MancalaPitType pitType;
    private int stones;

    public MancalaPit(int index) {
        this.index = index;
        validatePit();
        this.playerType = getPlayerType();
        this.pitType = getPitType();
        this.stones = getInitialStones();
    }

    private void validatePit() {
        if (index >= MancalaBoard.TOTAL_PITS || index < 0)
            throw new InvalidPitException("Invalid pit index: " + index);
    }

    private int getInitialStones() {
        return MancalaPitType.REGULAR_PIT.equals(pitType) ? MancalaBoard.INITIAL_STONES_PER_PIT : 0;
    }

    public MancalaPitType getPitType() {
        return index % (REGULAR_PITS_PER_PLAYER + 1) == REGULAR_PITS_PER_PLAYER ? MancalaPitType.BIG_PIT : MancalaPitType.REGULAR_PIT;
    }

    public MancalaPlayerType getPlayerType() {
        return index > REGULAR_PITS_PER_PLAYER ? MancalaPlayerType.SECOND_PLAYER : MancalaPlayerType.FIRST_PLAYER;
    }

    public void incrementStones() {
        stones += 1;
    }

    public void addStones(int incomingStones) {
        stones += incomingStones;
    }
}
