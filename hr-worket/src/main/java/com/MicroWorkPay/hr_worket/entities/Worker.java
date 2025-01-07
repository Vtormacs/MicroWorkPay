package com.MicroWorkPay.hr_worket.entities;

import lombok.*;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Worker implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private Double dailyIncome;
}