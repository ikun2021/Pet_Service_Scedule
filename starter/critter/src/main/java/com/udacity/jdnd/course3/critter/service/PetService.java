package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.exception.IdNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Pet save(Pet pet){
        pet = petRepository.save(pet);
        if(pet.getCustomer()!=null){
            Customer customer = pet.getCustomer();
            List<Pet> pets = customer.getPets();
            if(pets!=null){
                pets.add(pet);
            }else{
                pets = new ArrayList<>();
                pets.add(pet);
            }
            customer.setPets(pets);
            customerRepository.save(customer);
        }
        return pet;
    }

    public Pet findById(Long petId){
        Optional<Pet> optionalPet =  petRepository.findById(petId);
        Pet pet = new Pet();
        if(optionalPet.isPresent()){
            pet=optionalPet.get();
            return pet;
        }else{
            throw new IdNotFoundException("pet id not found");
        }
    }

    public List<Pet> findAll(){
        return petRepository.findAll();
    }

    public List<Pet> findAllByCustomerId(Long customerId){
        return petRepository.findAllByCustomerId(customerId);
    }


}
