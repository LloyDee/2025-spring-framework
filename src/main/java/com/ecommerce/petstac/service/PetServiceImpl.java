package com.ecommerce.petstac.service;

import com.ecommerce.petstac.exceptions.APIException;
import com.ecommerce.petstac.exceptions.ResourceNotFoundException;
import com.ecommerce.petstac.model.Pet;
import com.ecommerce.petstac.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    public PetRepository petRepository;

    @Override
    public List<Pet> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        if (pets.isEmpty()) {
            throw new APIException("No pets found");
        } else {
            return petRepository.findAll();
        }
    }

    @Override
    public void addPet(Pet pet) {
        Pet savedPet = petRepository.findByName(pet.getName());
        if (savedPet != null) {
            throw new APIException(pet.getName() + " already exists");
        } else {
            petRepository.save(pet);
        }
    }

    @Override
    public String deletePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        petRepository.delete(pet);
        return "Pet deleted successfully";
    }

    @Override
    public void deleteAllPet() {
       if (petRepository.findAll().isEmpty()) {
           throw new APIException("No pets found");
       }else {
           petRepository.deleteAll();
       }
    }


    @Override
    public void editPet(Pet pet, Long id) {
        petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "Pet ID", id));
        if (petRepository.findById(id).isPresent()) {
            if (petRepository.findByName(pet.getName()) != null) {
                throw new APIException("Pet "+pet.getName()+" name already exists");
            }else {
                petRepository.save(pet);
            }
        }

    }
}
