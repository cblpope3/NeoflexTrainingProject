package ru.leonov.neotraining.mappers;

import org.mapstruct.Mapper;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;
import ru.leonov.neotraining.model.ExecutedOperationGeneratedDTO;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ExecutedOperationsMapper {

    ExecutedOperationGeneratedDTO executedOpsEntityToExecutedOpsDto(ExecutedOperationsEntity executedOperation);

    Set<ExecutedOperationGeneratedDTO> executedOpsEntityToExecutedOpsDtoAll(Set<ExecutedOperationsEntity> techMaps);

    List<ExecutedOperationGeneratedDTO> executedOpsEntityToExecutedOpsDtoAllOrdered(List<ExecutedOperationsEntity> techMaps);
}
