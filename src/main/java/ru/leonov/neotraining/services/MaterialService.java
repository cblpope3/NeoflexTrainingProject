package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.repositories.MaterialRepository;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public boolean add(String name) {
        MaterialEntity material = new MaterialEntity(name);
        return (materialRepository.save(material)) == material;
    }

    public Iterable<MaterialEntity> getAll() {
        return materialRepository.findAll();
    }

    public MaterialEntity getById(int id) {
        if (materialRepository.existsById(id)) return materialRepository.findById(id);
        else return null;
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
