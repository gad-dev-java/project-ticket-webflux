package com.ticketmaster.user.domain.exception;

public class GenreNotFoundException extends RuntimeException{
    public GenreNotFoundException(String message){
        super(message);
    }
}
