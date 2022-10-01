package com.example.demo.service;

import com.example.demo.model.aetos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;

@Service
public class aetosService {
    @Autowired
    private com.example.demo.repository.aetosRepository aetosRepository;

    public ResponseEntity<?> verify(aetos verify_aetos) {
        Optional<aetos> found_aetos = aetosRepository
                .findByUsernameEquals(verify_aetos.getUsername());
        if (!found_aetos.isEmpty()) {
            if (Objects.equals(found_aetos.get().getPassword(), verify_aetos.getPassword())) {
                return new ResponseEntity<>(found_aetos.get().getId(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}