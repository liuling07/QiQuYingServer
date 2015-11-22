package com.lling.qiqu.dao;

import java.util.Date;
import java.util.List;

import com.lling.qiqu.beans.Joke;
import com.lling.qiqu.commons.Limit;
import com.lling.qiqu.commons.PageResult;


public interface IJokeDAO {
	
	/**
	 * 添加Joke
	 * @param joke
	 */
	public Integer addJoke(Joke joke);
	
	/**
	 * 根据id查找Joke
	 * @param id
	 * @return
	 */
	public Joke getById(int id);
	
	/**
	 * 分页获取joke列表
	 * @param limit 分页对象
	 * @param type  joke类别
	 * @param newOrHotFlag 标记是按最新还是最热排序
	 * @return
	 */
	public PageResult<Joke> getJokes(Limit limit, int type, int newOrHotFlag);
	
	/**
	 * 分页获取joke列表(查询所有类别)
	 * @param limit 分页对象
	 * @param newOrHotFlag 标记是按最新还是最热排序
	 * @return
	 */
	public PageResult<Joke> getJokes(Limit limit, int newOrHotFlag);
	
	/**
	 * 根据用户id列表查找jokes
	 * @param ids
	 * @return
	 */
	public List<Joke> getJokesByIds(String ids);
	
	/**
	 * 批量修改
	 * @param jokes
	 */
	public void updateJokes(List<Joke> jokes);
	
	/**
	 * 修改
	 * @param joke
	 */
	public void updateJoke(Joke joke);
	
	/**
	 * 根据id获取下一条
	 * @param timeStamp
	 */
	public Joke getNextById(Integer id);
	
	/**
	 * 根据id获取上一条
	 * @param timeStamp
	 */
	public Joke getLastById(Integer id);
	
}
