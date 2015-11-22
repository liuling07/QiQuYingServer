package com.lling.qiqu.service.impl;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lling.qiqu.beans.FeedBack;
import com.lling.qiqu.beans.User;
import com.lling.qiqu.commons.Constants;
import com.lling.qiqu.commons.InitConfig;
import com.lling.qiqu.commons.ResponseInfo;
import com.lling.qiqu.dao.IUserDAO;
import com.lling.qiqu.enums.CodeRespEnum;
import com.lling.qiqu.service.IUserService;
import com.lling.qiqu.thread.DeleteImgThread;
import com.lling.qiqu.utils.MD5;
import com.lling.qiqu.utils.QiNiuUtil;
import com.lling.qiqu.utils.Util;

@Service("userServie")
public class UserServiceImpl implements IUserService {
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private IUserDAO userDAO;
	
	@Override
	public List<User> findAllAdminUsers() {
		return userDAO.findAllAdminUsers();
	}

	@Override
	public User getUserById(int id) {
		return userDAO.getUserById(id);
	}

	@Override
	public ResponseInfo addTencentUser(String openId, String portraitUrl, String nickName, String sex) {
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			if(Util.isNotEmpty(openId)) {
				User user = userDAO.getUserByOpenId(openId);
				if(user != null) {
					user.setPortraitUrl(portraitUrl);
					user.setUserNike(URLDecoder.decode(nickName, "UTF-8"));
					user.setSex(Integer.valueOf(sex));
					userDAO.updateUser(user);
					responseInfo.setData(user);
					responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
					return responseInfo;
				}
			}
			User user = new User();
			user.setPortraitUrl(portraitUrl);
			user.setUserNike(URLDecoder.decode(nickName, "UTF-8"));
			user.setSex(Integer.valueOf(sex));
			user.setUserName(UUID.randomUUID().toString());
			user.setCreateDate(new Date());
			user.setTecentOpenId(openId);
			user.setIsReceivePush(1);
			User newUser = userDAO.getUserById(userDAO.addUser(user));
			responseInfo.setData(newUser);
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
			return responseInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
			return responseInfo;
		} 
	}

	@Override
	public ResponseInfo regist(String userName, String password) {
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			if(Util.isEmpty(userName) || Util.isEmpty(password)) {
				//参数非法
				responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
				return responseInfo;
			}
			User user = userDAO.getUserByUserName(userName);
			if(user != null) {
				//用户名已经存在
				responseInfo.setCode(CodeRespEnum.CODE_SOURCE_EXISTS.getCode());
				return responseInfo;
			}
			//正常注册
			user = new User();
			user.setUserNike("用户"+userName);
			user.setSex(1);
			user.setUserName(userName);
			user.setPassword(MD5.MD5Encode(password));
			user.setIsReceivePush(1);
			user.setIsForbid(0);
			user.setCreateDate(new Date());
			
			User newUser = userDAO.regist(user);  //注册
			if(newUser == null) {
				//注册失败
				responseInfo.setCode(CodeRespEnum.CODE_OTHER_ERROR.getCode());
				return responseInfo;
			}
			
			responseInfo.setData(newUser);
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
			return responseInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//注册失败,服务器异常
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
			return responseInfo;
		}
	}

	@Override
	public ResponseInfo login(String userName, String password) {
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			if(Util.isEmpty(userName) || Util.isEmpty(password)) {
				//参数非法
				responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
				return responseInfo;
			}
			User user = userDAO.getUserByUserName(userName);
			if(user == null) {
				//用户名不存在
				responseInfo.setCode(CodeRespEnum.CODE_NO_SOURCE_EXISTS.getCode());
				return responseInfo;
			}
			
			//用户名存在，验证密码
			if(!MD5.MD5Encode(password).equals(user.getPassword())) {
				//密码错误
				responseInfo.setCode(CodeRespEnum.CODE_OTHER_ERROR.getCode());
				return responseInfo;
			}
			
			responseInfo.setData(user);
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
			return responseInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//登录失败,服务器异常
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
			return responseInfo;
		}
	}

	@Override
	public ResponseInfo getQiNiuKeyAndToken() {
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

	@Override
	public ResponseInfo setPortrait(String userId, String portraitURL) {
		ResponseInfo responseInfo = new ResponseInfo();
		if(Util.isEmpty(userId) || Util.isEmpty(portraitURL)) {
			//参数非法
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
			return responseInfo;
		}
		try {
			User user = userDAO.getUserById(Integer.valueOf(userId));
			if(user == null) {
				//用户不存在
				responseInfo.setCode(CodeRespEnum.CODE_NO_SOURCE_EXISTS.getCode());
				return responseInfo;
			}
			if(Util.isNotEmpty(user.getPortraitUrl())) {
				//删除七牛原有的图片资源
				String url = InitConfig.get("qiniu.bbkurl");
				if(user.getPortraitUrl().startsWith(url)) {
					String key = user.getPortraitUrl().substring(user.getPortraitUrl().lastIndexOf("/")+1);
					new DeleteImgThread(key).start();
				}
			}
			user.setPortraitUrl(portraitURL);
			userDAO.updateUser(user);
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
		}
		return responseInfo;
	}

	@Override
	public ResponseInfo setNickAndSex(String userId, String nickName, String sex) {
		ResponseInfo responseInfo = new ResponseInfo();
		if(Util.isEmpty(userId)) {
			//参数非法
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
			return responseInfo;
		}
		try {
			User user = userDAO.getUserById(Integer.valueOf(userId));
			if(user == null) {
				//用户不存在
				responseInfo.setCode(CodeRespEnum.CODE_NO_SOURCE_EXISTS.getCode());
				return responseInfo;
			}
			if(Util.isNotEmpty(nickName)) {
				user.setUserNike(URLDecoder.decode(nickName, "UTF-8"));
			}
			if(Util.isNotEmpty(sex)) {
				user.setSex(Integer.valueOf(sex));
			}
			userDAO.updateUser(user);
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
		}
		return responseInfo;
	}

	@Override
	public ResponseInfo addSinaUser(String uid, String portraitUrl,
			String nickName) {
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			if(Util.isNotEmpty(uid)) {
				User user = userDAO.getUserByUid(uid);
				if(user != null) {
					user.setPortraitUrl(portraitUrl);
					user.setUserNike(URLDecoder.decode(nickName, "UTF-8"));
					userDAO.updateUser(user);
					responseInfo.setData(user);
					responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
					return responseInfo;
				}
			}
			User user = new User();
			user.setPortraitUrl(portraitUrl);
			user.setUserNike(URLDecoder.decode(nickName, "UTF-8"));
			user.setUserName(UUID.randomUUID().toString());
			user.setIsReceivePush(1);
			user.setCreateDate(new Date());
			user.setSinaUid(uid);
			User newUser = userDAO.getUserById(userDAO.addUser(user));
			responseInfo.setData(newUser);
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
			return responseInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
			return responseInfo;
		} 
	}

	@Override
	public ResponseInfo feedBack(String contactWay, String content,
			String imgUrl) {
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			if(Util.isEmpty(content)) {
				//参数非法
				responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
				return responseInfo;
			}
			FeedBack feedBack = new FeedBack();
			feedBack.setContactWay(contactWay);
			feedBack.setImgUrl(imgUrl);
			feedBack.setContent(URLDecoder.decode(content, "UTF-8"));
			feedBack.setCreateDate(new Date());
			userDAO.addFeedBack(feedBack);
			
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
			return responseInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//反馈失败,服务器异常
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
			return responseInfo;
		}
	}

	@Override
	public ResponseInfo checkExeist(String phone) {
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			if(Util.isEmpty(phone) || !Util.isPhone(phone)) {
				//参数非法
				responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
				return responseInfo;
			}
			User user = userDAO.getUserByUserName(phone);
			if(user != null) {
				//用户名已经存在
				responseInfo.setCode(CodeRespEnum.CODE_SOURCE_EXISTS.getCode());
				return responseInfo;
			}
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
			return responseInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//注册失败,服务器异常
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
			return responseInfo;
		}
	}

	@Override
	public ResponseInfo resetPassword(String phone, String password) {
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			if(Util.isEmpty(phone) || !Util.isPhone(phone)) {
				//参数非法
				responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
				return responseInfo;
			}
			User user = userDAO.getUserByUserName(phone);
			if(user == null) {
				//用户名不存在
				responseInfo.setCode(CodeRespEnum.CODE_NO_SOURCE_EXISTS.getCode());
				return responseInfo;
			}
			user.setPassword(MD5.MD5Encode(password));
			userDAO.updateUser(user);
			responseInfo.setData(user);
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
			return responseInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//注册失败,服务器异常
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
			return responseInfo;
		}
	}

	@Override
	public ResponseInfo adminLogin(String userName, String password, HttpSession session) {
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			if(Util.isEmpty(userName) || Util.isEmpty(password)) {
				//参数非法
				responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
				return responseInfo;
			}
			User user = userDAO.getUserByUserName(userName);
			if(user == null) {
				//用户名不存在
				responseInfo.setCode(CodeRespEnum.CODE_NO_SOURCE_EXISTS.getCode());
				return responseInfo;
			}
			if(user.getRole() == User.ROLE_ADMIN) {//用户是管理员
				//用户名存在，验证密码
				if(!MD5.MD5Encode(password).equals(user.getPassword())) {
					//密码错误
					responseInfo.setCode(CodeRespEnum.CODE_OTHER_ERROR.getCode());
					return responseInfo;
				}
				responseInfo.setData(user);
				responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
				//将当前用户存储到session中
				session.setAttribute(Constants.KEY_CURR_USER, user);
				return responseInfo;
			} else {  //非管理员
				responseInfo.setCode(CodeRespEnum.CODE_OTHER_ERROR.getCode());
				return responseInfo;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//登录失败,服务器异常
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
			return responseInfo;
		}
	}
	
	
}
