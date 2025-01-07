package com.MicroWorkPay.hr_worket.mappers;

import com.MicroWorkPay.hr_worket.DTOs.WorkerInfoDTO;
import com.MicroWorkPay.hr_worket.entities.Worker;

public class WorkerMapper {

    public WorkerInfoDTO convertToDTO(Worker worker) {
        WorkerInfoDTO dto = new WorkerInfoDTO();
        dto.setId(worker.getId());
        dto.setName(worker.getName());
        dto.setDailyIncome(worker.getDailyIncome());
        return dto;
    }
}
