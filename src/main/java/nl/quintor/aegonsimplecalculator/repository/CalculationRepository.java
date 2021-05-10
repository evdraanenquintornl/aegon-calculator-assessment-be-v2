package nl.quintor.aegonsimplecalculator.repository;

import nl.quintor.aegonsimplecalculator.model.CalculationResultDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationRepository extends JpaRepository<CalculationResultDao, Long> {
}
