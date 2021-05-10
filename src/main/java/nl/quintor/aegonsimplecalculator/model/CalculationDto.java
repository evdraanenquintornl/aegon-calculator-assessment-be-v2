package nl.quintor.aegonsimplecalculator.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CalculationDto {
    @NotNull
    private int prefix;
    @NotEmpty
    private String operator;
    @NotNull
    private int suffix;
}
