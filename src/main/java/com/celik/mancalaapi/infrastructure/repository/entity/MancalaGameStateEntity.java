package com.celik.mancalaapi.infrastructure.repository.entity;

import com.celik.mancalaapi.domain.model.enums.MancalaGameStatus;
import com.celik.mancalaapi.domain.model.enums.MancalaPlayerType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MancalaGameStateEntity {
    @Id
    private UUID gameId;
    @ElementCollection
    private List<Integer> player1Pits;
    private int player1BigPit;
    @ElementCollection
    private List<Integer> player2Pits;
    private int player2BigPit;
    @Enumerated(EnumType.STRING)
    private MancalaGameStatus gameStatus;
    @Enumerated(EnumType.STRING)
    private MancalaPlayerType currentPlayer;
}
