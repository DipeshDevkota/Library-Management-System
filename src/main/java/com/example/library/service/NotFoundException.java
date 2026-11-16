package com.example.library.service;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String type, Long id) {
        super(type + " with id " + id + " was not found");
    }
}
