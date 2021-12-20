package ru.leonov.neotraining.dto.executed_operations_dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecutedOperationsPostDTO {

    @JsonProperty("workerId")
    private int workerId;

    @JsonProperty("materialId")
    private int materialId;

    @JsonProperty("techMapId")
    private int techMapId;

    public ExecutedOperationsPostDTO() {
    }

    public ExecutedOperationsPostDTO(int workerId, int materialId, int techMapId) {
        this.workerId = workerId;
        this.materialId = materialId;
        this.techMapId = techMapId;
    }

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

    public int getTechMapId() {
        return techMapId;
    }

    public void setTechMapId(int techMapId) {
        this.techMapId = techMapId;
    }
}
