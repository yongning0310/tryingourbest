package com.example.demo.controller;

import com.example.demo.model.aetos;
import com.example.demo.model.requester;
import com.example.demo.model.task;
import com.example.demo.service.taskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class taskController {

    @Autowired
    private taskService taskService;

    @GetMapping("All")
    public ResponseEntity<?> All(){
        return taskService.All();
    }

    //CSO
    @GetMapping("allTasks/{requester_id}")
    public ResponseEntity<?> getAllTasks(@PathVariable Long requester_id){
        return taskService.getAllTasks(requester_id);
    }

    //CSO
    @PutMapping("authoriseRequest/{task_id}")
    public ResponseEntity<?> authoriseRequest(@PathVariable Long task_id){
        return taskService.authoriseRequest(task_id);
    }

    //requester
    @GetMapping("pendingRequest/{requester_id}")
    public ResponseEntity<?> getAllpendingRequest(@PathVariable Long requester_id){
        return taskService.getAllpendingRequest(requester_id);
    }

    //requester
    @PostMapping("createTask/{requester_id}")
    public ResponseEntity<?> createTask(@Valid @RequestBody
                                        task new_task,
                                        @PathVariable Long requester_id){
        return taskService.createTask(new_task,requester_id);
    }

    //DO
    @GetMapping("unvalidatedRequest/{requester_id}")
    public ResponseEntity<?> unvalidatedRequest(@PathVariable Long requester_id){
        return taskService.unvalidatedRequest(requester_id);
    }

    //DO
    @PutMapping("validateRequest/{task_id}/{cso_id}/{do_id}")
    public ResponseEntity<?> validateRequest(@PathVariable Long task_id,
                                             @PathVariable Long cso_id,
                                             @PathVariable Long do_id){
        // System.out.println(task_id.toString()+cso_id.toString()+do_id.toString());
        return taskService.validateRequest(task_id,cso_id,do_id);

    }

    //aetos
    @GetMapping("unverified")
    public ResponseEntity<?> getAllunverified(){

        return taskService.getAllunverified();
    }

    //aetos
    @PutMapping("verifyTask/{task_id}/{aetos_id}")
    public ResponseEntity<?> verifyTask(@PathVariable Long task_id,
                                        @PathVariable Long aetos_id){
        return taskService.verifyTask(task_id,aetos_id);
    }

    @GetMapping("availableCSO/{do_id}/{task_id}")
    public ResponseEntity<?> getCSO(@PathVariable Long do_id,
                                        @PathVariable Long task_id){
        return taskService.getCSO(do_id,task_id);
    }

    @GetMapping("checkBlockchain/{task_id}")
    public ResponseEntity<?> checkBlockchain(@PathVariable Long task_id){
        return taskService.checkBlockChain(task_id);
    }

    // avail CSO ( exclude the DO, requester )
    // include date time


    //
}
