package com.jatincodes1.springboot.myfirstwebapp.todo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private static List<Todo> todos = new ArrayList<>();



    static {  // this will work first .. when class is made
        todos.add(new Todo(1,"1","a", LocalDate.now().plusYears(1),false));
        todos.add(new Todo(2,"2","b",LocalDate.now().plusYears(1),false));
        todos.add(new Todo(3,"3","c",LocalDate.now().plusYears(1),false));
    }

    public List<Todo> findByUsername(String username){
        return todos;
    }
    //OLD_CODE

}
