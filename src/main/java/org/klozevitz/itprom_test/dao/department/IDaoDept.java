package org.klozevitz.itprom_test.dao.department;

import org.klozevitz.itprom_test.dao.IDaoDb;
import org.klozevitz.itprom_test.model.entities.Department;
import org.klozevitz.itprom_test.model.dto.DepartmentDTO;

public interface IDaoDept extends IDaoDb<Department> {
    public Department update(DepartmentDTO dto);
}
