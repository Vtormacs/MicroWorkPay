package com.MicroWorkPay.hr_playroll.services;

import com.MicroWorkPay.hr_playroll.entities.Payment;
import com.MicroWorkPay.hr_playroll.entities.Worker;
import com.MicroWorkPay.hr_playroll.feignclients.WorkerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private WorkerFeignClient workerFeignClient;

    public Payment getPayment(UUID workerId, int days){

        Worker worker = workerFeignClient.getWorkerForId(workerId).getBody();

        return new Payment(worker.getName(), worker.getDailyIncome(), days);
    }
}
