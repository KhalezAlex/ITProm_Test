package org.klozevitz.itprom_test.controllers;

import org.springframework.web.bind.annotation.GetMapping;

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
