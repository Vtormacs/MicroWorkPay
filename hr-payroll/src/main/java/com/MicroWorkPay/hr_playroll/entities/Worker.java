package com.MicroWorkPay.hr_playroll.entities;

import lombok.*;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Worker implements Serializable {
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private Double dailyIncome;
}