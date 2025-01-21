package com.MicroWorkPay.hr_worket.controllers;

import com.MicroWorkPay.hr_worket.DTOs.WorkerInfoDTO;
import com.MicroWorkPay.hr_worket.services.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RefreshScope
@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private static Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Value("${test.config}")
    private String testConfig;

    @Autowired
    private Environment env;

    @Autowired
    private WorkerService workerService;

    @GetMapping
    public ResponseEntity<List<WorkerInfoDTO>> getAllWorkers() {
        try {
            return ResponseEntity.ok(workerService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerInfoDTO> getWorkerForId(@PathVariable UUID id) {
        try {

            logger.info("PORT = " + env.getProperty("local.server.port"));

            return ResponseEntity.ok(workerService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/configs")
    public ResponseEntity<Void> getConfigs() {
        logger.info("CONFIG = " + testConfig);
        return  ResponseEntity.noContent().build();
    }
}
