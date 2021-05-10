package nl.quintor.aegonsimplecalculator.model;

import lombok.Data;
import lombok.Setter;

@Data
public class Calculation {
    private int prefix;
    private String operator;
    private int suffix;
    @Setter
    private double result;

    public Calculation(int prefix, String operator, int suffix) {
        this.prefix = prefix;
        this.operator = operator;
        this.suffix = suffix;
    }
}
