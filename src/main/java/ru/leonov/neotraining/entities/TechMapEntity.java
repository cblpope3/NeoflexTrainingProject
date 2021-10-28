package ru.leonov.neotraining.entities;

import javax.persistence.*;

@Entity
@Table(name = "technical_maps")
public class TechMapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    private WorkerEntity worker;

    @OneToOne(fetch = FetchType.LAZY)
    private MaterialEntity material;

    public TechMapEntity() {
    }

    public TechMapEntity(WorkerEntity worker, MaterialEntity material) {
        this.worker = worker;
        this.material = material;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorkerEntity getWorker() {
        return worker;
    }

    public void setWorker(WorkerEntity worker) {
        this.worker = worker;
    }

    public MaterialEntity getMaterial() {
        return material;
    }

    public void setMaterial(MaterialEntity material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "TechMap{" +
                "worker=" + worker.toString() +
                ", material=" + material.toString() +
                '}';
    }
}
