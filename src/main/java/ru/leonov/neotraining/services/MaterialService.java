package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.dto.material_dto.MaterialDTO;
import ru.leonov.neotraining.dto.material_dto.MaterialPostDTO;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.mappers.MaterialMapper;
import ru.leonov.neotraining.repositories.MaterialRepository;

@Service
public class MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialRepository materialRepository;

    public boolean add(MaterialPostDTO materialPostDTO) {
        MaterialEntity materialEntity = materialMapper.materialPostDtoToMaterialEntity(materialPostDTO);
        return (materialRepository.save(materialEntity)) == materialEntity;
    }

    public Iterable<MaterialDTO> getAll() {
        return materialMapper.materialsToMaterialsAllDto(materialRepository.findAll());
    }

    public MaterialDTO getById(int id) {
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
