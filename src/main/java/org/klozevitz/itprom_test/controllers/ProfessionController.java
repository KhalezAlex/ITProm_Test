package org.klozevitz.itprom_test.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.itprom_test.dao.profession.IDaoProfession;
import org.klozevitz.itprom_test.model.entities.Profession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professions")
@RequiredArgsConstructor
public class ProfessionController {
    private final IDaoProfession daoP;

    @GetMapping
    public List<Profession> all() {
        return daoP.findAll();
    }

    @GetMapping("/{id}")
    public Profession findById(@PathVariable int id) {
        return daoP.findById(id);
    }

    @PostMapping
    public Profession create(@RequestBody Profession profession) {
        return daoP.save(profession);
    }

    @PutMapping("/{id}")
    public Profession update(@PathVariable String id, @RequestBody Profession profession) {
        profession.setId(Integer.parseInt(id));
        return daoP.update(profession);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        daoP.delete(id);
    }
}
