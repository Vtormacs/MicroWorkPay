package com.example.hr_oauth.entities;


import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
}
