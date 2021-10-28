package ru.leonov.neotraining.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.leonov.neotraining.entities.TechMapEntity;

@Repository
public interface TechMapRepository extends CrudRepository<TechMapEntity, Integer> {

}
