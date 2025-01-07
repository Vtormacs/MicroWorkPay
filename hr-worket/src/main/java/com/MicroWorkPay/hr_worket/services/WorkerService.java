package com.MicroWorkPay.hr_worket.services;

import com.MicroWorkPay.hr_worket.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;


}
