package ru.leonov.neotraining.mappers;

import org.mapstruct.Mapper;
import ru.leonov.neotraining.dto.workers_dto.WorkerDTO;
import ru.leonov.neotraining.dto.workers_dto.WorkerPostDTO;
import ru.leonov.neotraining.entities.WorkerEntity;

@Mapper(componentModel = "spring")
public interface WorkersMapper {

    WorkerDTO workerEntityToWorkerDto(WorkerEntity worker);

    WorkerEntity workerPostDtoToWorkerEntity(WorkerPostDTO userPostDto);

    Iterable<WorkerDTO> workersToWorkersAllDto(Iterable<WorkerEntity> workers);

}