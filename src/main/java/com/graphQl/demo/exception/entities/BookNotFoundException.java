package com.graphQl.demo.exception.entities;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(){
        super("Book Name Not found");
    }

}
