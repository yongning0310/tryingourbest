package com.example.demo.service;

import com.example.demo.model.requester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class requesterService {

    @Autowired
    private com.example.demo.repository.requesterRepository requesterRepository;

    public ResponseEntity<?> verify(requester verify_requester){
        Optional <requester> found_requester = requesterRepository
                .findByUsernameEquals(verify_requester.getUsername());
        if (found_requester!=null) {
            if (Objects.equals(found_requester.get().getPassword(), verify_requester.getPassword())) {
                return new ResponseEntity<>(found_requester.get().getId(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> verify_DO(requester verify_requester){
        Optional <requester> found_requester = requesterRepository
                .findByUsernameEquals(verify_requester.getUsername());
        if (found_requester!=null) {
            if (Objects.equals(found_requester.get().getPassword(), verify_requester.getPassword())) {
                if (found_requester.get().getDesignated_officer()){
                    return new ResponseEntity<>(found_requester.get().getId(), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
