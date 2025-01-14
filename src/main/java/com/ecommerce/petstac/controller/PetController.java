package com.ecommerce.petstac.controller;

import com.ecommerce.petstac.model.Pet;
import com.ecommerce.petstac.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("api/pets/")
    public ResponseEntity<List<Pet>> getPets() {
        try {
            petService.getAllPets();
            return new ResponseEntity<>(petService.getAllPets(), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(petService.getAllPets(), e.getStatusCode());
        }
    }

    @PostMapping("api/pets/")
    public ResponseEntity<String> addPet(@RequestBody Pet pet) {
        try {
            petService.addPet(pet);
            return new ResponseEntity<>(pet.getName() + " added successfully", HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(pet.getName() + " already exist!", e.getStatusCode());
        }
    }

    @DeleteMapping("api/pets/{id}")
    public ResponseEntity<String> deletePet(@PathVariable Long id) {
        try {
            petService.deletePet(id);
            return new ResponseEntity<>("Pet deleted", HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping("api/pets/")
    public ResponseEntity<String> deletePet() {
        try {
            petService.deleteAllPet();
            return new ResponseEntity<>("Pet deleted", HttpStatus.OK);
        }catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("api/pets/{petId}")
    public ResponseEntity<String> updatePet(@RequestBody Pet pet, @PathVariable Long petId) {
        try {
            petService.editPet(pet, petId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
