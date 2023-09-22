package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    public Schedule createSchedule(Schedule schedule) {
        Schedule saved = scheduleRepository.save(schedule);
        return saved;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForEmployee(Employee employee) {
        return  scheduleRepository.findByEmployees(employee);
    }

    public List<Schedule> getScheduleForPet(Pet pet) {
//        return scheduleRepository.findByPet(pet);
        return scheduleRepository.findByPets(pet);
    }

    public List<Schedule> getScheduleForCustomer(List<Pet> pets) {
        List<Schedule> combined = new ArrayList<>();
        for (Pet pet : pets) {
            List<Schedule> schedule = getScheduleForPet(pet);
            combined.addAll(schedule);
        }
        return combined;
    }
}
