package com.lling.qiqu.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lling.qiqu.beans.FeedBack;
import com.lling.qiqu.beans.User;
import com.lling.qiqu.dao.IUserDAO;
import com.lling.qiqu.dao.base.BaseDAO;

@Transactional
@Repository("userDAO")
public class UserDAOImpl extends BaseDAO implements IUserDAO{

	@Override
	public User getUserById(int id) {
		return super.getUniqueResult("from User where id=?", id);
	}

	@Override
	public List<User> findAllAdminUsers() {
		return super.find("from User where role=?", User.ROLE_ADMIN);
	}

	@Override
	public Integer addUser(User user) {
		return (Integer)super.save(user);
	}

	@Override
	public User getUserByOpenId(String openId) {
		return super.getUniqueResult("from User where tecentOpenId=?", openId);
	}
	
	@Override
	public User getUserByUid(String uid) {
		return super.getUniqueResult("from User where sinaUid=?", uid);
	}
	
	@Override
	public void updateUser(User user) {
		super.update(user);
	}

	@Override
	public User getUserByUserName(String userName) {
		return super.getUniqueResult("from User where userName=?", userName);
	}

	@Override
	public User regist(User user) {
		Integer userId = (Integer)super.save(user);
		if(userId == null) {
			return null;
		}
		return getUserById(userId);
	}

	@Override
	public void addFeedBack(FeedBack feedBack) {
		super.save(feedBack);
	}

}
