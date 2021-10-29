package ru.leonov.neotraining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leonov.neotraining.entities.MaterialEntity;
import ru.leonov.neotraining.services.MaterialService;

@Controller
@RequestMapping(value = "/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam String name){
        materialService.add(name);
        return String.format("Material %s added successfully!", name);
    }

    @GetMapping("")
    @ResponseBody
    public Iterable<MaterialEntity> getAll(){
        return materialService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public MaterialEntity getById(@PathVariable int id){
        return materialService.getById(id);
    }

    @GetMapping("/{id}/update")
    @ResponseBody
    public String updateById(@PathVariable int id, @RequestParam String name){
        if (materialService.updateById(id, name)) return "updated successfully!";
        else return String.format("material #%s not found", id);
    }

    @GetMapping("/{id}/delete")
    @ResponseBody
    public String deleteById(@PathVariable int id){
        if (materialService.deleteById(id)) return "deleted successfully!";
        else return String.format("material #%s not found.", id);
    }
}
