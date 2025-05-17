package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.trainer;
import com.example.demo.repository.trainerRepository;
import java.util.List;

@Service
public class trainerService {

    @Autowired
    private trainerRepository trainerRepository;

    public List<trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public trainer getTrainerById(Long id) {
        return trainerRepository.findById(id).orElse(null);
    }

    public trainer createTrainer(trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public trainer updateTrainer(Long id, trainer updatedTrainer) {
        trainer existingTrainer = trainerRepository.findById(id).orElse(null);
        if (existingTrainer != null) {
            existingTrainer.setUsername(updatedTrainer.getUsername());
            existingTrainer.setEmail(updatedTrainer.getEmail());
            // add other fields to update
            return trainerRepository.save(existingTrainer);
        }
        return null;
    }

    public trainer getTrainerByEmail(String email) {
        return trainerRepository.findByEmail(email);
    }

//    public trainer authenticateTrainer(String email, String password) {
//        return trainerRepository.findByEmailAndPassword(email, password);
//    }
public trainer authenticateTrainer(String email, String password) {
    trainer t = trainerRepository.findByEmail(email);
    if (t != null && t.getPassword().equals(password)) {
        return t;
    }
    return null;
}


    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }
}
