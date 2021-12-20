package ru.leonov.neotraining.entities;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "executed_operations")
public class ExecutedOperationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operations_seq_gen")
    @SequenceGenerator(name = "operations_seq_gen", sequenceName = "executed_operations_id_sequence", allocationSize = 1)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "techMap_id")
    private TechMapEntity techMap;

    private String date;

    public ExecutedOperationsEntity() {
    }

    public ExecutedOperationsEntity(TechMapEntity techMap) {
        this.techMap = techMap;

        ZoneId currentTimeZone = ZoneId.of("Europe/Moscow");
        ZonedDateTime currentDate = ZonedDateTime.now(currentTimeZone);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        this.date = currentDate.format(dateFormatter);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TechMapEntity getTechMap() {
        return techMap;
    }

    public void setTechMap(TechMapEntity techMap) {
        this.techMap = techMap;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExecutedOperationsEntity{" +
                "techMap=" + techMap.toString() +
                ", date='" + date + '\'' +
                '}';
    }
}
