package nl.quintor.aegonsimplecalculator.controller;

import nl.quintor.aegonsimplecalculator.model.CalculationDto;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDao;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDto;
import nl.quintor.aegonsimplecalculator.model.MapStruct.CalculationMapper;
import nl.quintor.aegonsimplecalculator.model.MapStruct.CalculationResultDaoMapper;
import nl.quintor.aegonsimplecalculator.service.CalculationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calculations")
@CrossOrigin("http://localhost:4200")
public class CalculationController {
    private final CalculationService calculationService;
    private final CalculationMapper calculationMapper;

    public CalculationController(CalculationService calculationService, CalculationMapper calculationMapper) {
        this.calculationService = calculationService;
        this.calculationMapper = calculationMapper;
    }

    @GetMapping
    public ResponseEntity<List<CalculationResultDto>> getAllCalculations() {
        return ResponseEntity.ok(
                calculationService.getAllCalculations().stream()
                        .map(CalculationResultDaoMapper.INSTANCE::calculationResultDaoToCalculationResultDto)
                        .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<CalculationResultDto> calculate(@Valid @RequestBody CalculationDto calculationDto) {
        if (calculationDto.getOperator().equals("/") &&
                (calculationDto.getPrefix() == 0 || calculationDto.getSuffix() == 0)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to divide by Zero");
        }
        CalculationResultDao calculationResultDao = this.calculationService.calculate(
                CalculationMapper.INSTANCE.calculationDtoToCalculation(calculationDto));

        return ResponseEntity.ok(
                CalculationResultDaoMapper.INSTANCE.calculationResultDaoToCalculationResultDto(calculationResultDao));
    }
}
