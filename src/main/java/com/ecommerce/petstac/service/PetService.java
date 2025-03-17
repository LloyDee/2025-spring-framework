package com.ecommerce.petstac.service;

import com.ecommerce.petstac.model.Pet;
import com.ecommerce.petstac.payload.PetDTO;
import com.ecommerce.petstac.payload.PetResponse;


public interface PetService {
    PetDTO addPet(PetDTO petDTO);
    PetResponse getAllPets();
    String deletePet(Long petId);
    void deleteAllPet();
    void editPet(Pet pet, Long petId);
}
