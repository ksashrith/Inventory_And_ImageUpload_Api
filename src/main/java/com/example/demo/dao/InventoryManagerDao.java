package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.InventoryManagementStore;

public interface InventoryManagerDao  extends CrudRepository<InventoryManagementStore, Integer>{
	
}
