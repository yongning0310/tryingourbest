package com.example.demo.service;

import com.example.demo.model.aetos;
import com.example.demo.model.requester;
import com.example.demo.model.task;
import com.example.demo.repository.aetosRepository;
import com.example.demo.repository.requesterRepository;
import com.example.demo.repository.taskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class taskService {

    @Autowired
    private taskRepository taskRepository;

    @Autowired
    private requesterRepository requesterRepository;

    @Autowired
    private aetosRepository aetosRepository;

    public ResponseEntity<?> All(){
        return new ResponseEntity<>(taskRepository
                .findAll(), HttpStatus.OK);

    }

    public ResponseEntity<?> getAllTasks(Long requester_id){
        return new ResponseEntity<>(taskRepository
                .findByAuthorisedBy_IdEqualsAndAuthorisedFalse(requester_id), HttpStatus.OK);

    }

    public ResponseEntity<?> authoriseRequest(Long task_id){
        Optional<task> task_found = taskRepository.findById(task_id);
        if (task_found!=null){
            task task_to_update = task_found.get();
            task_to_update.setAuthorised(true);
            taskRepository.save(task_to_update);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getAllpendingRequest(Long requester_id){
        return new ResponseEntity<>(taskRepository
                .findByCreatedBy_IdEqualsOrValidatedFalseOrAuthorisedFalseOrVerifiedFalse
                        (requester_id), HttpStatus.OK);

    }

    public ResponseEntity<?> createTask(task createTask, Long requester_id){
        task task_to_save = new task();
        task_to_save.setCompany_name(createTask.getCompany_name());
        task_to_save.setVehicle_no(createTask.getVehicle_no());
        task_to_save.setDriver_name(createTask.getDriver_name());
        task_to_save.setDriver_psa_pass_no(createTask.getDriver_psa_pass_no());
        task_to_save.setDescription(createTask.getDescription());
        task_to_save.setVerified(false);
        task_to_save.setValidated(false);
        task_to_save.setAuthorised(false);
        task_to_save.setAttachments("insert urls");
        task_to_save.setCreatedBy(requesterRepository.findById(requester_id).get());
        return new ResponseEntity<>(taskRepository.save(task_to_save), HttpStatus.OK);
    }

    public ResponseEntity<?> unvalidatedRequest(Long requester_id){
        return new ResponseEntity<>(taskRepository.getByCreatedBy_IdLessThanOrCreatedBy_IdGreaterThanAndValidatedFalse(requester_id,requester_id), HttpStatus.OK);

    }

    public ResponseEntity<?> validateRequest(Long task_id, Long cso_id, Long do_id ){
        Optional<task> task_found = taskRepository.findById(task_id);
        Optional<requester> cso_found = requesterRepository.findById(cso_id);
        Optional<requester> do_found = requesterRepository.findById(do_id);
        if (task_found!=null
                && !task_found.get().getValidated()
                && cso_found!=null
                && do_found!=null){
            task task_to_update = task_found.get();
            task_to_update.setValidated(true);
            task_to_update.setAuthorisedBy(cso_found.get());
            task_to_update.setValidatedBy(do_found.get());
            taskRepository.save(task_to_update);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getAllunverified(){
        return new ResponseEntity<>(taskRepository
                .findByVerifiedFalseAndAuthorisedTrue(), HttpStatus.OK);

    }

    public ResponseEntity<?> verifyTask(Long task_id, Long aetos_id){
        Optional<task> task_found = taskRepository.findById(task_id);
        Optional<aetos> aetos_found = aetosRepository.findById(aetos_id);
        if (task_found!=null && aetos_found!=null ){
            task task_to_update = task_found.get();
            task_to_update.setVerified(true);
            //task_to_update.setTime_verified(datetime.now());
            task_to_update.setVerifiedBy(aetos_found.get());
            taskRepository.save(task_to_update);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getCSO(Long do_id,Long task_id){
        try {
            Optional<requester> requester_found = requesterRepository.findById(
                    taskRepository.findById(task_id).get().getCreatedBy().getId());

            Optional<requester> do_found = requesterRepository.findById(do_id);
            return new ResponseEntity<>(requesterRepository
                    .findByIdNotAndIdNot(requester_found.get().getId(),
                            do_found.get().getId()).stream().map(
                                    requester -> requester.toDto()),
                            HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }





}
