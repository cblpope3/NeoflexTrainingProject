package ru.leonov.neotraining.data_containers;

public class ExecutedOperationsJSONContainer {

    private int workerId;
    private int materialId;
    private int techMapId;

    public ExecutedOperationsJSONContainer() {
    }

    public ExecutedOperationsJSONContainer(int workerId, int materialId, int techMapId) {
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

    @Override
    public String toString() {
        return "ExecutedOperationsJSONContainer{" +
                "workerId=" + workerId +
                ", materialId=" + materialId +
                ", techMapId=" + techMapId +
                '}';
    }
}
