package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.exception.IdNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee findById(Long employeeId){
        Optional<Employee> optionalEmployee =  employeeRepository.findById(employeeId);
        Employee employee = new Employee();
        if(optionalEmployee.isPresent()){
            employee=optionalEmployee.get();
            return employee;
        }else{
            throw new IdNotFoundException("employee id not found");
        }
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Employee employee = findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        save(employee);
    }

    public List<Employee> findEmployeesForService(DayOfWeek dayOfWeek,Set<EmployeeSkill> employeeSkills){
        List<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(dayOfWeek);
        List<Employee> availableEmployees = new ArrayList<>();
        for(Employee employee:employees){
            if(employee.getSkills().containsAll(employeeSkills)){
                availableEmployees.add(employee);
            }
        }
        return availableEmployees;
    }
}
