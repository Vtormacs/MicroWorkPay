package com.MicroWorkPay.hr_playroll.services;

import com.MicroWorkPay.hr_playroll.entities.Payment;
import com.MicroWorkPay.hr_playroll.entities.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {

    @Value("${hr-worker.host}")
    private String workerHost;

    @Autowired
    private RestTemplate restTemplate;

    public Payment getPayment(UUID workerId, int days){

        Map<String, String> uriVariables = new HashMap<>();

        uriVariables.put("id", workerId.toString());

        Worker worker = restTemplate.getForObject(workerHost + "/api/workers/{id}", Worker.class, uriVariables);

        return new Payment(worker.getName(), worker.getDailyIncome(), days);
    }
}
