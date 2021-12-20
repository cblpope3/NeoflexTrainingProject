package ru.leonov.neotraining.dto.workers_dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkerPostDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "lastName", required = true)
    private String lastName;

    public WorkerPostDTO() {
    }

    public WorkerPostDTO(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    public boolean isDataCorrect() {
        return (name != null) && (lastName != null);
    }
}
