package com.example.demo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface InventoryAndImageService {

	public String uploadImage(String name, MultipartFile file) throws IOException;
	
	public InputStream displayImage(String username, int id) throws FileNotFoundException;
}
