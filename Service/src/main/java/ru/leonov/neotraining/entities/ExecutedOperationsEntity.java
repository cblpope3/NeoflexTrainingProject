package ru.leonov.neotraining.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "executed_operations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecutedOperationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operations_seq_gen")
    @SequenceGenerator(name = "operations_seq_gen", sequenceName = "executed_operations_id_sequence", allocationSize = 1)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "techMap_id")
    private TechMapEntity techMap;

    private String date;

    public ExecutedOperationsEntity(TechMapEntity techMap) {
        this.techMap = techMap;

        ZoneId currentTimeZone = ZoneId.of("Europe/Moscow");
        ZonedDateTime currentDate = ZonedDateTime.now(currentTimeZone);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        this.date = currentDate.format(dateFormatter);
    }

    @Override
    public String toString() {
        return "ExecutedOperationsEntity{" +
                "techMap=" + techMap.toString() +
                ", date='" + date + '\'' +
                '}';
    }
}
