package com.example.demo.TodoController;

import com.example.demo.Exception.TodoNotFoundException;
import com.example.demo.TodoService.TodoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Model.Todo;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/todo")
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodo();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Todo create(@RequestBody Todo todo) {
        return todoService.create(todo);
    }
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Todo update(@PathVariable Integer id, @RequestBody Todo todo) {
            return todoService.update(id, todo);
    }

    @PutMapping("/toggle/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Todo toggle(@PathVariable Integer id){
        Todo todo = todoService.getTodoById(id);
        return todoService.toggle(id, todo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        todoService.delete(id);
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Integer id){
        return todoService.getTodoById(id);
    }

}