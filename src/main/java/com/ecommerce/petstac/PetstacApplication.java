package com.ecommerce.petstac;

import com.ecommerce.petstac.model.Pet;
import com.ecommerce.petstac.repository.PetRepository;
import com.ecommerce.petstac.service.PetService;
import com.ecommerce.petstac.service.PetServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetstacApplication {

    public static void main(String[] args) {

       SpringApplication.run(PetstacApplication.class, args);
    }

}
