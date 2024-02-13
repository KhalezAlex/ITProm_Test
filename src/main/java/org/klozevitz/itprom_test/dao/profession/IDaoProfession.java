package org.klozevitz.itprom_test.dao.profession;

import org.klozevitz.itprom_test.dao.IDaoDb;
import org.klozevitz.itprom_test.model.Profession;

public interface IDaoProfession extends IDaoDb<Profession> {
    Profession update(Profession profession);
}
