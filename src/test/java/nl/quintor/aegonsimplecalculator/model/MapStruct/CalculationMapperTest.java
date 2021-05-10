package nl.quintor.aegonsimplecalculator.model.MapStruct;

import nl.quintor.aegonsimplecalculator.model.Calculation;
import nl.quintor.aegonsimplecalculator.model.CalculationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculationMapperTest {
    private final CalculationMapper mapper = CalculationMapper.INSTANCE;

    @Test
    void testCalculationDtoToCalculation() {
        //  ARRANGE
        int prefix = 1;
        String operator = "+";
        int suffix = 1;

        CalculationDto calculationDto = new CalculationDto();
        calculationDto.setPrefix(prefix);
        calculationDto.setOperator(operator);
        calculationDto.setSuffix(suffix);

        Calculation expected = new Calculation(prefix, operator, suffix);

        //  ACT
        Calculation calculation = mapper.calculationDtoToCalculation(calculationDto);

        //  ASSERT
        Assertions.assertThat(calculation).isEqualTo(expected);
    }
}