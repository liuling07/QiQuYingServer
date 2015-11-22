package com.lling.qiqu.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lling.qiqu.commons.InitConfig;
import com.lling.qiqu.commons.ResponseInfo;
import com.lling.qiqu.enums.CodeRespEnum;
import com.lling.qiqu.utils.AjaxResponse;
import com.lling.qiqu.utils.AjaxUtil;
import com.lling.qiqu.utils.QiNiuUtil;

/**
 * @ClassName: UploadController
 * @Description: 七牛上传控制器
 * @author lling
 * @date 2015-5-31
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 获取七牛上传的token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/token", method = {RequestMethod.GET})
	public ResponseInfo gettoken(){
		ResponseInfo responseInfo = new ResponseInfo();
		try{
			String rskey = UUID.randomUUID().toString()+".jpg";
			String token = QiNiuUtil.getQiniuToken(rskey, true);
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
			Map<String, String> data = new HashMap<String, String>();
			data.put("key", rskey);
			data.put("token", token);
			responseInfo.setData(data);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
		}		
		return responseInfo;
	}
	
	@ResponseBody
	@RequestMapping(value = "/qutu", method = {RequestMethod.POST})
	public String uploadQuTu(@RequestParam(required = true) MultipartFile fileToUpload) throws IOException{
		if(fileToUpload == null) {
			logger.info("上传图片失败！图片为空。");
			return AjaxUtil.ajaxFail("上传图片为空");
		}
		try{
			String fileName = fileToUpload.getOriginalFilename();
			String extention = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			if(!".jpg".equals(extention.toLowerCase()) && !".jpeg".equals(extention.toLowerCase()) 
					&& !".pne".equals(extention.toLowerCase())) {
				logger.info("上传图片失败！非图片内容");
				return AjaxUtil.ajaxFail("非图片内容");
			}
			String rskey = UUID.randomUUID().toString() + extention;
			QiNiuUtil.upload2Stream(rskey, fileToUpload.getInputStream(), true);
			logger.info("上传图片成功");
			AjaxResponse<String> ajaxResponse = new AjaxResponse<>();
			ajaxResponse.setResult(InitConfig.get("qiniu.bbkurl")+rskey);
			ajaxResponse.setMsg("上传成功");
			return AjaxUtil.ajaxSuccess(ajaxResponse);
		}catch(Exception e){
			logger.error("上传图片" + e.getMessage(), e);
			return AjaxUtil.ajaxError(e, "服务器异常");
		}		
	} 
	
	/**
	 * 上传gif
	 * @param fileToUpload
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/gif", method = {RequestMethod.POST})
	public String uploadGIF(@RequestParam(required = true) MultipartFile fileToUpload) throws IOException{
		if(fileToUpload == null) {
			logger.info("上传gif失败！gif图片为空。");
			return AjaxUtil.ajaxFail("上传gif为空");
		}
		try{
			String fileName = fileToUpload.getOriginalFilename();
			String extention = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			if(!".gif".equals(extention.toLowerCase())) {
				logger.info("上传gif失败！非gif内容");
				return AjaxUtil.ajaxFail("非gif内容");
			}
			String rskey = UUID.randomUUID().toString() + extention;
			QiNiuUtil.upload2Stream(rskey, fileToUpload.getInputStream(), true);
			
			logger.info("上传gif成功");
			AjaxResponse<String> ajaxResponse = new AjaxResponse<>();
			ajaxResponse.setResult(InitConfig.get("qiniu.bbkurl")+rskey);
			ajaxResponse.setMsg("上传成功");
			return AjaxUtil.ajaxSuccess(ajaxResponse);
		}catch(Exception e){
			logger.error("上传gif" + e.getMessage(), e);
			return AjaxUtil.ajaxError(e, "服务器异常");
		}		
	} 
	
	/**
	 * 上传美图
	 * @param fileToUpload
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/meitu", method = {RequestMethod.POST})
	public String uploadMeiTu(@RequestParam(required = true) MultipartFile fileToUpload) throws IOException{
		return uploadQuTu(fileToUpload);
	} 
}
