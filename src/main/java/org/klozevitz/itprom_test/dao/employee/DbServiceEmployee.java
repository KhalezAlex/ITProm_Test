package org.klozevitz.itprom_test.dao.employee;

import lombok.RequiredArgsConstructor;
import org.klozevitz.itprom_test.exceptions.RecordNotFoundException;
import org.klozevitz.itprom_test.model.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbServiceEmployee implements IDaoEmployee {
    private final IRepoEmployee repo;

    @Override
    public List<Employee> findAll() {
        return (List<Employee>) repo.findAll();
    }

    @Override
    public Employee findById(Integer id) {
        return repo.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Employee save(Employee employee) {
        return repo.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        Employee employeeToUpdate = repo.findById(employee.getId()).get();
        employeeToUpdate.setFio(employee.getFio());
        employeeToUpdate.setNote(employee.getNote());
        employeeToUpdate.setDepartment(employee.getDepartment());
        employeeToUpdate.setProfession(employee.getProfession());
        return repo.save(employeeToUpdate);
    }

    @Override
    public Employee delete(Integer id) {
        Employee employee = repo.findById(id).orElseThrow(RecordNotFoundException::new);
        repo.deleteById(id);
        return employee;
    }
}
