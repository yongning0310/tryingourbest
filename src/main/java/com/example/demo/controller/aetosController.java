package com.example.demo.controller;

import com.example.demo.model.aetos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.aetosService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class aetosController {

    @Autowired
    private aetosService aetosService;

    @PostMapping("aetos")
    public ResponseEntity<?> verifyRequester(@Valid @RequestBody
                                             aetos verify_aetos){
        return aetosService.verify(verify_aetos);
    }
}