package ru.leonov.neotraining.dto.material_dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaterialDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public MaterialDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
