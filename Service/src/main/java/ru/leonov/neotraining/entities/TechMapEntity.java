package ru.leonov.neotraining.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "technical_maps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechMapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tech_map_seq_gen")
    @SequenceGenerator(name = "tech_map_seq_gen", sequenceName = "technical_maps_id_sequence", allocationSize = 1)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "worker_id")
    private WorkerEntity worker;

    @ManyToOne(optional = false)
    @JoinColumn(name = "material_id")
    private MaterialEntity material;

    public TechMapEntity(WorkerEntity worker, MaterialEntity material) {
        this.worker = worker;
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
