package com.example.demo.Model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "todoitem")
@Data
public class Todo {
    private String text;
    private Boolean done;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // constructor

    public Todo(Integer id, String text, Boolean done) {
        this.id = id ;
        this.text =text ;
        this.done = done;
    }

    public Todo() {

    }
}
