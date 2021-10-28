package ru.leonov.neotraining.entities;

import javax.persistence.*;

@Entity
@Table(name = "executed_operations")
public class ExecutedOperationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    private TechMapEntity techMap;

    private String date;

    public ExecutedOperationsEntity() {
    }

    public ExecutedOperationsEntity(TechMapEntity techMap, String date) {
        this.techMap = techMap;
        this.date = date;
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
