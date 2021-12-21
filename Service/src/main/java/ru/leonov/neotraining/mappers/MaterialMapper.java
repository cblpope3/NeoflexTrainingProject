package ru.leonov.neotraining.mappers;

import org.mapstruct.Mapper;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.model.MaterialGeneratedDTO;
import ru.leonov.neotraining.model.MaterialPostGeneratedDTO;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    MaterialGeneratedDTO materialEntityToMaterialDto(MaterialEntity material);

    MaterialEntity materialPostDtoToMaterialEntity(MaterialPostGeneratedDTO userPostDto);

    Set<MaterialGeneratedDTO> materialsToMaterialsAllDto(Set<MaterialEntity> materials);
}
