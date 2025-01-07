package com.MicroWorkPay.hr_worket.DTOs;

import lombok.Data;
import java.util.UUID;

@Data
public class WorkerInfoDTO {
    private UUID id;
    private String name;
    private Double dailyIncome;
}