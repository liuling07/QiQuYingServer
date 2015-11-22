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
 * @ClassName: FeedBack
 * @Description: 用户意见反馈实体类
 * @author lling
 * @date 2015年7月10日
 */
@Entity
@Table(name = "t_feedback")
public class FeedBack implements Serializable {

	private static final long serialVersionUID = -1371659645448329097L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	/**
	 * 联系方式
	 */
	@Column(name = "contact_way", nullable = true)
	private String contactWay;
	
	/**
	 * 反馈内容
	 */
	@Column(name = "content", nullable = false)
	private String content;
	
	/**
	 * 图片路径
	 */
	@Column(name = "img_url", nullable = true)
	private String imgUrl;
	
	/**
	 * 创建日期
	 */
	@Column(name = "create_date", updatable = false)
	private Date createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
