package com.graphQl.demo.exception;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(){
        super("Book Name Not found");
    }

}
