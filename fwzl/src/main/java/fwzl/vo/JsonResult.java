package fwzl.vo;

import java.io.Serializable;

/**
 * json对象
 * @author 马亮
 */
public class JsonResult implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7086377817781910406L;

	/**
	 * 成功信息
	 */
	private static final int SUCCESS = 1;
	
	/**
	 * 失败信息
	 */
	private static final int ERROR = 0;
	
	
	/**
	 * 消息,成功时为ok，失败时为异常信息
	 */
	private String msg;
	
	
	/**
	 * 状态信息,成功为SUCCESS(1),失败为ERROR(0)
	 */
	private int success;
	
	/**
	 * 对象中封装的数据
	 */
	private Object data;

	/**
	 * 无参构造方法,没有数据需要保存,执行无异常,可以发送成功的json数据
	 */
	public JsonResult() {
		success = SUCCESS;
		msg = "OK";
	}
	
	/**
	 * 有参构造方法,可以保存数据
	 * @param data 需要保存的数据
	 */
	public JsonResult(Object data) {
		this();
		this.data = data;
	}
	
	/**
	 * 有参构造方法,保存异常信息
	 * @param e 需要保存的异常
	 */
	public JsonResult(Throwable e) {
		success = ERROR;
		msg = "ERROR";
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
	

}