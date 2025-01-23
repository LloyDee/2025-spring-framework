package com.ecommerce.petstac.controller;

import com.ecommerce.petstac.exceptions.APIException;
import com.ecommerce.petstac.model.Pet;
import com.ecommerce.petstac.service.PetService;
import jakarta.validation.Valid;
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
        return new ResponseEntity<>(petService.getAllPets(), HttpStatus.OK);
    }

    @PostMapping("api/pets/")
    public ResponseEntity<String> addPet(@Valid @RequestBody Pet pet) {
        petService.addPet(pet);
        return new ResponseEntity<>("Pet "+pet.getName()+" added", HttpStatus.CREATED);
    }

    @DeleteMapping("api/pets/{id}")
    public ResponseEntity<String> deletePet(@PathVariable Long id) {
        String status = petService.deletePet(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @DeleteMapping("api/pets/")
    public ResponseEntity<String> deletePet() {
        petService.deleteAllPet();
        return new ResponseEntity<>("All pets have been deleted", HttpStatus.OK);
    }

    @PutMapping("api/pets/{petId}")
    public ResponseEntity<String> updatePet(@RequestBody Pet pet, @PathVariable Long petId) {
        petService.editPet(pet, petId);
        return new ResponseEntity<>( "Pet ID of " + petId + " has been updated to " + pet.getName(),HttpStatus.OK);
    }
}
