package nl.quintor.aegonsimplecalculator.service;

import nl.quintor.aegonsimplecalculator.model.Calculation;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDao;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDto;
import nl.quintor.aegonsimplecalculator.repository.CalculationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

    @InjectMocks
    private CalculationService calculationService;
    @Mock
    private CalculationRepository calculationRepository;
    @Mock
    private SimpleCalculator simpleCalculator;

    @Test
    void should_getAllCalculations_when_allSucceed() {
        //  ARRANGE
        List<CalculationResultDao> calculationResultDaoList = List.of(new CalculationResultDao());
        Mockito.when(calculationRepository.findAll()).thenReturn(calculationResultDaoList);
        //  ACT
        this.calculationService.getAllCalculations();
        //  ASSERT
        verify(calculationRepository, times(1)).findAll();
    }

    @Test
    void should_calculateAndSave_when_allSucceed() {
        //  ARRANGE
        CalculationService calculationService = spy(this.calculationService);
        Calculation calculation = new Calculation(1, "/", 2);
        //  ACT
        calculationService.calculate(calculation);
        //  ASSERT
        verify(calculationService, times(1)).doSimpleCalculation(calculation);
        verify(calculationRepository, times(1)).save(any(CalculationResultDao.class));
    }

    @Test
    public void should_calculateCorrectResult_when_operatorIsPlus() {
        //  ARRANGE
        int prefix = 1;
        int suffix = 2;
        Calculation calculation = new Calculation(prefix, "+", suffix);
        //  ACT
        this.calculationService.doSimpleCalculation(calculation);
        //  ASSERT
        verify(simpleCalculator, times(1)).add(prefix, suffix);
    }

    @Test
    public void should_calculateCorrectResult_when_operatorIsMinus() {
        //  ARRANGE
        int prefix = 1;
        int suffix = 2;
        Calculation calculation = new Calculation(prefix, "-", suffix);
        //  ACT
        this.calculationService.doSimpleCalculation(calculation);
        //  ASSERT
        verify(simpleCalculator, times(1)).subtract(prefix, suffix);
    }

    @Test
    public void should_calculateCorrectResult_when_operatorIsForwardSlash() {
        //  ARRANGE
        int prefix = 1;
        int suffix = 2;
        Calculation calculation = new Calculation(prefix, "/", suffix);
        //  ACT
        this.calculationService.doSimpleCalculation(calculation);
        //  ASSERT
        verify(simpleCalculator, times(1)).divide(prefix, suffix);
    }

    @Test
    public void should_calculateCorrectResult_when_operatorIsMultiply() {
        //  ARRANGE
        int prefix = 1;
        int suffix = 2;
        Calculation calculation = new Calculation(prefix, "X", suffix);

        //  ACT
        this.calculationService.doSimpleCalculation(calculation);

        //  ASSERT
        verify(simpleCalculator, times(1)).multiply(prefix, suffix);
    }

    @Test
    public void should_throwBadRequestException_when_operatorIsNotSupported() {
        //  ARRANGE
        String operator = "*";
        Calculation calculation = new Calculation(1, operator, 2);
        //  ACT
        Throwable throwable = assertThrows(ResponseStatusException.class, () -> this.calculationService.doSimpleCalculation(calculation));
        //  ASSERT
        assertThat(throwable.getMessage()).isEqualTo(
                String.format("400 BAD_REQUEST \"Operator %s not supported\"", operator));
    }
}