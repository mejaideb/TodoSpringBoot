package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.tavisca.workshops.todolist.com.tavisca.workshops.todolist.exception.UserNotFoundException;
@CrossOrigin
@RestController
public class TodoRestController {
	
	@Autowired
	private TodoDao todoDao;
	
	@GetMapping(path="/todo")
	public List<TodoItem> retrieveListOfTodoItems(){
		return todoDao.findAll();
	}
	
	@GetMapping(path="/todo/{id}")
	public TodoItem getItemById(@PathVariable int id){
		TodoItem item= todoDao.findAnItem(id);
		if(item==null)
			throw new UserNotFoundException("id-" +id);
		return item;
		
	}
	
	@PostMapping("/todo")
	public ResponseEntity<?> createNewItem(@RequestBody TodoItem item) {
		TodoItem savedItem=todoDao.save(item);
		
		return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/todo/{id}")
	public void deleteItem(@PathVariable int id){
		
		todoDao.deleteById(id);
	}
	
	@PutMapping("/todo/{itemId}")
	public ResponseEntity<?> updateById(@PathVariable int itemId, @RequestBody TodoItem item) {
		
	    if(todoDao.updateItem(itemId,item))
	                return new ResponseEntity<>(HttpStatus.OK) ;
	               else
	               return new ResponseEntity<>(HttpStatus.NOT_FOUND);

//		item.setId(itemId);
//		todoDao.updateItem(item);
	}
}
