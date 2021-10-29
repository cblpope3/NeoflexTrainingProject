package ru.leonov.neotraining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.entities.TechMapEntity;
import ru.leonov.neotraining.services.TechMapService;

@Controller
@RequestMapping(value = "tech_map")
public class TechMapController {

    @Autowired
    private TechMapService techMapService;

    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam int workerId, @RequestParam int materialId){
        TechMapEntity addedTechMap = techMapService.add(workerId, materialId);
        return String.format("Technical map '%s' added successfully!", addedTechMap.toString());
    }

    @GetMapping("")
    @ResponseBody
    public Iterable<TechMapEntity> getAll(){
        return techMapService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public TechMapEntity getById(@PathVariable int id){
        return techMapService.getById(id);
    }

    @GetMapping("/{id}/update")
    @ResponseBody
    public String updateById(@PathVariable int id, @RequestParam int workerId, @RequestParam int materialId){
        if (techMapService.updateById(id, workerId, materialId)) return "updated successfully!";
        else return String.format("technical map '#%s' not found", id);
    }

    @GetMapping("/{id}/delete")
    @ResponseBody
    public String deleteById(@PathVariable int id){
        if (techMapService.deleteById(id)) return "deleted successfully!";
        else return String.format("technical map '#%s' not found.", id);
    }
}
