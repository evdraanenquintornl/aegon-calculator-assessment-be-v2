package nl.quintor.aegonsimplecalculator.service;

import org.springframework.stereotype.Service;

@Service
public class SimpleCalculator {

    public double add(int leftSide, int rightSide) {
        return leftSide + rightSide;
    }

    public double subtract(int leftSide, int rightSide) {
        return leftSide - rightSide;
    }

    public double multiply(int leftSide, int rightSide) {
        return leftSide * rightSide;
    }

    public double divide(int leftSide, int rightSide) {
        return (double) leftSide / rightSide;
    }
}
