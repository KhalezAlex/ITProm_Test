package org.klozevitz.itprom_test.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.itprom_test.dao.department.IDaoDept;
import org.klozevitz.itprom_test.dao.employee.IDaoEmployee;
import org.klozevitz.itprom_test.dao.profession.IDaoProfession;
import org.klozevitz.itprom_test.exceptions.RecordNotFoundException;
import org.klozevitz.itprom_test.exceptions.WrongArgumentsException;
import org.klozevitz.itprom_test.model.Department;
import org.klozevitz.itprom_test.model.Employee;
import org.klozevitz.itprom_test.model.Profession;
import org.klozevitz.itprom_test.model.dto.DepartmentDTO;
import org.klozevitz.itprom_test.model.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final IDaoProfession daoProf;
    private final IDaoDept daoDept;
    private final IDaoEmployee daoEmpl;

    @GetMapping
    public Map<String, Object> all() {
        Map<String, Object> result = new HashMap<>();
        result.put("employees", daoEmpl.findAll().stream().map(EmployeeDTO::fromEntity).collect(Collectors.toList()));
        result.put("departments", daoDept.findAll().stream().map(DepartmentDTO::fromEntity).collect(Collectors.toList()));
        result.put("professions", daoProf.findAll());
        return result;
    }

    @GetMapping("/{id}")
    public EmployeeDTO findById(@PathVariable int id) {
        return EmployeeDTO.fromEntity(daoEmpl.findById(id));
    }

    @PostMapping
    public EmployeeDTO create(@RequestBody EmployeeDTO dto) {
        if (dto.getDeptId() == 0 || dto.getProfId() == 0) {
            throw new WrongArgumentsException();
        }
        else {
            Employee employee = Employee.builder()
                    .fio(dto.getFio())
                    .note(dto.getNote())
                    .profession(daoProf.findById(dto.getProfId()))
                    .department(daoDept.findById(dto.getDeptId()))
                    .build();
            employee = daoEmpl.save(employee);
            return EmployeeDTO.fromEntity(employee);
        }
    }

    @PutMapping("/{id}")
    public EmployeeDTO update(@PathVariable String id, @RequestBody EmployeeDTO dto) {
        Employee employee = Employee.builder()
                .id(Integer.parseInt(id))
                .fio(dto.getFio())
                .note(dto.getNote())
                .profession(daoProf.findById(dto.getProfId()))
                .department(daoDept.findById(dto.getDeptId()))
                .build();
        return EmployeeDTO.fromEntity(daoEmpl.update(employee));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        daoEmpl.delete(id);
    }
}
