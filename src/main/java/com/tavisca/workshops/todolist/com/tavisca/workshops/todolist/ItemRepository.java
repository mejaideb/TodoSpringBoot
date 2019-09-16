package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<TodoItem, Integer> {

}
