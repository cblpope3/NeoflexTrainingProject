package ru.leonov.neotraining.dto.tech_map_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.leonov.neotraining.dto.material_dto.MaterialDTO;
import ru.leonov.neotraining.dto.workers_dto.WorkerDTO;

public class TechMapDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("worker")
    private WorkerDTO worker;

    @JsonProperty("material")
    private MaterialDTO material;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorkerDTO getWorker() {
        return worker;
    }

    public void setWorker(WorkerDTO worker) {
        this.worker = worker;
    }

    public MaterialDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDTO material) {
        this.material = material;
    }
}
