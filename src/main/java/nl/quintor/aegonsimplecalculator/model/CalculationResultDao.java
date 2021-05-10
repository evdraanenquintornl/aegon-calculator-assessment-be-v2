package nl.quintor.aegonsimplecalculator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class CalculationResultDao {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @Setter
    private String calculation;
    @Setter
    private double result;
}
