package ru.leonov.neotraining.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.leonov.neotraining.entities.MaterialEntity;

import java.util.Set;

@Repository
public interface MaterialRepository extends CrudRepository<MaterialEntity, Integer> {
    MaterialEntity findById(int id);

    Set<MaterialEntity> findAll();
}
