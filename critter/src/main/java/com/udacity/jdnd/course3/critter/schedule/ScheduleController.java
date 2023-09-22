package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    final ScheduleService scheduleService;
    final PetService petService;
    final EmployeeService employeeService;
    final CustomerService customerService;

    public ScheduleController(ScheduleService scheduleService, PetService petService, EmployeeService employeeService, CustomerService customerService) {
        this.scheduleService = scheduleService;
        this.petService = petService;
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule savedSchedule = scheduleService.createSchedule(
                convertScheduleDTOToEntity(scheduleDTO));
        return convertEntityToScheduleDTO(savedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> DTOs = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedules();
        for (Schedule schedule : schedules) {
            DTOs.add(convertEntityToScheduleDTO(schedule));
        }
        return DTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> DTOs = new ArrayList<>();
        Pet pet = petService.getPetById(petId);
        List<Schedule> schedules = scheduleService.getScheduleForPet(pet);
        if (schedules!=null) {
            for (Schedule schedule : schedules) {
                DTOs.add(convertEntityToScheduleDTO(schedule));
            }
        }
        return DTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> DTOs = new ArrayList<>();
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employee);
        if (schedules!=null) {
            for (Schedule schedule : schedules) {
                DTOs.add(convertEntityToScheduleDTO(schedule));
            }
        }
        return DTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> DTOs = new ArrayList<>();
        List<Pet> pets = petService.getPetsByOwner(customerId);
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(pets);
        if (schedules!=null) {
            for (Schedule schedule : schedules) {
                DTOs.add(convertEntityToScheduleDTO(schedule));
            }
        }
        return DTOs;

    }

    private static ScheduleDTO convertEntityToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Pet> pets = schedule.getPets();
        if(pets!=null) {
//            for (Pet pet : pets) {
//                scheduleDTO.getPetIds().add(pet.getId());
//            }
            pets.forEach(pet -> scheduleDTO.getPetIds().add(pet.getId()));
        }

        List<Employee> employees = schedule.getEmployees();
        if(employees!=null) {
            employees.forEach(employee -> scheduleDTO.getEmployeeIds().add(employee.getId()));
        }

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        List<Long> petIds = scheduleDTO.getPetIds();
        if(petIds!=null) {
            for(Long id : petIds) {
                schedule.getPets().add( petService.getPetById(id) );
            }
        }
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        if(employeeIds!=null) {
            for(Long id : employeeIds) {
                schedule.getEmployees().add( employeeService.getEmployeeById(id) );
            }
        }
        return schedule;
    }
}
