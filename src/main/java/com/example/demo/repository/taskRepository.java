package com.example.demo.repository;

import com.example.demo.model.task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface taskRepository extends JpaRepository<task,Long> {
    List<task> findByAuthorisedBy_IdEqualsAndAuthorisedFalse(Long id);

    List<task> findByCreatedBy_IdNotAndValidatedFalse(Long id);


    List<task> findByCreatedBy_IdEqualsAndVerifiedFalse(Long id);

    List<task> findByVerifiedFalseAndAuthorisedTrue();



}
