package com.example.demo.Exception;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(String todoItemNotFound) {
    }
}
