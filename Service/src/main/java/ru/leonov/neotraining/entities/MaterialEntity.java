package ru.leonov.neotraining.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "materials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materials_seq_gen")
    @SequenceGenerator(name = "materials_seq_gen", sequenceName = "materials_id_sequence", allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String name;

    public MaterialEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Material: %s", name);
    }
}
