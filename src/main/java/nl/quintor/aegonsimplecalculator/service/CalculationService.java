package nl.quintor.aegonsimplecalculator.service;

import nl.quintor.aegonsimplecalculator.model.Calculation;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDao;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDto;
import nl.quintor.aegonsimplecalculator.model.MapStruct.CalculationResultDaoMapper;
import nl.quintor.aegonsimplecalculator.repository.CalculationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculationService {
    private final SimpleCalculator simpleCalculator;
    private final CalculationRepository calculationRepository;

    public CalculationService(SimpleCalculator simpleCalculator, CalculationRepository calculationRepository) {
        this.simpleCalculator = simpleCalculator;
        this.calculationRepository = calculationRepository;
    }

    public List<CalculationResultDao> getAllCalculations() {
        return this.calculationRepository.findAll();
    }

    public CalculationResultDao calculate(Calculation calculation) {
        doSimpleCalculation(calculation);

        CalculationResultDao calculationResult = this.calculationRepository
                .save(CalculationResultDaoMapper.INSTANCE.calculationToCalculationResultDao(calculation));
        return calculationResult;
    }

    void doSimpleCalculation(Calculation calculation) {
        switch (calculation.getOperator()) {
            case "/":
                calculation.setResult(simpleCalculator.divide(calculation.getPrefix(), calculation.getSuffix()));
                break;
            case "X":
                calculation.setResult(simpleCalculator.multiply(calculation.getPrefix(), calculation.getSuffix()));
                break;
            case "-":
                calculation.setResult(simpleCalculator.subtract(calculation.getPrefix(), calculation.getSuffix()));
                break;
            case "+":
                calculation.setResult(simpleCalculator.add(calculation.getPrefix(), calculation.getSuffix()));
                break;
            default: throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Operator %s not supported", calculation.getOperator()));
        }
    }
}
