package ru.leonov.neotraining.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.services.WorkersService;

@Controller
@RequestMapping(value = "/worker")
@Api
public class WorkersController {

    @Autowired
    private WorkersService workersService;

    @ApiOperation(value = "Add a new worker.")
    @GetMapping("/add")
    @ResponseBody
    public String addWorker(@ApiParam(value = "Name of worker.", required = true) @RequestParam String name,
                            @ApiParam(value = "Last name of worker.", required = true) @RequestParam String lastName){
        workersService.addWorker(name, lastName);
        return String.format("Worker %s %s added successfully!", name, lastName);
    }

    @ApiOperation(value = "Get list of all workers.")
    @ApiResponses(value = {@ApiResponse(
            code = 200,
            message = "Returns list of all workers as JSON object.",
            response = WorkerEntity[].class)})
    @GetMapping("")
    @ResponseBody
    public Iterable<WorkerEntity> getAllWorkers(){
        return workersService.getAllWorkers();
    }

    @ApiOperation(value = "Get specific worker with id={id}.")
    @ApiResponses(value = {@ApiResponse(
            code = 200,
            message = "Returns requested worker as JSON object.",
            response = WorkerEntity.class)})
    @GetMapping("/{id}")
    @ResponseBody
    public WorkerEntity getWorkerById(@ApiParam(value = "Id of requested worker.", required = true)
                                          @PathVariable int id){
        return workersService.getById(id);
    }

    @ApiOperation(value = "Change name and last name of worker with id={id}.")
    @GetMapping("/{id}/update")
    @ResponseBody
    public String updateWorkerById(@ApiParam(value = "Id of requested worker.", required = true)
                                       @PathVariable int id,
                                   @ApiParam(value = "New name of worker.", required = true)
                                        @RequestParam String name,
                                   @ApiParam(value = "New last name of worker.", required = true)
                                       @RequestParam String lastName){
        if (workersService.updateById(id, name, lastName)) return "updated successfully!";
        else return String.format("worker #%s not found", id);
    }

    @ApiOperation(value = "Delete worker with id={id}.")
    @GetMapping("/{id}/delete")
    @ResponseBody
    public String deleteWorkerById(@ApiParam(value = "Id of requested worker.", required = true) @PathVariable int id){
        if (workersService.deleteById(id)) return "deleted successfully!";
        else return String.format("Worker #%s not found.", id);
    }
}
