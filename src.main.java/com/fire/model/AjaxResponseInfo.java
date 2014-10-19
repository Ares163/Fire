package com.fire.model;

/**
 * The Class AjaxResponseInfo.
 */
public class AjaxResponseInfo {

	/** 状态码. */
	private String code;

	/** 返回信息. */
	private String msg;

	/** 数据 **/
	private Object data;

	public AjaxResponseInfo() {
		super();
	}

	public AjaxResponseInfo(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 获取 状态码.
	 * 
	 * @return the 状态码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 状态码.
	 * 
	 * @param code
	 *            the new 状态码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取 返回信息.
	 * 
	 * @return the 返回信息
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置 返回信息.
	 * 
	 * @param msg
	 *            the new 返回信息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
