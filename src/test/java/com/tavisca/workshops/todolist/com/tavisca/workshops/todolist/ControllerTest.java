package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TodoRestController.class)
public class ControllerTest {
	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
		private TodoDao todoDao;
	   

	    private List<TodoItem> mockTodoList;
	    
	    ObjectMapper mapper = new ObjectMapper();
	    
	    @Before
	    public void setUp(){
	        TodoItem todo1=new TodoItem();
	        todo1.setId(1);
	        todo1.setName("Mocking");
	        todo1.setDetails("Mockingdetails");

	        TodoItem todo2=new TodoItem();
	        todo2.setId(2);
	        todo2.setName("Test");
	        todo2.setDetails("Testingdetails");
	        mockTodoList = new ArrayList<TodoItem>();

	        mockTodoList.add(todo1);
	        mockTodoList.add(todo2);
	    }

	    @Test
	    public void getAllTodos() throws Exception {
	    
	    	
	        Mockito.when(todoDao.findAll())
	        .thenReturn(mockTodoList);
	        
	        String jsonResponse = mapper.writeValueAsString(mockTodoList);
	      
	
	         mockMvc.perform(get("/todo"))
	         .andExpect(status().isOk())
	         .andExpect(content().json(jsonResponse))
	        ;
	 }
	    @Test
	    public void testEmptyTodo() throws Exception {
	        List<TodoItem> todos=new ArrayList<>();
	        Mockito.when(todoDao.findAll()).thenReturn(todos);

	        
	        mockMvc.perform(get("/todo"))
	                .andExpect(status().isOk()).andExpect(content().json("[]"));
	    }
	    
	    @Test
	    public void postRequestCreatesAnItem() throws Exception {
	    	TodoItem todoList=new TodoItem();
	        todoList.setId(1);
	        todoList.setName("Mocking");
	        todoList.setDetails("Mockingdetails");
	        
	        List<TodoItem> list = new ArrayList<>();
	        list.add(todoList);
	        
//	        Mockito.when(todoDao.save(todoList)).thenReturn(todoList);
	        Mockito.when(todoDao.findAll()).thenReturn(list);
	        
	        String jsonRequest = mapper.writeValueAsString(todoList);
	        String jsonResponse = mapper.writeValueAsString(list);
	        
	        mockMvc.perform(
	        		post("/todo")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(jsonRequest))
	        		.andExpect(status().isCreated());
	        
	       mockMvc.perform(get("/todo"))
	       .andExpect(status().isOk())
	       .andExpect(content().json(jsonResponse));
	        
	    }
	    
	    @Test
	    public void deleteAnItem() throws Exception {
	        TodoItem item = new TodoItem(10, "Hello","Hi");
	        Mockito.when(todoDao.deleteById(10)).thenReturn(item);
	        mockMvc.perform(MockMvcRequestBuilders.delete("/todo/10")
	        		.accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
	    }
	    
	    @Test
	    public void updateTodoItem() throws Exception {
	    	TodoItem mockTodolist = new TodoItem(10, "do brush","doing brush is mandatory");
	        Mockito.when(todoDao.updateItem(Mockito.anyInt(),Mockito.any(TodoItem.class))).thenReturn(true);
	        RequestBuilder requestBuilder= MockMvcRequestBuilders.put("/todo/10")
	                                      .accept(MediaType.APPLICATION_JSON)
	                                      .content(mapper.writeValueAsString(mockTodolist))
	                                      .contentType(MediaType.APPLICATION_JSON);
	          
	          MvcResult result=mockMvc.perform(requestBuilder).andReturn();
	          MockHttpServletResponse res=result.getResponse();
	          Assert.assertEquals(HttpStatus.OK.value(),res.getStatus());
	    }
	    
	    
}
