package ru.leonov.neotraining.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;
import ru.leonov.neotraining.entities.TechMapEntity;

@Repository
public interface ExecutedOperationsRepository extends CrudRepository<ExecutedOperationsEntity, Integer> {
    ExecutedOperationsEntity findById(int id);

    Iterable<ExecutedOperationsEntity> findAllByOrderByIdDesc();
}
