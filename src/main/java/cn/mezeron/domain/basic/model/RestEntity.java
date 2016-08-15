package cn.mezeron.domain.basic.model;

public class RestEntity<T> {
	
	private String status;
	private String errMsg;
	T data;
	
	public RestEntity() {
		this.status = "ok";
		this.errMsg = "";
	}
	
	public RestEntity<T> ok(T data) {
		this.status = "ok";
		this.errMsg = "";
		this.data = data;
		return this;
	}
	
	public RestEntity<T> fail(String errMsg) {
		this.status = "fail";
		this.errMsg = errMsg;
		return this;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
