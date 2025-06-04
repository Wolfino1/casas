package com.casas.casas.infrastructure.endpoints.rest;

import com.casas.casas.application.dto.response.DepartmentResponse;
import com.casas.casas.application.mappers.DepartmentDtoMapper;
import com.casas.casas.application.services.DepartmentService;
import com.casas.casas.domain.model.DepartmentModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor

public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentDtoMapper departmentDtoMapper;

    @GetMapping("/get")
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        List<DepartmentResponse> response = departmentService.getAllDepartments()
                .stream()
                .map(departmentDtoMapper::modelToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {
        DepartmentModel model = departmentService.getDepartmentById(id);
        DepartmentResponse response = departmentDtoMapper.modelToResponse(model);
        return ResponseEntity.ok(response);
    }
}
