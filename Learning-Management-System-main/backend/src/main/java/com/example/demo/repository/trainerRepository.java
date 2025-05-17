package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.trainer;

public interface trainerRepository extends JpaRepository<trainer, Long> {
    trainer findByEmail(String email);
    trainer findByEmailAndPassword(String email, String password);
}
