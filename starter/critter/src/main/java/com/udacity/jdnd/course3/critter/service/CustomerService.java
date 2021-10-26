package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.exception.IdNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional  //It is required to simply have @Transactional annotation at the top of every Service class.
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PetRepository petRepository;

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findById(long customerId){
        Optional<Customer> optionalCustomer =  customerRepository.findById(customerId);
        Customer customer = new Customer();
        if(optionalCustomer.isPresent()){
            customer=optionalCustomer.get();
            return  customer;
        }else{
            throw new IdNotFoundException("customer id not found");
        }
    }

    public Customer findByPet(Long petId){
        Optional<Pet> optionalPet = petRepository.findById(petId);
        Pet pet = new Pet();
        if(optionalPet.isPresent()){
            pet = optionalPet.get();
            return customerRepository.findByPetsContaining(pet);
        }else{
            throw new IdNotFoundException("pet id not found");
        }
    }
}
