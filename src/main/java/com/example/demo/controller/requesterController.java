package com.example.demo.controller;

import com.example.demo.model.requester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.requesterService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
public class requesterController {

    @Autowired
    private requesterService requesterService;




    @PostMapping("/requester")
    public ResponseEntity<?> verifyRequester(@Valid @RequestBody
                                             requester verify_requester) {
        return requesterService.verify(verify_requester);
    }

    @PostMapping("/designated_officer")
    public ResponseEntity<?> verifyDO(@Valid @RequestBody
                                             requester verify_requester) {
        return requesterService.verify_DO(verify_requester);
    }
}
