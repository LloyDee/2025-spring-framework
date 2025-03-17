package com.ecommerce.petstac.service;

import com.ecommerce.petstac.exceptions.APIException;
import com.ecommerce.petstac.exceptions.ResourceNotFoundException;
import com.ecommerce.petstac.model.Pet;
import com.ecommerce.petstac.payload.PetDTO;
import com.ecommerce.petstac.payload.PetResponse;
import com.ecommerce.petstac.repository.PetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PetResponse getAllPets() {
        List<Pet> pets = petRepository.findAll();
        if (pets.isEmpty()) {
            throw new APIException("No pets found");
        } else {
            List<PetDTO> petDTOS = pets.stream()
                    .map(pet -> modelMapper.map(pet, PetDTO.class))
                    .toList();

            PetResponse petResponse = new PetResponse();
            petResponse.setContent(petDTOS);
            return petResponse;
        }
    }

    @Override
    public PetDTO addPet(PetDTO petDTO) {
        Pet savedPetFromDB = petRepository.findByName(petDTO.getPetName());
        if (savedPetFromDB != null) {
            throw new APIException(petDTO.getPetName() + " already exists");
        } else {

            Pet pet = new Pet();
            pet.setName(petDTO.getPetName());
            pet.setAge(petDTO.getPetAge());

            Pet savedPet =  petRepository.save(pet);

            return new PetDTO(
                    savedPet.getPetId(),
                    savedPet.getName(),
                    savedPet.getAge()
            );
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
        } else {
            petRepository.deleteAll();
        }
    }


    @Override
    public void editPet(Pet pet, Long id) {
        petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "Pet ID", id));
        if (petRepository.findById(id).isPresent()) {
            if (petRepository.findByName(pet.getName()) != null) {
                throw new APIException("Pet " + pet.getName() + " name already exists");
            } else {
                pet.setName(pet.getName().trim());
                petRepository.save(pet);
            }
        }

    }
}
