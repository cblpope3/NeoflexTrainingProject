package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.repositories.ExecutedOperationsRepository;
import ru.leonov.neotraining.repositories.MaterialRepository;
import ru.leonov.neotraining.repositories.TechMapRepository;
import ru.leonov.neotraining.repositories.WorkerRepository;

@Service
public class ExecutedOperationsService {

    @Autowired
    private ExecutedOperationsRepository executedOperationsRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private TechMapRepository techMapRepository;

    public boolean executeOperation(int workerId, int materialId, int techMapId){
        boolean isWorkerExist = workerRepository.existsById(workerId);
        boolean isMaterialExist = materialRepository.existsById(materialId);
        boolean isTechMapExist = techMapRepository.existsById(techMapId);

        //check if worker, material and technical map exist in database
        if (isWorkerExist & isMaterialExist & isTechMapExist){

            TechMapEntity executingTechMap = techMapRepository.findById(techMapId);

            boolean isWorkerMatchTechMap = executingTechMap.getWorker().getId() == workerId;
            boolean isMaterialMatchTechMap = executingTechMap.getMaterial().getId() == materialId;

            //check if worker and material are in current technical map
            if (isWorkerMatchTechMap & isMaterialMatchTechMap){

                ExecutedOperationsEntity operation = new ExecutedOperationsEntity(executingTechMap);
                executedOperationsRepository.save(operation);
                return true;
            }
        }
        //if didn't return before this line, check failed
        return false;
    }

    public Iterable<ExecutedOperationsEntity> getAll(){
        return executedOperationsRepository.findAll();
    }

    public ExecutedOperationsEntity getById(int id){
        if (executedOperationsRepository.existsById(id)) return executedOperationsRepository.findById(id);
        else return null;
    }

    public boolean deleteById(int id){
        if (executedOperationsRepository.existsById(id)){
            executedOperationsRepository.deleteById(id);
            return true;
        } else return false;
    }
}
