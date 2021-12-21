package ru.leonov.neotraining.mappers;

import org.mapstruct.Mapper;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.model.TechMapGeneratedDTO;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TechMapMapper {

    TechMapGeneratedDTO techMapEntityToTechMapDto(TechMapEntity techMap);

    Set<TechMapGeneratedDTO> techMapsToTechMapsAllDto(Set<TechMapEntity> techMaps);
}
