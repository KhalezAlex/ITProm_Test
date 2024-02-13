package org.klozevitz.itprom_test.util;

import lombok.RequiredArgsConstructor;
import org.klozevitz.itprom_test.dao.department.IDaoDept;
import org.klozevitz.itprom_test.dao.employee.IDaoEmployee;
import org.klozevitz.itprom_test.dao.profession.IDaoProfession;
import org.klozevitz.itprom_test.model.entities.Department;
import org.klozevitz.itprom_test.model.entities.Employee;
import org.klozevitz.itprom_test.model.entities.Profession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/service")
@RequiredArgsConstructor
public class BaseInitController {
    private final IDaoProfession daoProf;
    private final IDaoDept daoDept;
    private final IDaoEmployee daoEmpl;

    @GetMapping("/generate")
    public String generate() {
        profsInit();
        deptsInit();
        emplsInit();
        return "redirect:/index.html";
    }

    private void profsInit(){
         daoProf.save(Profession.builder().name("ПМ").note("").build());
         daoProf.save(Profession.builder().name("Бухгалтер").note("").build());
         daoProf.save(Profession.builder().name("Разработчик").note("").build());
         daoProf.save(Profession.builder().name("Тестировщик").note("").build());
         System.out.println(daoProf.findAll());
    }

    private void deptsInit() {
        Department d = daoDept.save(Department.builder().name("DB").note("отдел по разработке DB").parentDpt(null).build());
        d = daoDept.save(Department.builder().name("Разработка").note("родитель - 1: DB").parentDpt(d).build());
        daoDept.save(Department.builder().name("Dev").note("родитель - 2: Разработка").parentDpt(d).build());
        daoDept.save(Department.builder().name("Test").note("родитель - 2: Разработка").parentDpt(d).build());
        daoDept.save(Department.builder().name("Design").note("родитель - 2: Разработка").parentDpt(d).build());
        daoDept.findAll().forEach(System.out::println);
    }

    private void emplsInit() {
        daoEmpl.save(Employee.builder()
                .fio("Max")
                .note("java")
                .department(daoDept.findById(3))
                .profession(daoProf.findById(3))
                .build());

        daoEmpl.save(Employee.builder()
                .fio("Anna")
                .note("manual")
                .department(daoDept.findById(4))
                .profession(daoProf.findById(4))
                .build());

        daoEmpl.save(Employee.builder()
                .fio("Anton")
                .note("java")
                .department(daoDept.findById(3))
                .profession(daoProf.findById(3))
                .build());

        daoEmpl.save(Employee.builder()
                .fio("Tim")
                .note("leader")
                .department(daoDept.findById(1))
                .profession(daoProf.findById(1))
                .build());

        daoEmpl.save(Employee.builder()
                .fio("Max")
                .note("auto")
                .department(daoDept.findById(4))
                .profession(daoProf.findById(4))
                .build());
    }
}
