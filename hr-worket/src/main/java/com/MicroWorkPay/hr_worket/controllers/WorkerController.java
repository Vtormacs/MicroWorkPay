package com.MicroWorkPay.hr_worket.controllers;

import com.MicroWorkPay.hr_worket.DTOs.WorkerInfoDTO;
import com.MicroWorkPay.hr_worket.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

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
            return ResponseEntity.ok(workerService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
