package com.MicroWorkPay.hr_playroll.controllers;

import com.MicroWorkPay.hr_playroll.entities.Payment;
import com.MicroWorkPay.hr_playroll.services.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @HystrixCommand(fallbackMethod = "getPaymentAlternative")
    @GetMapping("/{workerId}/days/{days}")
    public ResponseEntity<Payment> getPayment(@PathVariable UUID workerId, @PathVariable Integer days){
        try {
            return ResponseEntity.ok(paymentService.getPayment(workerId, days));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Payment> getPaymentAlternative(UUID workerId, Integer days){
        return ResponseEntity.ok(new Payment("Default", 0.0, days));
    }
}
