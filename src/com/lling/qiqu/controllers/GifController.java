package com.lling.qiqu.controllers;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.lling.qiqu.utils.Util;

/**
 * @ClassName: GifController
 * @Description: gif控制器
 * @author lling
 * @date 2015-5-30
 */
@Controller
@RequestMapping("/gif")
public class GifController {
	
	@Resource
	private IJokeService jokeService;
	
	@Resource
	private IUserService userServie;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 进入添加gif界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add/index", method=RequestMethod.GET)
	public String addIndex(Model model) {
		List<User> users = userServie.findAllAdminUsers();
		logger.info("进入添加gif界面");
		model.addAttribute("users", users);
		return "gifAdd";
	}
	
	/**
	 * 添加趣图
	 * @param content
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/addgif", method=RequestMethod.POST)
	public String add(@RequestParam String title, @RequestParam(required = true) String imgUrl, @RequestParam(required = true) int userId) {
		logger.info("添加gif----------------------------start");
		User user = userServie.getUserById(userId);
		Joke joke = new Joke();
		joke.setTitle(title);
		joke.setGifUrl(imgUrl);
		if(user == null) {
			joke.setUserId(1);
			joke.setUserNike("残剑");
			joke.setPortraitUrl("http://7xixxm.com2.z0.glb.clouddn.com/cc44325c-701b-4e97-8b93-c9187c75218e.jpg");
		} else {
			joke.setUserId(user.getId());
			joke.setUserNike(user.getUserNike());
			joke.setPortraitUrl(user.getPortraitUrl());
		}
		joke.setIsPass(Joke.PASS);
		joke.setType(Joke.TYPE_GIF);
		joke.setCreateDate(new Date());
		jokeService.addJoke(joke);
		logger.info("添加gif----------------------------end");
		return "addSuccess";
	}
	
	/**
	 * 客户端添加gif访问接口
	 * @param content
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ResponseInfo addAPI(@RequestParam(required = true) String title, @RequestParam(required = true) String imgUrl, @RequestParam(required = true) int userId) {
		logger.info("添加gif接口----------------------------start");
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
		joke.setGifUrl(imgUrl);
		joke.setIsPass(Joke.NOT_PASS);
		joke.setType(Joke.TYPE_GIF);
		joke.setCreateDate(new Date());
		jokeService.addJoke(joke);
		logger.info("添加gif接口----------------------------end");
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		return responseInfo;
	}
	
	/**
	 * 客户端获取趣图列表
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
		PageResult<Joke> page = jokeService.getJokes(limit, Joke.TYPE_GIF, newOrHotFlag);
		
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		responseInfo.setData(page);
		return responseInfo;
	}
}
