package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllCustomers(List<Long> ids) {
        return employeeRepository.findAllById(ids); // what's this?
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public Employee setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        employee.setDaysAvailable(daysAvailable);
        return employee;
    }

    public List<Employee> findEmployeesForService0(EmployeeRequestDTO er) {
        List<Employee> matches = new ArrayList<>();
        List<Employee> all = employeeRepository.findAll();
        Set<EmployeeSkill> skills = er.getSkills();
        for (Employee employee : all) {
            boolean flag = true;
//            for (EmployeeSkill skill : skills) {
//                if(!employee.getSkills().contains(skill)) flag = false;
//            }
            if(employee.getSkills().containsAll(skills)) flag = false;
            if (!employee.getDaysAvailable().contains(er.getDate().getDayOfWeek()) ) flag = false;
            if(flag) matches.add(employee);
        }
        return matches;
    }

    public List<Employee> findEmployeesForService(EmployeeRequestDTO er) {
        List<Employee> filtering = employeeRepository.findAllByDaysAvailableContaining(er.getDate().getDayOfWeek());
        return filtering.stream().filter(e -> e.getSkills().containsAll(er.getSkills())).collect(Collectors.toList());
    }

}
