package com.example.demo.service;

import com.example.demo.model.aetos;
import com.example.demo.model.requester;
import com.example.demo.model.task;
import com.example.demo.repository.aetosRepository;
import com.example.demo.repository.requesterRepository;
import com.example.demo.repository.taskRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@EnableAsync
public class taskService {

    @Autowired
    private taskRepository taskRepository;

    @Autowired
    private requesterRepository requesterRepository;

    @Autowired
    private aetosRepository aetosRepository;

    @Autowired
    private Environment environment;

    String api = "https://c37b-119-74-37-116.ap.ngrok.io";
    String passwordForAuthorization = "123456";


    public ResponseEntity<?> All(){
        return new ResponseEntity<>(taskRepository
                .findAll().stream().map(task -> task.toDto()), HttpStatus.OK);

    }

    public ResponseEntity<?> getAllTasks(Long requester_id){
        return new ResponseEntity<>(taskRepository
                .findByAuthorisedBy_IdEqualsAndAuthorisedFalse(
                        requester_id).stream().map(task -> task.toDto()), HttpStatus.OK);

    }
    //@Async("threadPoolTaskExecutor")
    public ResponseEntity<?> authoriseRequest(Long task_id){
        Optional<task> task_found = taskRepository.findById(task_id);
        if (task_found!=null
                && !task_found.get().getAuthorised()
                && task_found.get().getValidated() ){
            task task_to_update = task_found.get();
            task_to_update.setAuthorised(true);
            long millis = System.currentTimeMillis();
            java.util.Date date = new java.util.Date(millis);
            task_to_update.setTime_authorised(date.toString());
            taskRepository.save(task_to_update);
            ExecutorService threadpool = Executors.newCachedThreadPool();
            threadpool.submit(() -> sendToBlockChainAndRecord(task_to_update));

            return new ResponseEntity<>(HttpStatus.OK);
        }
        //System.out.println(task_id);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getAllpendingRequest(Long requester_id){
        return new ResponseEntity<>(taskRepository
                .findByCreatedBy_IdEqualsAndVerifiedFalse
                        (requester_id).stream().map(task -> task.toDto()), HttpStatus.OK);

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
        task_to_save.setAttachments(createTask.getAttachments());
        task_to_save.setCreatedBy(requesterRepository.findById(requester_id).get());
        return new ResponseEntity<>(taskRepository.save(task_to_save).toDto(), HttpStatus.OK);
    }

    public ResponseEntity<?> unvalidatedRequest(Long requester_id){
        return new ResponseEntity<>(taskRepository.
                findByCreatedBy_IdNotAndValidatedFalse
                        (requester_id).stream().map
                        (task -> task.toDto()), HttpStatus.OK);

    }

    public ResponseEntity<?> validateRequest(Long task_id, Long cso_id, Long do_id ){
        Optional<task> task_found = taskRepository.findById(task_id);
        Optional<requester> cso_found = requesterRepository.findById(cso_id);
        Optional<requester> do_found = requesterRepository.findById(do_id);
        if (task_found!=null
                && !task_found.get().getValidated()
                && cso_found!=null
                && do_found!=null
                && do_found.get().getDesignated_officer()
                && task_found.get().getCreatedBy().getId() != cso_id
                && task_found.get().getCreatedBy().getId() != do_id
                && cso_id != do_id){
            task task_to_update = task_found.get();
            task_to_update.setValidated(true);
            task_to_update.setAuthorisedBy(cso_found.get());
            task_to_update.setValidatedBy(do_found.get());
            long millis = System.currentTimeMillis();
            java.util.Date date = new java.util.Date(millis);
            task_to_update.setTime_validated(date.toString());
            taskRepository.save(task_to_update);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getAllunverified(){
        return new ResponseEntity<>(taskRepository
                .findByVerifiedFalseAndAuthorisedTrue().stream().map(task -> task.toDto()), HttpStatus.OK);

    }

    public ResponseEntity<?> verifyTask(Long task_id, Long aetos_id){
        Optional<task> task_found = taskRepository.findById(task_id);
        Optional<aetos> aetos_found = aetosRepository.findById(aetos_id);
        if (task_found!=null && aetos_found!=null && !task_found.get().getVerified()){
            task task_to_update = task_found.get();
            task_to_update.setVerified(true);
            //task_to_update.setTime_verified(datetime.now());
            task_to_update.setVerifiedBy(aetos_found.get());
            long millis = System.currentTimeMillis();
            java.util.Date date = new java.util.Date(millis);
            task_to_update.setTime_verified(date.toString());
            taskRepository.save(task_to_update);
            ExecutorService threadpool = Executors.newCachedThreadPool();
            threadpool.submit(() -> sendToBlockChainAndRecord(task_to_update));


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

    public ResponseEntity<?> checkBlockChain(Long task_id){
        try {
            Optional<task> task_found = taskRepository.findById(task_id);
            if (task_found != null) {
                String assest_id = task_found.get().getAsset_id();
                task task_to_send = task_found.get();
                task_to_send.setAsset_id(null);
                Map<String, Object> map = new HashMap<>();
                map.put("metadatafile", task_to_send);
                map.put("asset_id", assest_id);
                ResponseEntity<String> response = createPost(map,
                        api + "/check");
                if (response != null) {
                    Boolean is_in_blockchain = response.getBody().contains("true");
                    if (is_in_blockchain) {
                        return new ResponseEntity<>(HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //@Async
    public void sendToBlockChainAndRecord(task task_to_update) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("metadatafile", task_to_update);
            map.put("pon_id",task_to_update.getId().toString());
            ResponseEntity<String> response = createPost(map,
                    api + "/mint");
            if (response != null) {
                String body = response.getBody();
                ObjectMapper mapper = new JsonMapper();
                JsonNode json = mapper.readTree(body);

                //System.out.println(json);
                task_to_update.setAsset_id(json.get("assetID").toString());
                taskRepository.save(task_to_update);

                System.out.println("Execute method asynchronously. "
                        + Thread.currentThread().getName());
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public ResponseEntity<String> createPost(Object body, String api) {
        String url = api;

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // set custom header
        headers.set("Authorization", passwordForAuthorization);

//        // create a map for post parameters
//        Map<String, Object> map = new HashMap<>();
//        map.put("metadatahash", "12345678912345678912345678911111");
//        map.put("pon_id", "01111111");

        // build the request
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        // send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        System.out.println(response);
        // check response status code
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response;
        } else {
            return null;
        }
    }



}

