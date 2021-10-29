package ru.leonov.neotraining.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.repositories.WorkerRepository;

@Service
public class WorkersService {

    @Autowired
    private WorkerRepository workerRepository;

    public void addWorker(String name, String lastName){
        WorkerEntity worker = new WorkerEntity(name, lastName);
        workerRepository.save(worker);
    }

    public Iterable<WorkerEntity> getAllWorkers(){
        return workerRepository.findAll();
    }

    public WorkerEntity getById(int id){
        if (workerRepository.existsById(id)) return workerRepository.findById(id).get();
        else return null;
    }

    public boolean updateById(int id, String name, String lastName){
        if (workerRepository.existsById(id)){
            WorkerEntity worker = workerRepository.findById(id).get();
            worker.setName(name);
            worker.setLastName(lastName);
            workerRepository.save(worker);
            return true;
        } else return false;
    }

    public boolean deleteById(int id){
        if (workerRepository.existsById(id)){
            workerRepository.deleteById(id);
            return true;
        } else return false;
    }
}
