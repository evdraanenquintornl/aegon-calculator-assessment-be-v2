package nl.quintor.aegonsimplecalculator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "CALCULATION_RESULT")
@Entity
@Getter
@NoArgsConstructor
public class CalculationResultDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private String calculation;
    @Setter
    private double result;
}
