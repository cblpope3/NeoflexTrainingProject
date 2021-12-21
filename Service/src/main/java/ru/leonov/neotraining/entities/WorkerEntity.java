package ru.leonov.neotraining.entities;

import javax.persistence.*;

@Entity
@Table(name = "workers")
public class WorkerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workers_seq_gen")
    @SequenceGenerator(name = "workers_seq_gen", sequenceName = "workers_id_sequence", allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    public WorkerEntity() {

    }

    public WorkerEntity(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public WorkerEntity(int id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Name: %s; Last name: %s", name, lastName);
    }
}
