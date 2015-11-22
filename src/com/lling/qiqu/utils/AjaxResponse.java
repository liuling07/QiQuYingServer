/**
 * 
 */
package com.lling.qiqu.utils;


/**
 * ***********************************
 * @author sandy
 * @project aider
 * @create_date 2013年8月22日 上午10:02:07
 * ***********************************
 */
public class AjaxResponse<T> {
	private String msg;
	private String detailMsg;
	private String status;
	private boolean error;
	private T result;

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the error
	 */
	public boolean getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the t
	 */
	public T getResult() {
		return result;
	}

	/**
	 * @param t the t to set
	 */
	public void setResult(T entity) {
		this.result = entity;
	}

	/**
	 * @return the detailMsg
	 */
	public String getDetailMsg() {
		return detailMsg;
	}

	/**
	 * @param detailMsg the detailMsg to set
	 */
	public void setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
	}
	

}
