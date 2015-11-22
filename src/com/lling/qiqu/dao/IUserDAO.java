package com.lling.qiqu.dao;

import java.util.List;

import com.lling.qiqu.beans.FeedBack;
import com.lling.qiqu.beans.User;

/**
 * @ClassName: IUserDAO
 * @Description: 用户DAO类
 * @author lling
 * @date 2015-5-30
 */
public interface IUserDAO {
	
	/**
	 * 根据用户id查找用户对象
	 * @param id
	 * @return
	 */
	public User getUserById(int id);
	
	/**
	 *  根据用户openid查找用户对象
	 * @param openId
	 * @return
	 */
	public User getUserByOpenId(String openId);
	
	/**
	 * 根据用户新浪uid查找用户对象
	 * @param uid
	 * @return
	 */
	public User getUserByUid(String uid);
	
	/**
	 * 根据用户名查找用户对象
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName);
	
	/**
	 * 查找所有管理员用户
	 * @return
	 */
	public List<User> findAllAdminUsers();
	
	/**
	 * 添加用户
	 * @param user
	 */
	public Integer addUser(User user);
	
	/**
	 * 用户修改
	 * @param user
	 * @return
	 */
	public void updateUser(User user);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public User regist(User user);
	
	/**
	 * 意见反馈
	 * @param feedBack
	 */
	public void addFeedBack(FeedBack feedBack);
}
