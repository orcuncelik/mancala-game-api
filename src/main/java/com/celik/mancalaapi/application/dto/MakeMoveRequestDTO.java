package com.celik.mancalaapi.application.dto;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class MakeMoveRequestDTO {
    @Min(value = 0, message = "Pit cannot be negative")
    @Max(13)
    private int pitIndex;
}
