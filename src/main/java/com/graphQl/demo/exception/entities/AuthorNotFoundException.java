package com.graphQl.demo.exception.entities;

public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException(){
        super("Author Not found");
    }
}
