package org.example.controller;


import org.example.model.GetObjectResponse;
import org.example.service.ObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ObjectsController {

    private final static Logger logger = LoggerFactory.getLogger(ObjectsController.class);

    @Autowired
    private ObjectService objectService;

    @GetMapping("/single-object/{id}")
    public ResponseEntity<GetObjectResponse> singleObject(@PathVariable Long id) {
        logger.info("single-object request {}",id);
        GetObjectResponse result = objectService.getObjectById(id);
        logger.info("single-object result {}", result);
        return ResponseEntity.ok(result);
    }
}
