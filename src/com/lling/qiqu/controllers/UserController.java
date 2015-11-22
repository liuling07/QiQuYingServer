package com.lling.qiqu.controllers;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lling.qiqu.commons.ResponseInfo;
import com.lling.qiqu.service.IUserService;

/**
 * @ClassName: UserController
 * @Description: 用户控制器
 * @author lling
 * @date 2015-5-31
 */
/**
 * @ClassName: UserController
 * @Description: 
 * @author lling
 * @date 2015年7月10日
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private IUserService userServie;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 获取七牛token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/get_key_token", method=RequestMethod.GET)
	public ResponseInfo getQiNiuKeyAndToken() {
		logger.info("获取七牛上传key----------------------------");
		return userServie.getQiNiuKeyAndToken();
	}
	
	/**
	 * 腾讯QQ登陆后添加用户信息
	 * @param openId
	 * @param portraitUrl
	 * @param nickName
	 * @param sex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/tencent/add", method=RequestMethod.POST)
	public ResponseInfo tencentAdd(@RequestParam(value = "openId", required = true) String openId,
			@RequestParam(value = "portraitUrl", required = true) String portraitUrl,
			@RequestParam(value = "nickName", required = true) String nickName,
			@RequestParam(value = "sex", required = true) String sex) {
		logger.info("腾讯QQ登录----------------------------openId：" + openId);
		return userServie.addTencentUser(openId, portraitUrl, nickName, sex);
	}
	
	@ResponseBody
	@RequestMapping(value="/sina/add", method=RequestMethod.POST)
	public ResponseInfo sinaAdd(@RequestParam(value = "uid", required = true) String openId,
			@RequestParam(value = "portraitUrl", required = true) String portraitUrl,
			@RequestParam(value = "nickName", required = true) String nickName) {
		logger.info("新浪微博登录----------------------------openId：" + openId);
		return userServie.addSinaUser(openId, portraitUrl, nickName);
	}
	
	/**
	 * 用户注册
	 * @param userName
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public ResponseInfo regist(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {
		logger.info("用户注册----------------------------");
		return userServie.regist(userName, password);
	}
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/exeist", method=RequestMethod.POST)
	public ResponseInfo regist(@RequestParam(value = "phone", required = true) String phone) {
		logger.info("检验用户是否存在----------------------------" + phone);
		return userServie.checkExeist(phone);
	}
	
	/**
	 * 用户登陆
	 * @param userName
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseInfo login(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {
		logger.info("用户登录----------------------------");
		return userServie.login(userName, password);
	}
	
	/**
	 * 设置用户头像
	 * @param userId
	 * @param portraitURL
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/set_portrait", method=RequestMethod.POST)
	public ResponseInfo setPortrait(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "portraitURL", required = true) String portraitURL) {
		logger.info("设置用户头像----------------------------userId:" + userId);
		return userServie.setPortrait(userId, portraitURL);
	}
	
	/**
	 * 设置用户昵称和性别
	 * @param userId
	 * @param nickName
	 * @param sex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/set_nick_sex", method=RequestMethod.POST)
	public ResponseInfo setNickAndSex(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "nickName", required = false) String nickName,
			@RequestParam(value = "sex", required = false) String sex) {
		logger.info("设置用户头像----------------------------userId:" + userId);
		return userServie.setNickAndSex(userId, nickName, sex);
	}
	
	/**
	 * 用户反馈
	 * @param contactWay
	 * @param content
	 * @param imgUrl
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/feedback", method=RequestMethod.POST)
	public ResponseInfo feedBack(@RequestParam(value = "contactWay", required = false) String contactWay,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "imgUrl", required = false) String imgUrl) {
		logger.info("用户提交意见反馈----------------------------");
		return userServie.feedBack(contactWay, content, imgUrl);
	}
	
	/**
	 * 修改密码
	 * @param phone
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/reset_password", method=RequestMethod.POST)
	public ResponseInfo reSetPassword(@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "password", required = true) String password) {
		logger.info("用户重置密码----------------------------" + phone);
		return userServie.resetPassword(phone, password);
	}
	
}
