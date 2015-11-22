package com.lling.qiqu.controllers;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.lling.qiqu.utils.Util;

/**
 * @ClassName: QuShiController
 * @Description: 趣事控制器
 * @author lling
 * @date 2015-5-30
 */
@Controller
@RequestMapping("/qushi")
public class QuShiController {
	
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
	 * 客户端获取趣事列表
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
		logger.info("获取趣事----------------------------");
		if(newOrHotFlag == null) {
			newOrHotFlag = Joke.SORT_NEW;
		}
		
		Limit limit = Limit.buildLimit(offset, count);
		PageResult<Joke> page = jokeService.getJokes(limit, Joke.TYPE_QUSHI, newOrHotFlag);
		
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		responseInfo.setData(page);
		return responseInfo;
	}
	
	/**
	 * 客户端获取所有列表
	 * @param newOrHotFlag 最新最热排序标记
	 * @param offset
	 * @param count
	 * @return
	 */
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
	}
	
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
	
	@ResponseBody
	@RequestMapping(value="/ding", method=RequestMethod.POST)
	public ResponseInfo dingJoke(@RequestParam(value = "ids", required = true) String ids) {
		logger.info("用户顶----------------------------，ids:" + ids);
		return jokeService.ding(ids);
	}
	
	@ResponseBody
	@RequestMapping(value="/cai", method=RequestMethod.POST)
	public ResponseInfo caiJoke(@RequestParam(value = "ids", required = true) String ids) {
		logger.info("用户踩----------------------------，ids:" + ids);
		return jokeService.cai(ids);
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
	 * 查看详情内容
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
	public String getById(@PathVariable Integer id ,Model model) {
		logger.info("根据id查看详情----------------------------jokeId:" + id);
		Joke joke = jokeService.getJokeById(id);
		model.addAttribute("joke", joke);
		List<Comment> comments = jokeService.getCommentsByJokeId(id);
		model.addAttribute("comments", comments);
		return "qushiShow";
	}
	
	
	@RequestMapping(value="/detail/next/{id}", method=RequestMethod.GET)
	public String getNext(@PathVariable Integer id ,Model model) {
		logger.info("查看下一条----------------------------jokeId:" + id);
		Joke joke = jokeService.getNextById(id);
		if(joke == null) {
			joke = jokeService.getJokeById(id);   //如果超出边界了，直接返回本条
		}
		if(joke != null) {
			List<Comment> comments = jokeService.getCommentsByJokeId(joke.getId());
			model.addAttribute("comments", comments);
		}
		model.addAttribute("joke", joke);
		return "qushiShow";
	}
	
	@RequestMapping(value="/detail/last/{id}", method=RequestMethod.GET)
	public String getLast(@PathVariable Integer id ,Model model) {
		logger.info("查看上一条----------------------------jokeId:" + id);
		Joke joke = jokeService.getLastById(id);
		if(joke == null) {
			joke = jokeService.getJokeById(id);   //如果超出边界了，直接返回本条
		}
		if(joke != null) {
			List<Comment> comments = jokeService.getCommentsByJokeId(joke.getId());
			model.addAttribute("comments", comments);
		}
		model.addAttribute("joke", joke);
		return "qushiShow";
	}
	
	@RequestMapping(value="/index")
	public String getLast(Model model) {
		PageResult<Joke> page;
		Limit limit = Limit.buildLimit(1, 30);
		page = jokeService.getJokes(limit, Joke.TYPE_QUSHI, Joke.SORT_NEW);
		model.addAttribute("page", page);
		model.addAttribute("type", "qushi");
		return "qushi";
	}
	
	
}
