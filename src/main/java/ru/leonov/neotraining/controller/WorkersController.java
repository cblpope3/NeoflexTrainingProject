package ru.leonov.neotraining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.services.WorkersService;

@Controller
@RequestMapping(value = "/worker")
public class WorkersController {

    @Autowired
    private WorkersService workersService;

    @GetMapping("/add")
    @ResponseBody
    public String addWorker(@RequestParam String name, @RequestParam String lastName){
        workersService.addWorker(name, lastName);
        return String.format("Worker %s %s added successfully!", name, lastName);
    }

    @GetMapping("")
    @ResponseBody
    public Iterable<WorkerEntity> getAllWorkers(){
        return workersService.getAllWorkers();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public WorkerEntity getWorkerById(@PathVariable int id){
        return workersService.getById(id);
    }

    @GetMapping("/{id}/update")
    @ResponseBody
    public String updateWorkerById(@PathVariable int id, @RequestParam String name, @RequestParam String lastName){
        if (workersService.updateById(id, name, lastName)) return "updated successfully!";
        else return String.format("worker #%s not found", id);
    }

    @GetMapping("/{id}/delete")
    @ResponseBody
    public String deleteWorkerById(@PathVariable int id){
        if (workersService.deleteById(id)) return "deleted successfully!";
        else return String.format("Worker #%s not found.", id);
    }
}
