package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private PetService petService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.save(convertScheduleDTOToSchedule(scheduleDTO));
        return convertScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAll();
        return schedules.stream().map(schedule -> convertScheduleToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.findByPetsContaining(petId);
        return schedules.stream().map(schedule -> convertScheduleToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.findByEmployeesContaining(employeeId);
        return schedules.stream().map(schedule -> convertScheduleToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        return schedules.stream().map(schedule -> convertScheduleToScheduleDTO(schedule)).collect(Collectors.toList());
    }


    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);
        List<Long> petIds = scheduleDTO.getPetIds();
        List<Pet> pets = new ArrayList<>();
        for(Long petId:petIds){
            pets.add(petService.findById(petId));
        }
        schedule.setPets(pets);

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Employee> employees = new ArrayList<>();
        for(Long employeeId:employeeIds){
            employees.add(employeeService.findById(employeeId));
        }
        schedule.setEmployees(employees);

        return  schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        List<Pet> pets = schedule.getPets();
        List<Long> petIds = new ArrayList<>();
        for(Pet pet:pets){
            petIds.add(pet.getId());
        }
        scheduleDTO.setPetIds(petIds);

        List<Employee> employees = schedule.getEmployees();
        List<Long> employeeIds = new ArrayList<>();
        for(Employee employee:employees){
            employeeIds.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }
}
