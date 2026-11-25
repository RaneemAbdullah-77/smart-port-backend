package com.smartport.auth.domain.entities;

import com.smartport.auth.domain.enums.EmployeeStatus;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Employee {

    private UUID userId;
    private String firstName;
    private String lastName;
    private EmployeeStatus status = EmployeeStatus.PENDING;

    public Employee(UUID userId) {
        this.userId = userId;
    }

    public Employee(UUID userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = EmployeeStatus.PENDING;
    }
}
