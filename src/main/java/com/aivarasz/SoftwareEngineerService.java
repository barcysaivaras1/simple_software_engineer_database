package com.aivarasz;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoftwareEngineerService {
    private SoftwareEngineerRepository softwareEngineerRepository;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }


    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return softwareEngineerRepository.findAll();
    }

    public SoftwareEngineer getSoftwareEngineerById(Integer id) {
        return softwareEngineerRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + "not found"));
    }

    public List<SoftwareEngineer> getSoftwareEngineerByName(String name) {
        return softwareEngineerRepository.findAll().stream().filter(softwareEngineer -> softwareEngineer.getName().equals(name)).collect(Collectors.toList());
    }

    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        softwareEngineerRepository.save(softwareEngineer);
    }

    public void deleteSoftwareEngineer(Integer id) {
        boolean exists = softwareEngineerRepository.existsById(id);
        if(exists) {
            softwareEngineerRepository.deleteById(id);
        }
        else{
            throw new IllegalStateException("Software Engineer with id " + id + " not found");
        }
    }

    public void updateSoftwareEngineer(int id, SoftwareEngineer softwareEngineer) {
        softwareEngineer.setId(id);
        boolean exists = softwareEngineerRepository.existsById(id);
        if(exists) {
            softwareEngineerRepository.save(softwareEngineer);
        }
        else{
            throw new IllegalStateException("Software Engineer with id " + id + " not found");
        }
    }
}
