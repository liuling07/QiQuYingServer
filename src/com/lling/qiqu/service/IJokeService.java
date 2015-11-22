package com.lling.qiqu.service;

import java.util.List;

import com.lling.qiqu.beans.Comment;
import com.lling.qiqu.beans.Joke;
import com.lling.qiqu.commons.Limit;
import com.lling.qiqu.commons.PageResult;
import com.lling.qiqu.commons.ResponseInfo;


public interface IJokeService {
	
	/**
	 * 添加Joke
	 * @param joke
	 */
	public Integer addJoke(Joke joke);
	
	/**
	 * 根据id获取joke对象
	 * @param id
	 * @return
	 */
	public Joke getJokeById(int id);
	
	/**
	 * 分页获取joke列表
	 * @param limit 分页对象
	 * @param type  joke类别
	 * @param newOrHotFlag 标记是按最新还是最热排序
	 * @return
	 */
	public PageResult<Joke> getJokes(Limit limit, int type, int newOrHotFlag);
	
	/**
	 * 分页获取joke列表（获取所有类别）
	 * @param limit 分页对象
	 * @param newOrHotFlag 标记是按最新还是最热排序
	 * @return
	 */
	public PageResult<Joke> getJokes(Limit limit, int newOrHotFlag);
	
	/**
	 * 根据JokeId分页获取评论列表
	 * @param limit
	 * @param jokeId
	 * @return
	 */
	public PageResult<Comment> getCommentsByJokeId(Limit limit, int jokeId);
	
	/**
	 * 根据JokeId获取所有评论列表
	 * @param jokeId
	 * @return
	 */
	public List<Comment> getCommentsByJokeId(int jokeId);
	
	/**
	 * 用户顶
	 * @param ids
	 * @return
	 */
	public ResponseInfo ding(String ids);
	
	/**
	 * 用户踩
	 * @param ids
	 * @return
	 */
	public ResponseInfo cai(String ids);
	
	/**
	 * 根据JokeId获取Joke
	 * @param jokeId
	 * @return
	 */
	public ResponseInfo getJokeByIdAPI(Integer jokeId);
	
	/**
	 * 用户评论
	 * @param jokeId
	 * @param userId
	 * @param userPortrait
	 * @param userNick
	 * @param content
	 * @return
	 */
	public ResponseInfo addComment(Integer jokeId, Integer userId, String userPortrait,
			String userNick, String content);
	
	/**
	 * 修改
	 * @param joke
	 */
	public void update(Joke joke);
	
	/**
	 * 根据id获取下一条
	 * @param id
	 * @return
	 */
	public Joke getNextById(Integer id);
	
	/**
	 * 根据id获取上一条
	 * @param id
	 * @return
	 */
	public Joke getLastById(Integer id);
}
