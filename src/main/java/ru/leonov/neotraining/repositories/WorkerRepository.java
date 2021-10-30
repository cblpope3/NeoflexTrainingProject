package ru.leonov.neotraining.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;
import ru.leonov.neotraining.entities.WorkerEntity;

@Repository
public interface WorkerRepository extends CrudRepository<WorkerEntity, Integer> {
    WorkerEntity findById(int id);
}
