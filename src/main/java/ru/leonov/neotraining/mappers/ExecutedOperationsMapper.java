package ru.leonov.neotraining.mappers;


import org.mapstruct.Mapper;
import ru.leonov.neotraining.dto.executed_operations_dto.ExecutedOperationsDTO;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;

@Mapper(componentModel = "spring")
public interface ExecutedOperationsMapper {

    ExecutedOperationsDTO executedOpsEntityToExecutedOpsDto(ExecutedOperationsEntity executedOperation);

    Iterable<ExecutedOperationsDTO> executedOpsEntityToExecutedOpsDtoAll(Iterable<ExecutedOperationsEntity> techMaps);
}
