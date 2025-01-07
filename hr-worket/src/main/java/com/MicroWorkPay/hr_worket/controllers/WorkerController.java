package com.MicroWorkPay.hr_worket.controllers;

import com.MicroWorkPay.hr_worket.DTOs.WorkerInfoDTO;
import com.MicroWorkPay.hr_worket.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
