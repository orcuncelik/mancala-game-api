package com.celik.mancalaapi.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class MakeMoveRequestDTO {

    @NotNull
    @Min(value = 0, message = "Pit index cannot be negative.")
    @Max(value = 5, message = "Pit index cannot be bigger than 5.")
    private final Integer pitIndex;

    @JsonCreator
    public MakeMoveRequestDTO(@JsonProperty("pitIndex") int pitIndex) {
        this.pitIndex = pitIndex;
    }
}
