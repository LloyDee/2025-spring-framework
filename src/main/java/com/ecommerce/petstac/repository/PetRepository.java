package com.ecommerce.petstac.repository;

import com.ecommerce.petstac.model.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Pet findByName(@NotBlank @Size(min = 2, message = "Name must contain more than 2 characters") String name);
}
