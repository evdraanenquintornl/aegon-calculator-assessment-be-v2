package nl.quintor.aegonsimplecalculator.model;

import lombok.Value;

@Value
public class CalculationResultDto {
    String calculation;
    double result;
}
