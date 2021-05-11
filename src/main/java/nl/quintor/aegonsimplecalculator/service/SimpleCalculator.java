package nl.quintor.aegonsimplecalculator.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SimpleCalculator {

    public double add(int prefix, int suffix) {
        return prefix + suffix;
    }

    public double subtract(int prefix, int suffix) {
        return prefix - suffix;
    }

    public double multiply(int prefix, int suffix) {
        return prefix * suffix;
    }

    public double divide(int prefix, int suffix) {
        if (prefix == 0 || suffix == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to divide by Zero");
        }

        return (double) prefix / suffix;
    }
}
