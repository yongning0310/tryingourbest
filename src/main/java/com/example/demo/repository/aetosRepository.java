package com.example.demo.repository;

import com.example.demo.model.aetos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface aetosRepository  extends JpaRepository <aetos,Long> {
    Optional<aetos> findByUsernameEquals(String username);

}
