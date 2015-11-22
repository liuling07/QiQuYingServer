package com.lling.qiqu.controllers;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lling.qiqu.beans.Comment;
import com.lling.qiqu.beans.Joke;
import com.lling.qiqu.beans.User;
import com.lling.qiqu.commons.Limit;
import com.lling.qiqu.commons.PageResult;
import com.lling.qiqu.commons.ResponseInfo;
import com.lling.qiqu.enums.CodeRespEnum;
import com.lling.qiqu.service.IJokeService;
import com.lling.qiqu.service.IUserService;
import com.lling.qiqu.thread.GetImgSizeThread;
import com.lling.qiqu.utils.Util;

/**
 * @ClassName: QuShiController
 * @Description: 接口访问控制器
 * @author lling
 * @date 2015-7-26
 */
@Controller
@RequestMapping("/api")
public class APIController {
	
	@Resource
	private IJokeService jokeService;
	
	@Resource
	private IUserService userServie;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	/**
	 * 客户端添加趣事访问接口
	 * @param content
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/qushi/add", method=RequestMethod.POST)
	public ResponseInfo addAPI(@RequestParam(required = true) String content, @RequestParam(required = true) int userId) {
		logger.info("添加趣事接口----------------------------start");
		ResponseInfo responseInfo = new ResponseInfo();
		if(Util.isEmpty(content)) {
			//参数无效
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
			return responseInfo;
		}
		
		Joke joke = new Joke();
		User user = userServie.getUserById(userId);
		if(user == null) {
			//用户不存在，使用默认用户
			joke.setUserId(1);
			joke.setUserNike("残剑");
			joke.setPortraitUrl("http://7xixxm.com2.z0.glb.clouddn.com/cc44325c-701b-4e97-8b93-c9187c75218e.jpg");
		} else {
			joke.setUserId(user.getId());
			joke.setUserNike(user.getUserNike());
			joke.setPortraitUrl(user.getPortraitUrl());
		}
		joke.setContent(content);
		joke.setIsPass(Joke.NOT_PASS);
		joke.setType(Joke.TYPE_QUSHI);
		joke.setCreateDate(new Date());
		jokeService.addJoke(joke);
		logger.info("添加趣事接口----------------------------end");
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		return responseInfo;
	}
	
	/**
	 * 客户端获取所有/趣事/趣图/美图列表
	 * @param newOrHotFlag 最新最热排序标记
	 * @param offset
	 * @param count
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ResponseInfo getListAPI(@RequestParam(value = "newOrHotFlag", required = false) Integer newOrHotFlag,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(value = "count", required = false, defaultValue = "10") int count) {
		logger.info("获取列表----------------------------");
		if(newOrHotFlag == null) {
			newOrHotFlag = Joke.SORT_NEW;
		}
		PageResult<Joke> page;
		Limit limit = Limit.buildLimit(offset, count);
		if(type == null) {
			page = jokeService.getJokes(limit, newOrHotFlag);
		} else {
			page = jokeService.getJokes(limit, type, newOrHotFlag);
		}
		
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		responseInfo.setData(page);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return responseInfo;
	}
	
	/**
	 * 客户端获取所有列表
	 * @param newOrHotFlag 最新最热排序标记
	 * @param offset
	 * @param count
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public ResponseInfo getAllListAPI(@RequestParam(value = "newOrHotFlag", required = false) Integer newOrHotFlag,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(value = "count", required = false, defaultValue = "10") int count) {
		logger.info("获取所有----------------------------");
		if(newOrHotFlag == null) {
			newOrHotFlag = Joke.SORT_NEW;
		}
		
		Limit limit = Limit.buildLimit(offset, count);
		PageResult<Joke> page = jokeService.getJokes(limit, newOrHotFlag);
		
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		responseInfo.setData(page);
		return responseInfo;
	}*/
	
