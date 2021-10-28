package ru.leonov.neotraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/material")
public class MaterialController {

    @GetMapping("/add")
    @ResponseBody
    public String addMaterial(){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }

    @GetMapping("/get")
    @ResponseBody
    public String getAllMaterials(){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public String getMaterialById(){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }

    @GetMapping("/delete")
    @ResponseBody
    public String deleteMaterialById(){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }

    @GetMapping("/update")
    @ResponseBody
    public String updateMaterialById(){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }
}
