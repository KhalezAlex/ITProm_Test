package org.klozevitz.itprom_test.dao.department;

import org.klozevitz.itprom_test.model.entities.Department;
import org.springframework.data.repository.CrudRepository;

public interface IRepoDept extends CrudRepository<Department, Integer> {
}
