package nl.quintor.aegonsimplecalculator.model.MapStruct;

import nl.quintor.aegonsimplecalculator.model.Calculation;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDao;
import nl.quintor.aegonsimplecalculator.model.CalculationResultDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CalculationResultDaoMapper {

    CalculationResultDaoMapper INSTANCE = Mappers.getMapper( CalculationResultDaoMapper.class );

    @Mapping(target = "calculation", ignore = true)
    CalculationResultDao calculationToCalculationResultDao(Calculation calculation);
    CalculationResultDto calculationResultDaoToCalculationResultDto(CalculationResultDao calculationResultDao);

    @AfterMapping
    default void setCalculation(@MappingTarget CalculationResultDao calculationResultDao, Calculation calculation) {
        calculationResultDao.setCalculation(
                String.format("%d %s %d", calculation.getPrefix(), calculation.getOperator(), calculation.getSuffix()));
    }
}
