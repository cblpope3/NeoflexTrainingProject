package ru.leonov.neotraining.entities;

import javax.persistence.*;

@Entity
@Table(name = "materials")
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materials_seq_gen")
    @SequenceGenerator(name = "materials_seq_gen", sequenceName = "materials_id_sequence", allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String name;

    public MaterialEntity() {
    }

    public MaterialEntity(String name) {
        this.name = name;
    }

    public MaterialEntity(int id, String name) {
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

    @Override
    public String toString() {
        return String.format("Material: %s", name);
    }
}
