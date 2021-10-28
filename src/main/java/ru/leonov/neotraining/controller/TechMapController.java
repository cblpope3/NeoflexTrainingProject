package ru.leonov.neotraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "tech_map")
public class TechMapController {

    @GetMapping("/add")
    @ResponseBody
    public String addTechMap(){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }

    @GetMapping("/get")
    @ResponseBody
    public String getAllTechMaps(){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public String getTechMapById(){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }

    @GetMapping("/delete")
    @ResponseBody
    public String deleteTechMapById(){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }

    @GetMapping("/update")
    @ResponseBody
    public String updateTechMapById(){
        //TODO complete method after implementation corresponding service
        return "Not implemented!";
    }
}
