package org.klozevitz.itprom_test.dao.employee;

import org.klozevitz.itprom_test.dao.IDaoDb;
import org.klozevitz.itprom_test.model.Employee;
import org.klozevitz.itprom_test.model.dto.EmployeeDTO;

public interface IDaoEmployee extends IDaoDb<Employee> {
    Employee update(Employee employee);
}