	/**
	 * 客户端获取趣事评论
	 * @param qushiId
	 * @param offset
	 * @param count
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/comments", method=RequestMethod.GET)
	public ResponseInfo getCommentsAPI(@RequestParam(value = "qushiId", required = true) int qushiId,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(value = "count", required = false, defaultValue = "10") int count) {
		logger.info("获取趣事评论----------------------------，id:" + qushiId);
		Limit limit = Limit.buildLimit(offset, count);
		PageResult<Comment> page = jokeService.getCommentsByJokeId(limit, qushiId);
		
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		responseInfo.setData(page);
		return responseInfo;
	}
	
	/**
	 * 顶
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ding", method=RequestMethod.POST)
	public ResponseInfo dingJoke(@RequestParam(value = "ids", required = true) String ids) {
		logger.info("用户顶----------------------------，ids:" + ids);
		return jokeService.ding(ids);
	}
	
	/**
	 * 踩
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/cai", method=RequestMethod.POST)
	public ResponseInfo caiJoke(@RequestParam(value = "ids", required = true) String ids) {
		logger.info("用户踩----------------------------，ids:" + ids);
		return jokeService.cai(ids);
	}
	
	/**
	 * 根据jokeId获取joke对象
	 * @param jokeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/get_joke", method=RequestMethod.GET)
	public ResponseInfo getjokeById(@RequestParam(value = "jokeId", required = true) Integer jokeId) {
		logger.info("获取Joke----------------------------，jokeId:" + jokeId);
		return jokeService.getJokeByIdAPI(jokeId);
	}
	
	/**
	 * 用户评论接口
	 * @param jokeId
	 * @param userId
	 * @param userPortrait
	 * @param userNick
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/comment", method=RequestMethod.POST)
	public ResponseInfo addCommentAPI(@RequestParam(value = "jokeId", required = true) Integer jokeId,
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "userPortrait", required = false) String userPortrait,
			@RequestParam(value = "userNick", required = true) String userNick,
			@RequestParam(value = "content", required = true) String content) {
		logger.info("用户评论----------------------------，jokeId:" + jokeId);
		return jokeService.addComment(jokeId, userId, userPortrait, userNick, content);
	}
	
	/**
	 * 客户端添加趣图访问接口
	 * @param content
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/qutu/add", method=RequestMethod.POST)
	public ResponseInfo addAPI(@RequestParam(required = true) String title, @RequestParam(required = true) String imgUrl, @RequestParam(required = true) int userId) {
		logger.info("添加趣图接口----------------------------start");
		ResponseInfo responseInfo = new ResponseInfo();
		if(Util.isEmpty(imgUrl)) {
			//参数无效
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
			return responseInfo;
		}
		
		Joke joke = new Joke();
		User user = userServie.getUserById(userId);
		if(user == null) {
			//用户不存在，使用默认用户
			joke.setUserId(1);
			joke.setUserNike("残剑");
			joke.setPortraitUrl("http://7xixxm.com2.z0.glb.clouddn.com/cc44325c-701b-4e97-8b93-c9187c75218e.jpg");
		} else {
			joke.setUserId(user.getId());
			joke.setUserNike(user.getUserNike());
			joke.setPortraitUrl(user.getPortraitUrl());
		}
		joke.setTitle(title);
		joke.setImgUrl(imgUrl);
		joke.setIsPass(Joke.NOT_PASS);
		joke.setType(Joke.TYPE_QUTU);
		joke.setCreateDate(new Date());
		int jokeId = jokeService.addJoke(joke);
		Joke newJoke = jokeService.getJokeById(jokeId);
		new GetImgSizeThread(newJoke, jokeService).start();
		logger.info("添加趣图接口----------------------------end");
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		return responseInfo;
	}
	
	/**
	 * 客户端添加趣事访问接口
	 * @param content
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ResponseInfo addMeiTuAPI(@RequestParam(required = true) String title, @RequestParam(required = true) String imgUrl, @RequestParam(required = true) int userId) {
		logger.info("添加美图接口----------------------------start");
		ResponseInfo responseInfo = new ResponseInfo();
		if(Util.isEmpty(imgUrl)) {
			//参数无效
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
			return responseInfo;
		}
		
		Joke joke = new Joke();
		User user = userServie.getUserById(userId);
		if(user == null) {
			//用户不存在，使用默认用户
			joke.setUserId(1);
			joke.setUserNike("残剑");
			joke.setPortraitUrl("http://7xixxm.com2.z0.glb.clouddn.com/cc44325c-701b-4e97-8b93-c9187c75218e.jpg");
		} else {
			joke.setUserId(user.getId());
			joke.setUserNike(user.getUserNike());
			joke.setPortraitUrl(user.getPortraitUrl());
		}
		joke.setTitle(title);
		joke.setImgUrl(imgUrl);
		joke.setIsPass(Joke.NOT_PASS);
		joke.setType(Joke.TYPE_MEITU);
		joke.setCreateDate(new Date());
		int jokeId = jokeService.addJoke(joke);
		Joke newJoke = jokeService.getJokeById(jokeId);
		new GetImgSizeThread(newJoke, jokeService).start();
		logger.info("添加美图接口----------------------------end");
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		return responseInfo;
	}
}
