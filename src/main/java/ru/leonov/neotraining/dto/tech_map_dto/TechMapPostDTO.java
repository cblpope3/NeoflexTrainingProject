package ru.leonov.neotraining.dto.tech_map_dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TechMapPostDTO {

    @JsonProperty("worker_id")
    private int workerId;

    @JsonProperty("material_id")
    private int materialId;

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
}
