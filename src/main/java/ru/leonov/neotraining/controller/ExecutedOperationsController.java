package ru.leonov.neotraining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.data_containers.ExecutedOperationsJSONContainer;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;
import ru.leonov.neotraining.services.ExecutedOperationsService;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(value = "operations")
public class ExecutedOperationsController {

    @Autowired
    private ExecutedOperationsService executedOpsService;

    //TODO decide if this function has to be removed
    @GetMapping("/add")
    @ResponseBody
    public String addOld(@RequestParam int workerId, @RequestParam int materialId, @RequestParam int techMapId){
        if (executedOpsService.executeOperation(workerId, materialId, techMapId)) {
            //if operation executed successfully
            return String.format("Operation with worker '#%d', material '#%d' and techMap '#%d' was executed successfully!",
                    workerId, materialId, techMapId);
        } else return "operation execution failed!";
    }

    @PostMapping("/add")
    @ResponseBody
    public String add(@RequestBody ExecutedOperationsJSONContainer request){
        int workerId = request.getWorkerId();
        int materialId = request.getMaterialId();
        int techMapId = request.getTechMapId();
        if (executedOpsService.executeOperation(workerId, materialId, techMapId)) {
            //if operation executed successfully
            return String.format("Operation with worker '#%d', material '#%d' and techMap '#%d' was executed successfully!",
                    workerId, materialId, techMapId);
        } else return "operation execution failed!";
    }

    @GetMapping("")
    @ResponseBody
    public Iterable<ExecutedOperationsEntity> getAll(){
        return executedOpsService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ExecutedOperationsEntity getById(@PathVariable int id){
        return executedOpsService.getById(id);
    }

    @GetMapping("/{id}/delete")
    @ResponseBody
    public String deleteById(@PathVariable int id){
        if (executedOpsService.deleteById(id)) return "deleted successfully!";
        else return String.format("operation '#%s' not found.", id);
    }
}
