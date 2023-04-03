package com.celik.mancalaapi.infrastructure.repository.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MancalaGameStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String gameId;
    @ElementCollection
    private List<Integer> player1Pits;
    private int player1BigPit;
    @ElementCollection
    private List<Integer> player2Pits;
    private int player2BigPit;
    private String currentPlayer;
}
