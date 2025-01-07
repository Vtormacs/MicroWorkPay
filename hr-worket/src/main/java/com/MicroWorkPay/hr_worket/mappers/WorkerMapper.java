package com.MicroWorkPay.hr_worket.mappers;

import com.MicroWorkPay.hr_worket.DTOs.WorkerInfoDTO;
import com.MicroWorkPay.hr_worket.entities.Worker;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface WorkerMapper {
    WorkerMapper INSTANCE = Mappers.getMapper(WorkerMapper.class);

    WorkerInfoDTO workerToWorkerInfoDTO(Worker worker);
    List<WorkerInfoDTO> workerListToWorkerInfoDTOList(List<Worker> workers);
}
