package com.example.prog4gradle.controller;

import com.example.prog4gradle.model.Employee;
import com.example.prog4gradle.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public String getEmployees(@RequestParam(value = "firstName", required = false) String firstName,
                               @RequestParam(value = "lastName", required = false) String lastName,
                               @RequestParam(value = "sex", required = false) String sex,
                               @RequestParam(value = "jobTitle", required = false) String jobTitle,
                               @RequestParam(value = "hireDateFrom", required = false) LocalDate hireDateFrom,
                               @RequestParam(value = "hireDateTo", required = false) LocalDate hireDateTo,
                               @RequestParam(value = "departureDateFrom", required = false) LocalDate departureDateFrom,
                               @RequestParam(value = "departureDateTo", required = false) LocalDate departureDateTo,
                               Model model) {
        List<Employee> employeeList = employeeService.getFilteredEmployees(firstName, lastName, sex, jobTitle, hireDateFrom, hireDateTo, departureDateFrom, departureDateTo);
        model.addAttribute("employees", employeeList);
        return "employee-list";
    }

    @GetMapping("/add-employee")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee"; // Nom du template Thymeleaf pour afficher le formulaire d'ajout d'employé
    }

    @PostMapping("/add-employee")
    public String addEmployee(@ModelAttribute("employee") Employee employeeModel) {
        employeeService.addEmployee(employeeModel);
        return "redirect:/employees";
    }

    @GetMapping("/employee/{id}")
    public String getEmployeeDetails(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee-details";
    }

    // Autres méthodes du contrôleur pour d'autres opérations liées aux employés
}