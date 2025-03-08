package com.aivarasz;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/software-engineer")
public class SoftwareEngineerController {


    private final SoftwareEngineerService softwareEngineerService;

    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    @GetMapping
    public List<SoftwareEngineer> getSoftwareEngineers(){
        return softwareEngineerService.getAllSoftwareEngineers();
    }

    @PostMapping
    public void addNewSoftwareEngineer(@RequestBody SoftwareEngineer softwareEngineer) {
        softwareEngineerService.insertSoftwareEngineer(softwareEngineer);
    }

    @GetMapping("name/{name}")
    public List<SoftwareEngineer> getSoftwareEngineersByName(@PathVariable String name) {
        return softwareEngineerService.getSoftwareEngineerByName(name);
    }

    @GetMapping("{id}")
    public SoftwareEngineer getSoftwareEngineerById(@PathVariable int id) {
        return softwareEngineerService.getSoftwareEngineerById(id);
    }


    @DeleteMapping("{id}")
    public void deleteSoftwareEngineerById(@PathVariable int id) {
        softwareEngineerService.deleteSoftwareEngineer(id);
    }

    @PutMapping("{id}")
    public void updateSoftwareEngineerById(@PathVariable int id, @RequestBody SoftwareEngineer softwareEngineer) {
        softwareEngineerService.updateSoftwareEngineer(id, softwareEngineer);
    }

}
