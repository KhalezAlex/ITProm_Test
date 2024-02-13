package org.klozevitz.itprom_test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {


    @GetMapping("/employee")
    public String employee() {
        return "employees";
    }

    @GetMapping("/profession")
    public String profession() {
        return "professions";
    }
}
