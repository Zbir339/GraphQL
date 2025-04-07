package com.graphQl.demo.exception;

public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException(){
        super("Author Not found");
    }
}
