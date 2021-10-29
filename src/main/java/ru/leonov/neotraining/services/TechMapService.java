package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.repositories.MaterialRepository;
import ru.leonov.neotraining.repositories.TechMapRepository;
import ru.leonov.neotraining.repositories.WorkerRepository;

@Service
public class TechMapService {

    @Autowired
    private TechMapRepository techMapRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public TechMapEntity add(int workerId, int materialId){
        //FIXME get rid of .get() methods
        TechMapEntity techMap = new TechMapEntity(workerRepository.findById(workerId).get(),
                materialRepository.findById(materialId).get());
        return techMapRepository.save(techMap);
    }

    public Iterable<TechMapEntity> getAll(){
        return techMapRepository.findAll();
    }

    public TechMapEntity getById(int id){
        if (techMapRepository.existsById(id)) return techMapRepository.findById(id);
        else return null;
    }

    public boolean updateById(int id, int workerId, int materialId){
        //FIXME get rid of .get() methods
        if (techMapRepository.existsById(id)){
            TechMapEntity techMap = techMapRepository.findById(id);
            techMap.setWorker(workerRepository.findById(workerId).get());
            techMap.setMaterial(materialRepository.findById(materialId).get());
            techMapRepository.save(techMap);
            return true;
        } else return false;
    }

    public boolean deleteById(int id){
        if (techMapRepository.existsById(id)){
            techMapRepository.deleteById(id);
            return true;
        } else return false;
    }
}
