package com.ecommerce.petstac.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {
    private long petId;

    @NotBlank
    @Size(min = 5, message = "Pet name must contains at least 5 characters")
    private String petName;
    private int petAge;

}
