package com.MicroWorkPay.hr_worket.services;

import com.MicroWorkPay.hr_worket.DTOs.WorkerInfoDTO;
import com.MicroWorkPay.hr_worket.entities.Worker;
import com.MicroWorkPay.hr_worket.mappers.WorkerMapper;
import com.MicroWorkPay.hr_worket.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    private final WorkerMapper workerMapper = new WorkerMapper();

    public List<WorkerInfoDTO> findAll() {
        List<Worker> workers = workerRepository.findAll();
        return workers.stream()
                .map(workerMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public WorkerInfoDTO findById(UUID id) {
        Worker worker = workerRepository.findById(id).orElseThrow();
        return workerMapper.convertToDTO(worker);
    }
}
