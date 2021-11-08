package ru.leonov.neotraining.dto.executed_operations_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.leonov.neotraining.entities.TechMapEntity;

public class ExecutedOperationsDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("techMap")
    private TechMapEntity techMap;

    @JsonProperty("date")
    private String date;

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
}
