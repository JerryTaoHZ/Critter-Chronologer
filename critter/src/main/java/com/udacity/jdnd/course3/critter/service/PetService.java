package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    final PetRepository petRepository;
    final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

//    public Pet savePet(Pet pet) {
//        Pet savedPet = petRepository.save(pet);
//        return savedPet;
//    }

    /**
     *
     * @param pet
     * @param ownerId Do the remaining work of converting petDTO.ownerId -> Pet.owner;
     * @return
     */
    public Pet savePet(Pet pet, long ownerId) {
        Customer owner = customerRepository.findById(ownerId).orElseThrow();
        pet.setOwner(owner);
        Pet savedPet = petRepository.save(pet);
        owner.getPets().add(savedPet);
        customerRepository.save(owner);
        return savedPet;
    }

    public Pet getPetById(long id) {
        return petRepository.findById(id).orElseThrow();
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        Customer owner = customerRepository.findById(ownerId).orElseThrow();
        List<Pet> pets = owner.getPets();
        return pets;
//        return petRepository.getPetsByOwner(id);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}
