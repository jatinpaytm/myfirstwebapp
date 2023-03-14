package com.jatincodes1.springboot.myfirstwebapp.todo;

/**
 *
 * we have use todorepository of Jparepository interfae
 * to implement update , delete and insert operations
 * instead of todoservice used in TodoController.java
 *
 *
 */

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {
    public TodoControllerJpa(/*TodoService todoService,*/TodoRepository todoRepository) {  // autowired using constructor
        super();
        //this.todoService = todoService;
        this.todoRepository = todoRepository;
    }

    //private TodoService todoService;

    private TodoRepository todoRepository;


    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        String username = getLoggedinUsername(model);
        List<Todo> todos = todoRepository.findByUsername(username);
        model.addAttribute("todos", todos);

        return "listTodos";
    }

    //GET, POST
    @RequestMapping(value="add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model) {
        // added the below code to set values to default .. if we dont add any values
        String username = getLoggedinUsername(model);
        Todo todo = new Todo(0, username, "enter description", LocalDate.now().plusYears(1), false);
        model.put("todo", todo);
        return "todo";
    }

    @RequestMapping(value="add-todo", method = RequestMethod.POST)
    public String addNewTodo(ModelMap model, @Valid Todo todo , BindingResult result) {   // we have done 2-way binding here .. refer section 7 .. step 22 -> udemy video
        if(result.hasErrors()){  // this is done to display error on the page itself .. so that links does not break
            return "todo";
        }
        String username = getLoggedinUsername(model);
        todo.setUsername(username);
        todoRepository.save(todo);
        // Upper 2 lines will do the same as below 2 lines
        /*
        todoService.addTodo(username, todo.getDescription(),
                todo.getTargetDate(), todo.isDone());   // todo.getTargetDate() takes date from user
         */
        return "redirect:list-todos";
    }

    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id) { // need requestParam to capture "delete-todo?id=${todo.id}"
        //Delete todo
        todoRepository.deleteById(id);
        //todoService.deleteById(id);
        return "redirect:list-todos";

    }

    @RequestMapping(value="update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) { // this will only show the update todo page
        Todo todo = todoRepository.findById(id).get();
        //Todo todo = todoService.findById(id);
        model.addAttribute("todo", todo);
        return "todo";
    }

    @RequestMapping(value="update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {  // this will update the todo

        if(result.hasErrors()) {
            return "todo";
        }

        String username = getLoggedinUsername(model);
        todo.setUsername(username);
        todoRepository.save(todo);
        //todoService.updateTodo(todo);
        return "redirect:list-todos";
    }

    // removing hardcoding of User id
    private String getLoggedinUsername(ModelMap model){  // this methods is implemented in section 7 .. step 32
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
