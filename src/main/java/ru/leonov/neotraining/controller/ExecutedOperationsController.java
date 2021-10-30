package ru.leonov.neotraining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.entities.ExecutedOperationsEntity;
import ru.leonov.neotraining.services.ExecutedOperationsService;

@Controller
@RequestMapping(value = "operations")
public class ExecutedOperationsController {

    @Autowired
    private ExecutedOperationsService executedOpsService;

    //TODO this operation should get JSON object as input, not GET request with parameters
    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam int workerId, @RequestParam int materialId, @RequestParam int techMapId){
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
