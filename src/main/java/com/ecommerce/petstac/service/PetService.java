package com.ecommerce.petstac.service;

import com.ecommerce.petstac.model.Pet;

import java.util.List;

public interface PetService {
    void addPet(Pet pet);
    List<Pet> getAllPets();
    void deletePet(Long petId);
    void deleteAllPet();
    void editPet(Pet pet, Long petId);
}
