package com.example.demo.model;

public class InventoryManagementResponse {
	private boolean success;
	private InventoryManagementStore data;
	
	

	public InventoryManagementStore getData() {
		return data;
	}
	public void setData(InventoryManagementStore data) {
		this.data = data;
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
	
	
	public InventoryManagementResponse(boolean success,InventoryManagementStore data, String errorMessage) {
		super();
		this.success = success;
		this.data = data;
		this.errorMessage = errorMessage;
	}
	
}
