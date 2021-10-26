package com.udacity.jdnd.course3.critter.exception;


public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(String message){
        super(message);
    }
}
