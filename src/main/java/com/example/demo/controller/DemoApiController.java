package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ImageStorageDao;
import com.example.demo.dao.InventoryManagerDao;
import com.example.demo.model.ImageStorageResponse;
import com.example.demo.model.InventoryManagementResponse;
import com.example.demo.model.InventoryManagementStore;
import com.example.demo.service.InventoryAndImageService;

//import com.example.imagestorage.Dao.ImageStorageDao;
//import com.example.imagestorage.model.ImageStorageResponse;
//import com.example.imagestorage.model.UserImages;

@RestController
public class DemoApiController {

	@Autowired
	ImageStorageDao imageStorageDao;

	@Autowired
	InventoryManagerDao inventoryDao;

	@Autowired
	InventoryAndImageService service;

	@PostMapping("/upload/userName/{name}")
	public ResponseEntity<ImageStorageResponse> imageUpload(@RequestParam("image") MultipartFile imageFile,
			@PathVariable("name") String userName) {
		String fileName;
		try {
			fileName = service.uploadImage(userName, imageFile);
			ImageStorageResponse response = new ImageStorageResponse(true, fileName, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			ImageStorageResponse response = new ImageStorageResponse(false, null, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			ImageStorageResponse response = new ImageStorageResponse(false, null, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/getImage/{username}/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(@PathVariable("username") String username, @PathVariable("id") int id,
			HttpServletResponse response) throws IOException {
		InputStream is = service.displayImage(username, id);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(is, response.getOutputStream());
	}

	@PostMapping(value = "/upload/item", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InventoryManagementResponse> uploadItem(@RequestBody InventoryManagementStore request) {
		try {
			if(request.getQuantity()<1) {
				request.setStatus("Empty");
				request.setAvailable(false);
			}
			else {
				request.setStatus("Available");
				request.setAvailable(true);
			}
			InventoryManagementStore item = inventoryDao.save(request);
			InventoryManagementResponse response = new InventoryManagementResponse(true, item, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			InventoryManagementResponse response = new InventoryManagementResponse(false, null, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PutMapping(value = "/update-inventory/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InventoryManagementResponse> updateItem(@RequestBody InventoryManagementStore request) {
		try {
			if(request.getQuantity()<1) {
				request.setStatus("Empty");
				request.setAvailable(false);
			}
			else {
				request.setStatus("Available");
				request.setAvailable(true);
			}
			InventoryManagementStore item = inventoryDao.save(request);
			InventoryManagementResponse response = new InventoryManagementResponse(true, item, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			InventoryManagementResponse response = new InventoryManagementResponse(false, null, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@PutMapping(value = "/update-item-status/{id}/{status}")
	public ResponseEntity<InventoryManagementResponse> updateStatus(@PathVariable ("id") int id, @PathVariable ("status")  String status) {
		try {
			
			Optional<InventoryManagementStore> item = inventoryDao.findById(id);
			InventoryManagementStore updateItem= item.get();
			updateItem.setAvailable(status.equals("available")? true: false);
			 updateItem= inventoryDao.save(updateItem);
			InventoryManagementResponse response = new InventoryManagementResponse(true, updateItem, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			InventoryManagementResponse response = new InventoryManagementResponse(false, null, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/get-all-data")
	public ResponseEntity<List<InventoryManagementStore>> getAllInventoryData(){
		try {
			List<InventoryManagementStore> responseList= (List<InventoryManagementStore>) inventoryDao.findAll();
			return new ResponseEntity<>(responseList, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/get-stock/{id}")
	public ResponseEntity<Integer> getStockOfItem(@PathVariable ("id") int id){
		try {
			Optional<InventoryManagementStore> item = inventoryDao.findById(id);
			InventoryManagementStore getItem= item.get();
			return new ResponseEntity<>(getItem.getQuantity(), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value = "/delete-inventory/{id}")
	public ResponseEntity<HttpStatus> updateItem(@PathVariable ("id") int id) {
		try {
			Optional<InventoryManagementStore> item = inventoryDao.findById(id);
			if(item.isPresent()) {
				inventoryDao.deleteById(id);
			}
			return new ResponseEntity<HttpStatus>( HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>( HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
