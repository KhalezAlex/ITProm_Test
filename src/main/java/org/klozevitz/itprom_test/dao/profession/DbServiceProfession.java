package org.klozevitz.itprom_test.dao.profession;

import lombok.RequiredArgsConstructor;
import org.klozevitz.itprom_test.exceptions.RecordNotFoundException;
import org.klozevitz.itprom_test.model.Profession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbServiceProfession implements IDaoProfession{
    private final IRepoProfession repo;

    @Override
    public List<Profession> findAll() {
        return (List<Profession>) repo.findAll();
    }

    @Override
    public Profession findById(Integer id) {
        return repo.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Profession save(Profession profession) {
        return repo.save(profession);
    }

    public Profession update(Profession profession) {
        Profession professionToUpdate = repo.findById(profession.getId()).get();
        professionToUpdate.setName(profession.getName());
        professionToUpdate.setNote(profession.getNote());
        return repo.save(profession);
    }

    @Override
    public Profession delete(Integer id) {
        Profession profession = repo.findById(id).orElseThrow(RecordNotFoundException::new);
        repo.deleteById(id);
        return profession;
    }
}
