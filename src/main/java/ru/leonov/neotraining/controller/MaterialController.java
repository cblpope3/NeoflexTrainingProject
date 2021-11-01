package ru.leonov.neotraining.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.services.MaterialService;

@Controller
@RequestMapping(value = "/material")
@Api
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @ApiOperation(value = "Add a new material.")
    @PostMapping("")
    @ResponseBody
    public String add(@ApiParam(value = "Name of material.", required = true) @RequestParam String name) {
        materialService.add(name);
        return String.format("Material %s added successfully!", name);
    }

    @ApiOperation(value = "Get list of all materials.")
    @ApiResponses(value = {@ApiResponse(
            code = 200,
            message = "Returns list of all materials as JSON object.",
            response = MaterialEntity[].class)})
    @GetMapping("")
    @ResponseBody
    public Iterable<MaterialEntity> getAll() {
        return materialService.getAll();
    }

    @ApiOperation(value = "Get specific material with id={id}.")
    @ApiResponses(value = {@ApiResponse(
            code = 200,
            message = "Returns requested material as JSON object.",
            response = MaterialEntity.class)})
    @GetMapping("/{id}")
    @ResponseBody
    public MaterialEntity getById(@ApiParam(value = "Id of requested material.", required = true) @PathVariable int id) {
        return materialService.getById(id);
    }

    @ApiOperation(value = "Change name of material with id={id}.")
    @PutMapping("/{id}")
    @ResponseBody
    public String updateById(@ApiParam(value = "Id of requested material.", required = true) @PathVariable int id,
                             @ApiParam(value = "New name of material.", required = true) @RequestParam String name) {
        if (materialService.updateById(id, name)) return "updated successfully!";
        else return String.format("material #%s not found", id);
    }

    @ApiOperation(value = "Delete material with id={id}.")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteById(@ApiParam(value = "Id of requested material.", required = true) @PathVariable int id) {
        if (materialService.deleteById(id)) return "deleted successfully!";
        else return String.format("material #%s not found.", id);
    }
}
