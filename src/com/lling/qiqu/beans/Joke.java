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
 * @ClassName: Joke
 * @Description: 笑话实体类
 * @author lling
 * @date 2015-5-28
 */
@Entity
@Table(name = "t_joke")
public class Joke implements Serializable{

	private static final long serialVersionUID = 4646260119535165219L;

	//type标记
	public static final int TYPE_QUSHI = 1;
	public static final int TYPE_QUTU = 2;
	public static final int TYPE_MEITU = 3;
	public static final int TYPE_GIF = 4;
	
	//是否通过审核标记
	public static final int NOT_PASS = 0;
	public static final int PASS = 1;
	
	//是否被删除标记
	public static final int NOT_DELETE = 0;
	public static final int DELETE = 1;
	
	//最新最热排序标记
	public static final int SORT_NEW = 0;
	public static final int SORT_HOT = 1;
	
	//最新最热排序标记
	public static final int NOT_JINGXUAN = 0;
	public static final int JINGXUAN = 1;
	
	/**
	 * 笑话id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	/**
	 * 笑话标题
	 */
	@Column(name = "title", nullable = true)
	private String title;
	
	/**
	 * 笑话内容
	 */
	@Column(name = "content", nullable = true)
	private String content;
	
	/**
	 * 图片地址
	 */
	@Column(name = "img_url", nullable = true)
	private String ImgUrl;
	
	/**
	 * gif地址
	 */
	@Column(name = "gif_url", nullable = true)
	private String GifUrl;
	
	/**
	 * 笑话支持数
	 */
	@Column(name = "support_num", length = 10, columnDefinition = "int default 0")
	private int supportsNum;
	
	/**
	 * 笑话反对数
	 */
	@Column(name = "oppose_num", length = 10, columnDefinition = "int default 0")
	private int opposesNum;
	
	/**
	 * 笑话评论数
	 */
	@Column(name = "comment_num", length = 10, columnDefinition = "int default 0")
	private int commentNum;
	
	/**
	 * 笑话创建日期
	 */
	@Column(name = "create_date", updatable = false)
	private Date createDate;
	
	/**
	 * 是否删除标记（0：未删除、1：已删除）
	 */
	@Column(name = "is_delete", length = 10, columnDefinition = "int default 0")
	private int isDelete;
	
	/**
	 * 笑话类别（1：趣事、2：趣图、3：美图、4：gif）
	 */
	@Column(name = "type", length = 10, columnDefinition = "int default 1")
	private int type;
	
	/**
	 * 是否审核通过（0：未通过、1：已通过）
	 */
	@Column(name = "is_pass", length = 10, columnDefinition = "int default 0")
	private int isPass;
	
	/**
	 * 发表用户
	 */
	@Column(name = "user_id", length = 10)
	private int userId;
	
	/**
	 * 发表用户头像
	 */
	@Column(name = "portrait_url", nullable = true)
	private String portraitUrl;

	/**
	 * 用户昵称
	 */
	@Column(name = "user_nike", nullable = true)
	private String userNike;
	
	/**
	 * 图片宽度
	 */
	@Column(name = "img_width", length = 10, columnDefinition = "int default 0")
	private int imgWidth;
	
	/**
	 * 图片高度
	 */
	@Column(name = "img_height", length = 10, columnDefinition = "int default 0")
	private int imgHeight;
	
	/**
	 * 是否精选内容
	 * 0：否
	 * 1:是
	 */
	@Column(name = "is_jingxuan", length = 10, columnDefinition = "int default 0")
	private int isJingXuan;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return ImgUrl;
	}

	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}

	public String getGifUrl() {
		return GifUrl;
	}

	public void setGifUrl(String gifUrl) {
		GifUrl = gifUrl;
	}

	public int getSupportsNum() {
		return supportsNum;
	}

	public void setSupportsNum(int supportsNum) {
		this.supportsNum = supportsNum;
	}

	public int getOpposesNum() {
		return opposesNum;
	}

	public void setOpposesNum(int opposesNum) {
		this.opposesNum = opposesNum;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsPass() {
		return isPass;
	}

	public void setIsPass(int isPass) {
		this.isPass = isPass;
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

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	public int getIsJingXuan() {
		return isJingXuan;
	}

	public void setIsJingXuan(int isJingXuan) {
		this.isJingXuan = isJingXuan;
	}
	
}
