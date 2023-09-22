package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet savedPet = petService.savePet(convertPetDTOToEntity(petDTO),
                petDTO.getOwnerId()); // the 2nd field make the conversion complete!
        return convertEntityToPetDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertEntityToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> allDTOs = new ArrayList<>();
        List<Pet> pets = petService.getAllPets();
        for (Pet p : pets) {
            allDTOs.add(convertEntityToPetDTO(p));
        }
        return allDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long ownerId) {
        List<PetDTO> allDTOs = new ArrayList<>();
        List<Pet> petsByOwner = petService.getPetsByOwner(ownerId);
        for (Pet p : petsByOwner) {
            allDTOs.add(convertEntityToPetDTO(p));
        }
        return allDTOs;
    }

    private static PetDTO convertEntityToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    private Pet convertPetDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
