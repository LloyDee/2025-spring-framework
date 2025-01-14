package com.ecommerce.petstac.service;

import com.ecommerce.petstac.model.Pet;
import com.ecommerce.petstac.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {
    Long id = 1L;

    @Autowired
    private PetRepository petRepository;

    @Override
    public void addPet(Pet pet) {
        pet.setId(id++);
        if (petExist(pet)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            petRepository.save(pet);
        }
    }

    private boolean petExist(Pet pet) {
        for (Pet value : petRepository.findAll()) {
            String petName = pet.getName();
            if (value.getName().equals(petName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public void deleteAllPet() {
        for (int i = 0; i < petRepository.findAll().size(); i++) {
            petRepository.deleteAll();
        }
        throw new ResponseStatusException(HttpStatus.OK, "All pets have been deleted");
    }

    @Override
    public void deletePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found"));

        petRepository.delete(pet);
        throw new ResponseStatusException(HttpStatus.OK, pet.getName() + " has been deleted");

    }

    @Override
    public void editPet(Pet pet, Long id) {
        petRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found"));

        pet.setId(id);
        petRepository.save(pet);
        throw new ResponseStatusException(HttpStatus.OK, "Pet ID of " + id + " has been updated to " + pet.getName());

    }
}
