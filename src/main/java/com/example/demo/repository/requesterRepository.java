package com.example.demo.repository;

import com.example.demo.model.requester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface requesterRepository extends JpaRepository<requester,Long> {
    Optional<requester> findByUsernameEquals(String username);

    List<requester> findByIdNotAndIdNot(java.lang.Long id, java.lang.Long id1);



}
