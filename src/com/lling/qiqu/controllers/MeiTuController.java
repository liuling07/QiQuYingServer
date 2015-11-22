package com.lling.qiqu.controllers;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * @ClassName: MeiTuController
 * @Description: 美图控制器
 * @author lling
 * @date 2015-5-30
 */
@Controller
@RequestMapping("/meitu")
public class MeiTuController {
	
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
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ResponseInfo addAPI(@RequestParam(required = true) String title, @RequestParam(required = true) String imgUrl, @RequestParam(required = true) int userId) {
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
	
	/**
	 * 客户端获取美图列表
	 * @param newOrHotFlag 最新最热排序标记
	 * @param offset
	 * @param count
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ResponseInfo getListAPI(@RequestParam(value = "newOrHotFlag", required = false) Integer newOrHotFlag,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(value = "count", required = false, defaultValue = "10") int count) {
		
		if(newOrHotFlag == null) {
			newOrHotFlag = Joke.SORT_NEW;
		}
		
		Limit limit = Limit.buildLimit(offset, count);
		PageResult<Joke> page = jokeService.getJokes(limit, Joke.TYPE_MEITU, newOrHotFlag);
		
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		responseInfo.setData(page);
		return responseInfo;
	}
}
