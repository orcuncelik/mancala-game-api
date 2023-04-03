package com.celik.mancalaapi.infrastructure.config;

import com.celik.mancalaapi.MancalaApiApplication;
import com.celik.mancalaapi.domain.ports.in.MancalaGameServicePort;
import com.celik.mancalaapi.domain.ports.out.MancalaGameRepositoryPort;
import com.celik.mancalaapi.domain.service.MancalaGameService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = MancalaApiApplication.class)
public class BeanConfiguration {

    @Bean
    MancalaGameServicePort orderService(final MancalaGameRepositoryPort gameRepositoryPort) {
        return new MancalaGameService(gameRepositoryPort);
    }

}
