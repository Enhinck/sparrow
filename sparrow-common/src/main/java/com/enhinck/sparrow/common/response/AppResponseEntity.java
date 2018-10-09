package com.enhinck.sparrow.common.response;

import java.io.Serializable;

public class AppResponseEntity implements Serializable {
	private static final long serialVersionUID = 5600732461682124950L;

	private int errorCode;

	private String errorMessage;

	private Object data;

	private transient String jsonpFunction;

	public AppResponseEntity(Object data) {
		this.data = (data == null ? "" : data);
	}

	public AppResponseEntity(int errorCode, String errorMessage, Object data) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.data = data;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getJsonpFunction() {
		return jsonpFunction;
	}

	public void setJsonpFunction(String jsonpFunction) {
		this.jsonpFunction = jsonpFunction;
	}
}
