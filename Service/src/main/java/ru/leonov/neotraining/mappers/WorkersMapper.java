package ru.leonov.neotraining.mappers;

import org.mapstruct.Mapper;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.model.WorkerGeneratedDTO;
import ru.leonov.neotraining.model.WorkerPostGeneratedDTO;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface WorkersMapper {

    WorkerGeneratedDTO workerEntityToWorkerDto(WorkerEntity worker);

    WorkerEntity workerPostDtoToWorkerEntity(WorkerPostGeneratedDTO userPostDto);

    Set<WorkerGeneratedDTO> workersToWorkersAllDto(Set<WorkerEntity> workers);

}