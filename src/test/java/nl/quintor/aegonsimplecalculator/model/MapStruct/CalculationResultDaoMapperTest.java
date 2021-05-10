package nl.quintor.aegonsimplecalculator.model.MapStruct;

import nl.quintor.aegonsimplecalculator.model.Calculation;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDao;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalculationResultDaoMapperTest {
    private final CalculationResultDaoMapper calculationResultDaoMapper = CalculationResultDaoMapper.INSTANCE;

    @Test
    void testCalculationToCalculationResultDao() {
        //  ARRANGE
        int prefix = 1;
        String operator = "+";
        int suffix = 1;
        double result = 2;

        Calculation calculation = new Calculation(prefix, operator, suffix);
        calculation.setResult(result);

        CalculationResultDao calculationResultDao = new CalculationResultDao();
        calculationResultDao.setCalculation(String.format("%d %s %d", prefix, operator, suffix));
        calculationResultDao.setResult(result);

        //  ACT
        CalculationResultDao expected = calculationResultDaoMapper.calculationToCalculationResultDao(calculation);

        //  ASSERT
        Assertions.assertThat(expected.getCalculation()).isEqualTo(calculationResultDao.getCalculation());
        Assertions.assertThat(expected.getResult()).isEqualTo(calculationResultDao.getResult());
    }

    @Test
    void testCalculationResultDaoToCalculationResultDto() {
        //  ARRANGE
        int prefix = 1;
        String operator = "+";
        int suffix = 1;
        double result = 2;

        CalculationResultDao calculationResultDao = new CalculationResultDao();
        calculationResultDao.setCalculation(String.format("%d %s %d", prefix, operator, suffix));
        calculationResultDao.setResult(result);

        CalculationResultDto calculationResultDto = new CalculationResultDto(String.format("%d %s %d", prefix, operator, suffix), result);
        //  ACT
        CalculationResultDto expected = calculationResultDaoMapper.calculationResultDaoToCalculationResultDto(calculationResultDao);
        //  ASSERT
        Assertions.assertThat(expected).isEqualTo(calculationResultDto);
    }

    @Test
    void setCalculation() {
        //  ARRANGE
        int prefix = 1;
        String operator = "+";
        int suffix = 1;

        Calculation calculation = new Calculation(prefix, operator, suffix);

        CalculationResultDao calculationResultDao = new CalculationResultDao();
        calculationResultDao.setCalculation(String.format("%d %s %d", prefix, operator, suffix));

        //  ACT
        calculationResultDaoMapper.setCalculation(calculationResultDao, calculation);

        //  ASSERT
        Assertions.assertThat(calculationResultDao.getCalculation()).isEqualTo(
                String.format("%d %s %d", calculation.getPrefix(), calculation.getOperator(), calculation.getSuffix()));
    }
}