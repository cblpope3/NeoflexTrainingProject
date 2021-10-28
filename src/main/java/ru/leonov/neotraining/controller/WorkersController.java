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

    @GetMapping("/get")
    @ResponseBody
    public Iterable<WorkerEntity> getAllWorkers(){
        return workersService.getAllWorkers();
    }
    @GetMapping("/get/{id}")
    @ResponseBody
    public WorkerEntity getWorkerById(@PathVariable int id){
        WorkerEntity worker = new WorkerEntity();
        //TODO complete method after implementation corresponding service
        return worker;
    }

    @GetMapping("/delete")
    @ResponseBody
    public String deleteWorkerById(@RequestParam int id){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }

    @GetMapping("/update")
    @ResponseBody
    public String updateWorkerById(@RequestParam int id, @RequestParam String name, @RequestParam String lastName){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }

}
