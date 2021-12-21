package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.mappers.MaterialMapper;
import ru.leonov.neotraining.model.MaterialGeneratedDTO;
import ru.leonov.neotraining.model.MaterialPostGeneratedDTO;
import ru.leonov.neotraining.repositories.MaterialRepository;

import java.util.Set;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    private final MaterialMapper materialMapper;

    @Autowired
    public MaterialService(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
        this.materialRepository = materialRepository;
    }

    public boolean add(MaterialPostGeneratedDTO materialPostDTO) {
        MaterialEntity materialEntity = materialMapper.materialPostDtoToMaterialEntity(materialPostDTO);
        return (materialRepository.save(materialEntity)) == materialEntity;
    }

    public Set<MaterialGeneratedDTO> getAll() {
        return materialMapper.materialsToMaterialsAllDto(materialRepository.findAll());
    }

    public MaterialGeneratedDTO getById(int id) {
        if (materialRepository.existsById(id)) {
            return materialMapper.materialEntityToMaterialDto(materialRepository.findById(id));
        } else return null;
    }

    public boolean updateById(int id, String name) {
        if (materialRepository.existsById(id)) {
            MaterialEntity material = materialRepository.findById(id);
            if (name != null) material.setName(name);
            materialRepository.save(material);
            return true;
        } else return false;
    }

    public boolean deleteById(int id) {
        if (materialRepository.existsById(id)) {
            materialRepository.deleteById(id);
            return true;
        } else return false;
    }
}
