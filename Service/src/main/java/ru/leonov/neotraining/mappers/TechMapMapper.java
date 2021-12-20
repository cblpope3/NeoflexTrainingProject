package ru.leonov.neotraining.mappers;

import org.mapstruct.Mapper;
import ru.leonov.neotraining.dto.tech_map_dto.TechMapDTO;
import ru.leonov.neotraining.entities.TechMapEntity;

@Mapper(componentModel = "spring")
public interface TechMapMapper {

    TechMapDTO techMapEntityToTechMapDto(TechMapEntity techMap);

    Iterable<TechMapDTO> techMapsToTechMapsAllDto(Iterable<TechMapEntity> techMaps);
}
