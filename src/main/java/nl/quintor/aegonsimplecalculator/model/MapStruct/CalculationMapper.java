package nl.quintor.aegonsimplecalculator.model.MapStruct;

import nl.quintor.aegonsimplecalculator.model.Calculation;
import nl.quintor.aegonsimplecalculator.model.CalculationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CalculationMapper {

    CalculationMapper INSTANCE = Mappers.getMapper( CalculationMapper.class );

    Calculation calculationDtoToCalculation(CalculationDto calculationDto);
}
