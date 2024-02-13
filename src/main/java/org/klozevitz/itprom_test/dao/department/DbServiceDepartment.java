package org.klozevitz.itprom_test.dao.department;

import lombok.RequiredArgsConstructor;
import org.klozevitz.itprom_test.exceptions.RecordNotFoundException;
import org.klozevitz.itprom_test.model.Department;
import org.klozevitz.itprom_test.model.dto.DepartmentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbServiceDepartment implements IDaoDept {
    private final IRepoDept repo;

    @Override
    public List<Department> findAll() {
        return (List<Department>) repo.findAll();
    }

    @Override
    public Department findById(Integer id) {
        return repo.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Department save(Department department) {
        return repo.save(department);
    }

    @Override
    public Department update(DepartmentDTO dto) {
        Department departmentToUpdate = repo.findById(dto.getId()).get();
        departmentToUpdate.setName(dto.getName());
        departmentToUpdate.setNote(dto.getNote());
        if(dto.getParentId() != 0)
            departmentToUpdate.setParentDpt(repo.findById(dto.getParentId()).get());
        else
            departmentToUpdate.setParentDpt(null);
        return repo.save(departmentToUpdate);
    }

    @Override
    public Department delete(Integer id) {
        Department department = repo.findById(id).orElseThrow(RecordNotFoundException::new);
        repo.deleteById(id);
        return department;
    }
}
