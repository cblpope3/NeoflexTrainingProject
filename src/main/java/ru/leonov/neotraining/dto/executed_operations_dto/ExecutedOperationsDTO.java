package ru.leonov.neotraining.dto.executed_operations_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapDTO;

public class ExecutedOperationsDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("techMap")
    private TechMapDTO techMap;

    @JsonProperty("date")
    private String date;

    public ExecutedOperationsDTO(int id, TechMapDTO techMap, String date) {
        this.id = id;
        this.techMap = techMap;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TechMapDTO getTechMap() {
        return techMap;
    }

    public void setTechMap(TechMapDTO techMap) {
        this.techMap = techMap;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toJSON() {
        return "{" +
                "\"id\":" + id +
                ", \"techMap\":" + techMap.toJSON() +
                ", \"date\":\"" + date + '\"' +
                '}';
    }
}
