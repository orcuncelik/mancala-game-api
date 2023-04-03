package com.celik.mancalaapi.infrastructure.repository;

import com.celik.mancalaapi.domain.ports.out.MancalaGameRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MancalaGameRepositoryAdapter implements MancalaGameRepositoryPort {
    private MancalaGameRepository gameRepository;

    @Autowired
    public MancalaGameRepositoryAdapter(MancalaGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }



}
