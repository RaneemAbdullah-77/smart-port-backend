package com.smartport.auth.infrastructure.persistence.entity;

import com.smartport.auth.domain.entities.Employee;
import com.smartport.auth.domain.enums.EmployeeStatus;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "EMPLOYEE")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeEntity {

    @Id
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "USER_ID", columnDefinition = "RAW(16)", nullable = false)
    private UUID userId;

    @Column(name = "FIRST_NAME", length = 100)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    private EmployeeStatus status;

    @PrePersist
    protected void generateUserId() {
        if (userId == null) {
            userId = UUID.randomUUID();
        }
        if (status == null) {
            status = EmployeeStatus.PENDING;
        }
    }

    public Employee toDomain() {
        Employee employee = new Employee(userId, firstName, lastName);
        employee.setStatus(status);
        return employee;
    }

    public static EmployeeEntity fromDomain(Employee employee) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setUserId(employee.getUserId());
        entity.setFirstName(employee.getFirstName());
        entity.setLastName(employee.getLastName());
        entity.setStatus(employee.getStatus());
        return entity;
    }
}
