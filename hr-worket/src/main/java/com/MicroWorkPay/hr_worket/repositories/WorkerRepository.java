package com.MicroWorkPay.hr_worket.repositories;

import com.MicroWorkPay.hr_worket.entities.Worker;
import org.hibernate.secure.internal.JaccSecurityListener;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkerRepository extends JpaRepository<Worker, UUID> {
}
