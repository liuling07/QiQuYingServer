package com.lling.qiqu.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: User
 * @Description: 用户实体类
 * @author lling
 * @date 2015-5-29
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable {

	private static final long serialVersionUID = -1371659645448329097L;

	public static final int ROLE_USER = 0;
	public static final int ROLE_ADMIN = 1;
	
	/**
	 * 用户id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	/**
	 * 用户名
	 */
	@Column(name = "user_name", unique = true, nullable = true)
	private String userName;
	
	/**
	 * 用户登陆密码
	 */
	@Column(name = "password", nullable = true)
	private String password;
	
	/**
	 * 用户昵称
	 */
	@Column(name = "user_nike", nullable = true)
	private String userNike;
	
	/**
	 * 用户性别(0：女、1：男)
	 */
	@Column(name = "sex", length = 10, columnDefinition = "int default 1")
	private int sex;
	
	/**
	 * 用户头像URL
	 */
	@Column(name = "portrait_url", nullable = true)
	private String portraitUrl;
	
	/**
	 * 是否接受推送消息（0：否、1：是）
	 */
	@Column(name = "is_receive_push", length = 10, columnDefinition = "int default 1")
	private int isReceivePush;
	
	/**
	 * 是否禁用（0：否、1：是）
	 */
	@Column(name = "is_forbid", length = 10, columnDefinition = "int default 0")
	private int isForbid;

	/**
	 * 创建日期
	 */
	@Column(name = "create_date", updatable = false)
	private Date createDate;
	
	/**
	 * 腾讯openId
	 */
	@Column(name = "tecent_open_id", unique = true, nullable = true)
	private String tecentOpenId;
	
	/**
	 * 新浪uid
	 */
	@Column(name = "sina_uid", unique = true, nullable = true)
	private String sinaUid;
	
	/**
	 * 用户角色
	 * 0：普通用户
	 * 1：管理员
	 */
	@Column(name = "role", length = 10, columnDefinition = "int default 0")
	private int role;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserNike() {
		return userNike;
	}

	public void setUserNike(String userNike) {
		this.userNike = userNike;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public int getIsReceivePush() {
		return isReceivePush;
	}

	public void setIsReceivePush(int isReceivePush) {
		this.isReceivePush = isReceivePush;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getIsForbid() {
		return isForbid;
	}

	public void setIsForbid(int isForbid) {
		this.isForbid = isForbid;
	}

	public String getTecentOpenId() {
		return tecentOpenId;
	}

	public void setTecentOpenId(String tecentOpenId) {
		this.tecentOpenId = tecentOpenId;
	}

	public String getSinaUid() {
		return sinaUid;
	}

	public void setSinaUid(String sinaUid) {
		this.sinaUid = sinaUid;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
}
