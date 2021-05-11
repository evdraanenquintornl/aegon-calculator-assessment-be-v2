package nl.quintor.aegonsimplecalculator.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleCalculatorTest {
    private SimpleCalculator simpleCalculator;

    @BeforeEach
    void setup() {
        this.simpleCalculator = new SimpleCalculator();
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "1,2,3",
            "-10,10,0",
            "-10,-5,-15",
            "2147483647,1,-2147483648"})
    void should_returnCorrectResult_when_add(int prefix, int suffix, double expectedResult) {
        //  ARRANGE
        //  ACT
        double actualResult = this.simpleCalculator.add(prefix, suffix);
        //  ASSERT
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,0",
            "1,2,-1",
            "-10,10,-20",
            "-10,-5,-5",
            "-2147483648,1,2147483647"})
    void should_returnCorrectResult_when_subtract(int prefix, int suffix, double expectedResult) {
        //  ARRANGE
        //  ACT
        double actualResult = this.simpleCalculator.subtract(prefix, suffix);
        //  ASSERT
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,1",
            "1,2,2",
            "-10,10,-100",
            "-10,-5,50",
            "-2147483648,-2147483648,0",
            "2147483647,2147483647,1.0"})
    void should_returnCorrectResult_when_multiply(int prefix, int suffix, double expectedResult) {
        //  ARRANGE
        //  ACT
        double actualResult = this.simpleCalculator.multiply(prefix, suffix);
        //  ASSERT
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,1",
            "1,2,0.5",
            "-10,10,-1",
            "-10,-5,2",
            "-2147483648,2147483647,-1.0000000004656613",
            "-2147483648,-2147483648,1.0",
            "2147483647,2147483647,1.0"})
    void should_returnCorrectResult_when_divide(int prefix, int suffix, double expectedResult) {
        //  ARRANGE
        //  ACT
        double actualResult = this.simpleCalculator.divide(prefix, suffix);
        //  ASSERT
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "1,0",
            "0,2",
            "0,0"})
    public void should_throwBadRequest_when_divideByZero(int prefix, int suffix) {
        //  ARRANGE
        //  ACT
        Throwable throwable = assertThrows(ResponseStatusException.class, () -> this.simpleCalculator.divide(prefix, suffix));
        //  ASSERT
        assertThat(throwable.getMessage()).isEqualTo("400 BAD_REQUEST \"Unable to divide by Zero\"");
    }
}