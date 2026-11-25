package com.smartport.auth.presentation.rest;

import com.smartport.auth.application.dto.requests.AddEmployeeRequest;
import com.smartport.auth.application.dto.responses.AddEmployeeResponse;
import com.smartport.auth.application.usecases.AddEmployeeCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class AddEmployeeController {

    private final AddEmployeeCommand addEmployeeCommand;

    @PostMapping
    public ResponseEntity<AddEmployeeResponse> addEmployee(
            @Valid @RequestBody AddEmployeeRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addEmployeeCommand.execute(request));
    }
}
