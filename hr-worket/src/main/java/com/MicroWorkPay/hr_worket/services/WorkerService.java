package com.MicroWorkPay.hr_worket.services;

import com.MicroWorkPay.hr_worket.DTOs.WorkerInfoDTO;
import com.MicroWorkPay.hr_worket.entities.Worker;
import com.MicroWorkPay.hr_worket.mappers.WorkerMapper;
import com.MicroWorkPay.hr_worket.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    public List<WorkerInfoDTO> findAll() {
        List<Worker> workers = workerRepository.findAll();
        return WorkerMapper.INSTANCE.workerListToWorkerInfoDTOList(workers);
    }
}
