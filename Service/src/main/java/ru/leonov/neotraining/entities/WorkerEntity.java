package ru.leonov.neotraining.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "workers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workers_seq_gen")
    @SequenceGenerator(name = "workers_seq_gen", sequenceName = "workers_id_sequence", allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    public WorkerEntity(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Name: %s; Last name: %s", name, lastName);
    }
}
