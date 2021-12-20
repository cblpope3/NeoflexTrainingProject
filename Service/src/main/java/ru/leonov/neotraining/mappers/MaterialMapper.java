package ru.leonov.neotraining.mappers;

import org.mapstruct.Mapper;
import ru.leonov.neotraining.dto.material_dto.MaterialDTO;
import ru.leonov.neotraining.dto.material_dto.MaterialPostDTO;
import ru.leonov.neotraining.entities.MaterialEntity;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    MaterialDTO materialEntityToMaterialDto(MaterialEntity material);

    MaterialEntity materialPostDtoToMaterialEntity(MaterialPostDTO userPostDto);

    Iterable<MaterialDTO> materialsToMaterialsAllDto(Iterable<MaterialEntity> materials);
}
