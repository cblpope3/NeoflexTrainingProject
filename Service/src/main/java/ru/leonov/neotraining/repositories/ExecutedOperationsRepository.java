package ru.leonov.neotraining.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface ExecutedOperationsRepository extends CrudRepository<ExecutedOperationsEntity, Integer> {
    ExecutedOperationsEntity findById(int id);

    List<ExecutedOperationsEntity> findAllByOrderByIdDesc();

    Set<ExecutedOperationsEntity> findAll();
}
