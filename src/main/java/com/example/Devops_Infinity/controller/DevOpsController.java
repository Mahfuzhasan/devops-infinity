package com.example.Devops_Infinity.controller;

import com.example.Devops_Infinity.model.DevOpsEngineer;
import com.example.Devops_Infinity.repository.DevOpsEngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/devops")
public class DevOpsController {

    @Autowired
    private DevOpsEngineerRepository repository;

    private List<String> names = Arrays.asList("Adam", "Mahfuz", "Ella", "Oliver", "Motu", "Lucas", "Sophia", "Liam", "Emma", "Ethan");
    private List<String> locations = Arrays.asList("Toronto", "Vancouver", "Ottawa", "Calgary", "Montreal", "Edmonton", "Winnipeg", "Quebec City", "Halifax", "Victoria");
    private Random random = new Random();

    @GetMapping("/engineers")
    public List<DevOpsEngineer> getAllEngineers() {
        return repository.findAll();
    }

    @GetMapping("/addRandomEngineer")
    public String addRandomEngineer() {
        DevOpsEngineer engineer = new DevOpsEngineer();
        engineer.setName(names.get(random.nextInt(names.size())));
        engineer.setLocation(locations.get(random.nextInt(locations.size())));
        repository.save(engineer);
        return engineer.getName() + " from " + engineer.getLocation() + " has been added to the DB!";
    }
}
