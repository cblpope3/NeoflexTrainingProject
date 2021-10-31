package ru.leonov.neotraining.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.entities.WorkerEntity;
import ru.leonov.neotraining.services.TechMapService;

@Controller
@RequestMapping(value = "tech_map")
@Api
public class TechMapController {

    @Autowired
    private TechMapService techMapService;

    @ApiOperation(value = "Add a new technical map.")
    @GetMapping("/add")
    @ResponseBody
    public String add(@ApiParam(value = "Id of worker associated with technical map.", required = true)
                          @RequestParam int workerId,
                      @ApiParam(value = "Id of material associated with technical map.", required = true)
                      @RequestParam int materialId){
        TechMapEntity addedTechMap = techMapService.add(workerId, materialId);
        return String.format("Technical map '%s' added successfully!", addedTechMap.toString());
    }

    @ApiOperation(value = "Get list of all technical maps.")
    @ApiResponses(value = {@ApiResponse(
            code = 200,
            message = "Returns list of all technical maps as JSON object.",
            response = TechMapEntity[].class)})
    @GetMapping("")
    @ResponseBody
    public Iterable<TechMapEntity> getAll(){
        return techMapService.getAll();
    }

    @ApiOperation(value = "Get specific technical map with id={id}.")
    @ApiResponses(value = {@ApiResponse(
            code = 200,
            message = "Returns requested technical map as JSON object.",
            response = TechMapEntity.class)})
    @GetMapping("/{id}")
    @ResponseBody
    public TechMapEntity getById(@ApiParam(value = "Id of requested technical map.", required = true)
                                     @PathVariable int id){
        return techMapService.getById(id);
    }

    @ApiOperation(value = "Change existing technical map with id={id}.")
    @GetMapping("/{id}/update")
    @ResponseBody
    public String updateById(@ApiParam(value = "Id of requested technical map.", required = true)
                                 @PathVariable int id,
                             @ApiParam(value = "New id of worker associated with technical map.", required = true)
                                 @RequestParam int workerId,
                             @ApiParam(value = "New id of material associated with technical map.", required = true)
                                 @RequestParam int materialId){
        if (techMapService.updateById(id, workerId, materialId)) return "updated successfully!";
        else return String.format("technical map '#%s' not found", id);
    }

    @ApiOperation(value = "Delete technical map with id={id}.")
    @GetMapping("/{id}/delete")
    @ResponseBody
    public String deleteById(@ApiParam(value = "Id of requested technical map.", required = true)  @PathVariable int id){
        if (techMapService.deleteById(id)) return "deleted successfully!";
        else return String.format("technical map '#%s' not found.", id);
    }
}
