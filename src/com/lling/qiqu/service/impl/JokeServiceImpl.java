package com.lling.qiqu.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lling.qiqu.beans.Comment;
import com.lling.qiqu.beans.Joke;
import com.lling.qiqu.commons.Limit;
import com.lling.qiqu.commons.PageResult;
import com.lling.qiqu.commons.ResponseInfo;
import com.lling.qiqu.dao.ICommentDAO;
import com.lling.qiqu.dao.IJokeDAO;
import com.lling.qiqu.enums.CodeRespEnum;
import com.lling.qiqu.service.IJokeService;
import com.lling.qiqu.utils.Util;

@Service("jokeServie")
public class JokeServiceImpl implements IJokeService {
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private IJokeDAO jokeDAO;
	
	@Resource
	private ICommentDAO commentDAO;
	
	@Override
	public Integer addJoke(Joke joke) {
		return jokeDAO.addJoke(joke);
	}

	@Override
	public PageResult<Joke> getJokes(Limit limit, int type, int newOrHotFlag) {
		return jokeDAO.getJokes(limit, type, newOrHotFlag);
	}
	
	@Override
	public PageResult<Joke> getJokes(Limit limit, int newOrHotFlag) {
		return jokeDAO.getJokes(limit, newOrHotFlag);
	}

	@Override
	public PageResult<Comment> getCommentsByJokeId(Limit limit, int jokeId) {
		return commentDAO.getCommentsByJokeId(limit, jokeId);
	}
	
	@Override
	public List<Comment> getCommentsByJokeId(int jokeId) {
		return commentDAO.getCommentsByJokeId(jokeId);
	}

	@Override
	public ResponseInfo ding(String ids) {
		ResponseInfo responseInfo = new ResponseInfo();
		if(Util.isEmpty(ids)) {
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
			return responseInfo;
		} 
		List<Joke> jokes = jokeDAO.getJokesByIds(ids);
		if(Util.isNotEmpty(jokes)) {
			for (Joke joke : jokes) {
				joke.setSupportsNum(joke.getSupportsNum() + 1);
			}
			jokeDAO.updateJokes(jokes);
		}
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		return responseInfo;
	}
	
	@Override
	public ResponseInfo cai(String ids) {
		ResponseInfo responseInfo = new ResponseInfo();
		if(Util.isEmpty(ids)) {
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
			return responseInfo;
		} 
		List<Joke> jokes = jokeDAO.getJokesByIds(ids);
		if(Util.isNotEmpty(jokes)) {
			for (Joke joke : jokes) {
				joke.setOpposesNum(joke.getOpposesNum() + 1);
			}
			jokeDAO.updateJokes(jokes);
		}
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		return responseInfo;
	}

	@Override
	public ResponseInfo addComment(Integer jokeId, Integer userId,
			String userPortrait, String userNick, String content) {
		ResponseInfo responseInfo = new ResponseInfo();
		if(jokeId == null || userId == null) {
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
			return responseInfo;
		} 
		Joke joke = jokeDAO.getById(jokeId);
		if(joke == null) {
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
			return responseInfo;
		}
		try {
			Comment comment = new Comment();
			comment.setContent(URLDecoder.decode(content, "UTF-8"));
			comment.setJokeId(jokeId);
			comment.setUserId(userId);
			comment.setPortraitUrl(userPortrait);
			comment.setUserNike(URLDecoder.decode(userNick, "UTF-8"));
			comment.setSupportsNum(0);
			comment.setCreateDate(new Date());
			Integer commentId = commentDAO.addComment(comment);
			
			//更新评论数
			joke.setCommentNum(joke.getCommentNum() + 1);
			jokeDAO.updateJoke(joke);
			
			responseInfo.setData(commentId);
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
			return responseInfo;
		} catch (UnsupportedEncodingException e) {
			logger.error("评论失败----------------------------", e);
			responseInfo.setCode(CodeRespEnum.CODE_SERVER_EXCEPTION.getCode());
			return responseInfo;
		}
	}

	@Override
	public Joke getJokeById(int id) {
		return jokeDAO.getById(id);
	}

	@Override
	public void update(Joke joke) {
		jokeDAO.updateJoke(joke);
	}

	@Override
	public ResponseInfo getJokeByIdAPI(Integer jokeId) {
		ResponseInfo responseInfo = new ResponseInfo();
		if(jokeId == null) {
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
			return responseInfo;
		} 
		Joke joke = jokeDAO.getById(jokeId);
		if(joke == null) {
			responseInfo.setCode(CodeRespEnum.CODE_NO_SOURCE_EXISTS.getCode());
			return responseInfo;
		}
		responseInfo.setData(joke);
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		return responseInfo;
	}

	@Override
	public Joke getNextById(Integer id) {
		return jokeDAO.getNextById(id);
	}

	@Override
	public Joke getLastById(Integer id) {
		return jokeDAO.getLastById(id);
	}

}
