package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserImages;

@Repository
public interface ImageStorageDao  extends CrudRepository<UserImages, Integer>{

}
