package com.example.demo.TodoRepository;
import com.example.demo.Model.Todo;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {


}
