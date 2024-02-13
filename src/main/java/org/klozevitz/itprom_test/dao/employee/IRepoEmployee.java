package org.klozevitz.itprom_test.dao.employee;

import org.klozevitz.itprom_test.model.entities.Employee;
import org.springframework.data.repository.CrudRepository;

public interface IRepoEmployee extends CrudRepository<Employee, Integer> {
}
