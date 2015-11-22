/**
 * 
 */
package com.lling.qiqu.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Jon Chiang
 * @project video
 * @create_date 2014-5-7 下午3:42:15
 */
public class AjaxUtil {

    /**
      * 直接打印异常到页面 统一用json格式 {error:true,status:'500',msg:'授权失败',entity:{}} 	
      * @author Jon Chiang
      * @create_date 2014-5-7 下午3:45:32
      * @param tx
      * @param msg
      * @param response
      * @throws IOException
     */
	public static void ajaxError(Throwable tx,String msg,HttpServletResponse response) throws IOException{
		AjaxResponse<String> ajaxResponse = new AjaxResponse<String>();
		ajaxResponse.setError(true);
		ajaxResponse.setMsg(msg);
		ajaxResponse.setDetailMsg(tx.getMessage());
		ajaxResponse.setStatus("500");
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().write(JsonUtil.objectToJson(ajaxResponse));
	}
	/**
	 * 返回ajax需要格式的json字符串 用于@ResponseBody
	 * @author Jon Chiang
	 * @create_date 2014-5-7 下午3:45:33
	 * @param tx
	 * @param msg
	 * @param response
	 * @return 
	 * @throws IOException
	 */
	public static String ajaxError(Throwable tx,String msg) throws IOException{
		AjaxResponse<String> ajaxResponse = new AjaxResponse<String>();
		ajaxResponse.setError(true);
		ajaxResponse.setMsg(msg);
		ajaxResponse.setDetailMsg(tx.getMessage());
		ajaxResponse.setStatus("500");
		return JsonUtil.objectToJson(ajaxResponse);
	}
	/**
	 * 返回ajax需要格式的json字符串 用于@ResponseBody
	 * @author Jon Chiang
	 * @create_date 2014-5-7 下午3:45:33
	 * @param tx
	 * @param msg
	 * @param response
	 * @return 
	 * @throws IOException
	 */
	public static String ajaxFail(String msg){
		AjaxResponse<String> ajaxResponse = new AjaxResponse<String>();
		ajaxResponse.setError(true);
		ajaxResponse.setMsg(msg);
		ajaxResponse.setStatus("500");
		return JsonUtil.objectToJson(ajaxResponse);
	}
	/**
	 * 返回ajax需要格式的json字符串 用于@ResponseBody
	 * @author Jon Chiang
	 * @create_date 2014-5-7 下午3:45:33
	 * @param tx
	 * @param msg
	 * @param response
	 * @return 
	 * @throws IOException
	 */
	public static String ajaxResult(String msg){
		AjaxResponse<String> ajaxResponse = new AjaxResponse<String>();
		ajaxResponse.setError(false);
		ajaxResponse.setMsg(msg);
		ajaxResponse.setStatus("200");
		return JsonUtil.objectToJson(ajaxResponse);
	}
	/**
	 * 返回ajax需要格式的json字符串 用于@ResponseBody
	 * @author Jon Chiang
	 * @create_date 2014-5-7 下午3:45:33
	 * @param tx
	 * @param msg
	 * @param response
	 * @return 
	 * @throws IOException
	 */
	public static String ajaxSuccess(AjaxResponse<?> ajaxResponse) throws IOException{
		ajaxResponse.setError(false);
		ajaxResponse.setStatus("200");
		return JsonUtil.objectToJson(ajaxResponse);
	}
	/**
	  * @author Jon Chiang
	  * @create_date 2014-7-2 下午5:59:20
	  * @return
	  */
	public static String ajaxReLogin() {
		AjaxResponse<String> ajaxResponse = new AjaxResponse<String>();
		ajaxResponse.setError(false);
		ajaxResponse.setMsg("请重新登录");
		ajaxResponse.setStatus("302");
		return JsonUtil.objectToJson(ajaxResponse);
	}
	/**
	  * @author Jon Chiang
	  * @create_date 2014-7-2 下午7:05:49
	  * @param response
	 * @throws IOException 
	  */
	public static void ajaxReLogin(HttpServletResponse response) throws IOException {
		AjaxResponse<String> ajaxResponse = new AjaxResponse<String>();
		ajaxResponse.setError(true);
		ajaxResponse.setMsg("请重新登录");
		ajaxResponse.setStatus("302");
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().write(JsonUtil.objectToJson(ajaxResponse));
	}
}
