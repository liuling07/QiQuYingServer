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
 * @ClassName: Collection
 * @Description: 笑话收藏表
 * @author lling
 * @date 2015-5-29
 */
@Entity
@Table(name = "t_collection")
public class Collection implements Serializable {

	private static final long serialVersionUID = -5997663925418625105L;
	
	/**
	 * 收藏id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	/**
	 * 收藏用户id
	 */
	@Column(name = "user_id", length = 10)
	private int userId;
	
	/**
	 * 收藏笑话id
	 */
	@Column(name = "joke_id", length = 10)
	private int jokeId;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_date", updatable = false)
	private Date createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getJokeId() {
		return jokeId;
	}

	public void setJokeId(int jokeId) {
		this.jokeId = jokeId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
