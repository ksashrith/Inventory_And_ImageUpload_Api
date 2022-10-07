package com.example.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ImageStorageDao;
import com.example.demo.model.UserImages;
import com.example.demo.service.InventoryAndImageService;

@Service
public class InventoryAndImageServiceImpl implements InventoryAndImageService {

	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	ImageStorageDao dao;

	@Override
	public String uploadImage(String name, MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		UserImages user = new UserImages();
		user.setUserName(name);
		user.setImageFile(fileName);
		UserImages createdUser = dao.save(user);
		String uploadDir = "user_images/" + createdUser.getUserName() + "_" + createdUser.getID() + "_";
		Path uploadDirectory = Paths.get(uploadDir);
		if (!Files.exists(uploadDirectory)) {
			Files.createDirectories(uploadDirectory);
		}

		Files.copy(file.getInputStream(), uploadDirectory.resolve(fileName));
		return uploadDirectory.resolve(fileName).toString();
	}

	@Override
	public InputStream displayImage(String username, int id) throws FileNotFoundException {
		Optional<UserImages> getUser = dao.findById(id);
		UserImages user = getUser.get();
		String fullPath = "user_images/" + File.separator + user.getUserName() + "_" + user.getID() + "_"
				+ File.separator + user.getImageFile();
		InputStream stream = new FileInputStream(fullPath);
		return stream;
	}

	
}
