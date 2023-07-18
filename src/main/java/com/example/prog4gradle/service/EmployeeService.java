package com.example.prog4gradle.service;

import com.example.prog4gradle.model.Employee;
import com.example.prog4gradle.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee addEmployee(Employee employeeModel) {
        Employee employee = new Employee();
        employee.setDateOfBirth(employeeModel.getDateOfBirth());
        employee.setFirstName(employeeModel.getFirstName());
        employee.setLastName(employeeModel.getLastName());

        MultipartFile photo = employeeModel.getPhoto();
        if (photo != null && !photo.isEmpty()) {
            try {
                byte[] imageBytes = photo.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                employee.setImage(base64Image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return employeeRepository.save(employee);
    }

    // Autres méthodes pour effectuer des opérations sur les employés en utilisant le repository
}