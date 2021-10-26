package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    PetService petService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    CustomerService customerService;

    public Schedule save (Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByPetsContaining(long petId){
        Pet pet = petService.findById(petId);
        return scheduleRepository.findByPetsContaining(pet);
    }

    public List<Schedule> findByEmployeesContaining(long employeeId){
        Employee employee = employeeService.findById(employeeId);
        return scheduleRepository.findByEmployeesContaining(employee);
    }

    public List<Schedule> getScheduleForCustomer(long customerId){
        Customer customer = customerService.findById(customerId);
        List<Pet> pets = customer.getPets();
        List<Schedule> finalSchedules = new ArrayList<>();
        for(Pet pet:pets){
            List<Schedule> duplicateSchedules = findByPetsContaining(pet.getId());
            for(Schedule schedule:duplicateSchedules){
                if(!finalSchedules.contains(schedule)){
                    finalSchedules.add(schedule);
                }
            }
        }
        return finalSchedules;
    }


}
