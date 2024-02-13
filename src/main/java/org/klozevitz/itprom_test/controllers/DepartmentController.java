package org.klozevitz.itprom_test.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.itprom_test.dao.department.IDaoDept;
import org.klozevitz.itprom_test.model.entities.Department;
import org.klozevitz.itprom_test.model.dto.DepartmentDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final IDaoDept daoDept;

    @GetMapping
    public List<DepartmentDTO> all() {
        return daoDept.findAll().stream()
                .map(DepartmentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DepartmentDTO findById(@PathVariable int id) {
        return DepartmentDTO.fromEntity(daoDept.findById(id));
    }

    @PostMapping
    public DepartmentDTO create(@RequestBody DepartmentDTO dto) {
        Department parent = dto.getParentId() != 0 ? daoDept.findById(dto.getParentId()) : null;
        Department department = Department.fromDto(dto, parent);
        department = daoDept.save(department);
        return DepartmentDTO.fromEntity(department);
    }

    @PutMapping("/{id}")
    public DepartmentDTO update(@PathVariable String id, @RequestBody DepartmentDTO dto) {
        dto.setId(Integer.parseInt(id));
        return DepartmentDTO.fromEntity(daoDept.update(dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        daoDept.delete(id);
    }
}
