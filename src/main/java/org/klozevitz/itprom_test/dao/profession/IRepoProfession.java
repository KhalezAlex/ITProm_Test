package org.klozevitz.itprom_test.dao.profession;

import org.klozevitz.itprom_test.model.entities.Profession;
import org.springframework.data.repository.CrudRepository;

public interface IRepoProfession extends CrudRepository<Profession, Integer> {
}
