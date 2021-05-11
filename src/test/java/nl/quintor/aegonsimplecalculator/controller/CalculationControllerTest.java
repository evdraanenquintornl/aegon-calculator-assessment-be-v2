package nl.quintor.aegonsimplecalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.quintor.aegonsimplecalculator.model.Calculation;
import nl.quintor.aegonsimplecalculator.model.CalculationDto;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDao;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDto;
import nl.quintor.aegonsimplecalculator.model.MapStruct.CalculationMapper;
import nl.quintor.aegonsimplecalculator.service.CalculationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculationController.class)
class CalculationControllerTest {
    private final String calculationsUrl = "/calculations";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculationService calculationService;

    @MockBean
    private CalculationMapper calculationMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void should_getAllCalculations_whenSucceed() throws Exception {
        // ARRANGE
        String calculation = "1x2";
        double result = 3;

        CalculationResultDao calculationDao = new CalculationResultDao();
        calculationDao.setCalculation(calculation);
        calculationDao.setResult(result);

        CalculationResultDto calculationResultDto = new CalculationResultDto(calculation, result);

        when(this.calculationService.getAllCalculations()).thenReturn(List.of(calculationDao));

        //  ACT
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(calculationsUrl))
                .andExpect(status().isOk())
                .andReturn();
        CalculationResultDto[] actualList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CalculationResultDto[].class);

        //  ASSERT
        verify(calculationService, times(1)).getAllCalculations();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(actualList).contains(calculationResultDto);
    }

    @ParameterizedTest
    @CsvSource({
            "1,+,1, 2",
            "30,X,0, 0"})
    void should_correctlyCalculate_when_Succeed(int prefix,String operator, int suffix, double result) throws Exception {
        //  ARRANGE
        String calculationString = "String.format(\"%d%s%d\", prefix, operator, suffix)";
        CalculationDto calculationDto = new CalculationDto();
        calculationDto.setPrefix(prefix);
        calculationDto.setOperator(operator);
        calculationDto.setSuffix(suffix);

        Calculation calculation = new Calculation(prefix, operator, suffix);

        CalculationResultDao calculationResultDao = new CalculationResultDao();
        calculationResultDao.setCalculation(calculationString);
        calculationResultDao.setResult(result);

        CalculationResultDto calculationResultDto = new CalculationResultDto(calculationString, result);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(calculationsUrl);
        requestBuilder.content(objectMapper.writeValueAsString(calculationDto));
        requestBuilder.contentType(MediaType.APPLICATION_JSON);

        when(calculationService.calculate(calculation)).thenReturn(calculationResultDao);

        //  ACT
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();
        CalculationResultDto actualList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CalculationResultDto.class);

        //  ASSERT
        verify(calculationService, times(1)).calculate(any());
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(actualList).isEqualTo(calculationResultDto);
    }

    @Test
    void should_badRequest_when_requestBodyIsMissing() throws Exception {
        //  ARRANGE
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(calculationsUrl);

        //  ACT
        //  ASSERT
        this.mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertThat(result.getResolvedException()
                        instanceof HttpMessageNotReadableException))
                .andExpect(result -> Assertions.assertThat(result.getResolvedException().getMessage())
                        .contains("Required request body is missing:"));

    }

    @Test
    void should_throwBadRequest_when_OperatorIsEmpty() throws Exception {
        //  ARRANGE
        CalculationDto calculationDto = new CalculationDto();
        calculationDto.setPrefix(0);
        calculationDto.setOperator("");
        calculationDto.setSuffix(0);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(calculationsUrl);
        requestBuilder.content(objectMapper.writeValueAsString(calculationDto));
        requestBuilder.contentType(MediaType.APPLICATION_JSON);


        //  ACT
        //  ASSERT
        this.mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertThat(result.getResolvedException()
                        instanceof HttpMessageNotReadableException))
                .andExpect(result -> Assertions.assertThat(result.getResolvedException().getMessage())
                        .contains("Validation failed for argument", "NotEmpty.operator"));
    }
}