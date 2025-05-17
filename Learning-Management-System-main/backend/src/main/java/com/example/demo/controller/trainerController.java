package com.example.demo.controller;

import com.example.demo.entity.trainer;
import com.example.demo.service.trainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/trainers")
public class trainerController {

    @Autowired
    private trainerService trainerService;

    @GetMapping
    public List<trainer> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    @GetMapping("/{id}")
    public trainer getTrainerById(@PathVariable Long id) {
        return trainerService.getTrainerById(id);
    }

    @PostMapping("/add")
    public trainer createTrainer(@RequestBody trainer trainer) {
        return trainerService.createTrainer(trainer);
    }

    @PutMapping("/{id}")
    public trainer updateTrainer(@PathVariable Long id, @RequestBody trainer updatedTrainer) {
        return trainerService.updateTrainer(id, updatedTrainer);
    }

    @DeleteMapping("/{id}")
    public void deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
    }

    @GetMapping("/details")
    public trainer getTrainerByEmail(@RequestParam String email) {
        return trainerService.getTrainerByEmail(email);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        trainer trainer = trainerService.authenticateTrainer(email, password);

        if (trainer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        String token = generateToken(trainer);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    private String generateToken(trainer trainer) {
        return "trainerId=" + trainer.getId() + ", email=" + trainer.getEmail();
    }
}
