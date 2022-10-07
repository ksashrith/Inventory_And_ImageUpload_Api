package com.example.demo.model;

public class ImageStorageResponse {
	private boolean success;
	private String storage_path;
	public String getStorage_path() {
		return storage_path;
	}
	public void setStorage_path(String storage_path) {
		this.storage_path = storage_path;
	}
	private String errorMessage;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public ImageStorageResponse(boolean success, String storage_path, String errorMessage) {
		super();
		this.success = success;
		this.storage_path = storage_path;
		this.errorMessage = errorMessage;
	}
	
}
