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
 * @ClassName: Comment
 * @Description: 笑话评论表
 * @author lling
 * @date 2015-5-29
 */
@Entity
@Table(name = "t_comment")
public class Comment implements Serializable {

	private static final long serialVersionUID = -8116039067400484215L;

	/**
	 * 评论id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	/**
	 * 评论笑话id
	 */
	@Column(name = "joke_id", length = 10)
	private int jokeId;
	
	/**
	 * 评论内容
	 */
	@Column(name = "content", nullable = false)
	private String content;
	
	/**
	 * 评论支持数
	 */
	@Column(name = "support_num", length = 10, columnDefinition = "int default 0")
	private int supportsNum;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_date", updatable = false)
	private Date createDate;
	
	/**
	 * 评论用户id
	 */
	@Column(name = "user_id", length = 10)
	private int userId;
	
	/**
	 * 评论用户头像
	 */
	@Column(name = "portrait_url", nullable = true)
	private String portraitUrl;

	/**
	 * 评论用户昵称
	 */
	@Column(name = "user_nike", nullable = true)
	private String userNike;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJokeId() {
		return jokeId;
	}

	public void setJokeId(int jokeId) {
		this.jokeId = jokeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSupportsNum() {
		return supportsNum;
	}

	public void setSupportsNum(int supportsNum) {
		this.supportsNum = supportsNum;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public String getUserNike() {
		return userNike;
	}

	public void setUserNike(String userNike) {
		this.userNike = userNike;
	}
	
}
