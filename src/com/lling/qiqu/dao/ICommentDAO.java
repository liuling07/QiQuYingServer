package com.lling.qiqu.dao;


import java.util.List;

import com.lling.qiqu.beans.Comment;
import com.lling.qiqu.commons.Limit;
import com.lling.qiqu.commons.PageResult;


public interface ICommentDAO {

	/**
	 * 根据jokeid分页获取评论列表
	 * @param limit
	 * @param jokeId
	 * @return
	 */
	public PageResult<Comment> getCommentsByJokeId(Limit limit, int jokeId);
	
	/**
	 * 根据jokeid获取评论列表
	 * @param jokeId
	 * @return
	 */
	public List<Comment> getCommentsByJokeId(int jokeId);
	
	/**
	 * 添加评论
	 * @param comment
	 */
	public Integer addComment(Comment comment);
	
}
