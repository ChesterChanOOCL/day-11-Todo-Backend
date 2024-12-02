package com.example.demo.TodoService;

import com.example.demo.Model.Todo;
import com.example.demo.TodoRepository.TodoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodo(){
        return todoRepository.findAll();
    }
    public Todo getTodoById(Integer todoId){
        return todoRepository.findById(todoId).orElse(null);
    }

    public Todo create(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo update(Integer id, Todo todo)  {
        Todo existingTodo = todoRepository.findById(id).orElse(null);
        System.out.println(id);
        if(existingTodo != null ){
            existingTodo.setText(todo.getText());
            existingTodo.setDone(todo.getDone());
            return todoRepository.save(existingTodo);
        }
    return existingTodo;
    }


    public void delete(Integer todoId) {
        todoRepository.deleteById(todoId);
    }
}
