package com.lling.qiqu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.lling.qiqu.beans.User;
import com.lling.qiqu.commons.ResponseInfo;

public interface IUserService {
	
	/**
	 * 查询所有管理员用户
	 * @return
	 */
	public List<User> findAllAdminUsers();
	
	/**
	 * 根据用户id查找用户
	 */
	public User getUserById(int userId);
	
	
	/**
	 * 添加腾讯QQ登录的用户
	 * @param openId
	 * @param portraitUrl
	 * @param nickName
	 * @param sex
	 */
	public ResponseInfo addTencentUser(String openId, String portraitUrl, String nickName, String sex);
	
	/**
	 * 添加新浪微博登录的用户
	 * @param uid
	 * @param portraitUrl
	 * @param nickName
	 * @return
	 */
	public ResponseInfo addSinaUser(String uid, String portraitUrl, String nickName);
	/**
	 * 用户注册
	 * @param userName
	 * @param password
	 * @param nickName
	 * @return
	 */
	public ResponseInfo regist(String userName, String password);
	
	/**
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public ResponseInfo login(String userName, String password);
	
	/**
	 * 获取七牛上传token
	 * @return
	 */
	public ResponseInfo getQiNiuKeyAndToken();
	
	/**
	 * 设置用户头像
	 * @param userId
	 * @param portraitURL
	 * @return
	 */
	public ResponseInfo setPortrait(String userId, String portraitURL);
	
	/**
	 * 设置用户昵称和性别
	 * @param userId
	 * @param nickName
	 * @param sex
	 * @return
	 */
	public ResponseInfo setNickAndSex(String userId, String nickName, String sex);
	
	/**
	 * 用户提交意见反馈
	 * @param contactWay
	 * @param content
	 * @param imgUrl
	 * @return
	 */
	public ResponseInfo feedBack(String contactWay, String content, String imgUrl);
	
	/**
	 * 检验用户是否存在
	 * @param phone
	 * @return
	 */
	public ResponseInfo checkExeist(String phone);
	
	/**
	 * 重置密码
	 * @param phone
	 * @param password
	 * @return
	 */
	public ResponseInfo resetPassword(String phone, String password);
	
	/**
	 * 管理员用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public ResponseInfo adminLogin(String userName, String password, HttpSession session);
}
