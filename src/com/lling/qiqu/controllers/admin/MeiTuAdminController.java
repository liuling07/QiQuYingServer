package com.lling.qiqu.controllers.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lling.qiqu.beans.Joke;
import com.lling.qiqu.beans.User;
import com.lling.qiqu.commons.Constants;
import com.lling.qiqu.service.IJokeService;
import com.lling.qiqu.service.IUserService;
import com.lling.qiqu.thread.GetImgSizeThread;
import com.lling.qiqu.utils.QiNiuUtil;

/**
 * @ClassName: MeiTuAdminController
 * @Description: 美图管理控制器
 * @author lling
 * @date 2015-5-30
 */
@Controller
@RequestMapping("/meituadmin")
public class MeiTuAdminController {
	
	@Resource
	private IJokeService jokeService;
	
	@Resource
	private IUserService userServie;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 进入添加美图界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add/index", method=RequestMethod.GET)
	public String addIndex(HttpSession session, Model model) {
		User user = (User) session.getAttribute(Constants.KEY_CURR_USER);
		if(user == null) {
			return "admin/login";
		}
		List<User> users = userServie.findAllAdminUsers();
		logger.info("进入添加美图界面");
		model.addAttribute("users", users);
		return "admin/addMeitu";
	}
	
	/**
	 * 添加美图
	 * @param content
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/addmeitu", method=RequestMethod.POST)
	public String add(@RequestParam(required = true) String title, @RequestParam(required = true) String imgUrl, 
			@RequestParam(required = false) Integer isJingXuan, @RequestParam(required = true) int userId,
			@RequestParam(required = false) Integer dingNum) {
		logger.info("添加美图----------------------------start");
		User user = userServie.getUserById(userId);
		Joke joke = new Joke();
		joke.setTitle(title);
		joke.setImgUrl(QiNiuUtil.getWaterMark(imgUrl));
		if(user == null) {
			joke.setUserId(1);
			joke.setUserNike("残剑");
			joke.setPortraitUrl("http://7xixxm.com2.z0.glb.clouddn.com/cc44325c-701b-4e97-8b93-c9187c75218e.jpg");
		} else {
			joke.setUserId(user.getId());
			joke.setUserNike(user.getUserNike());
			joke.setPortraitUrl(user.getPortraitUrl());
		}
		if(isJingXuan != null) {
			joke.setIsJingXuan(isJingXuan);
		} else {
			joke.setIsJingXuan(Joke.NOT_JINGXUAN); //美图默认非精选
		}
		joke.setSupportsNum(dingNum);
		joke.setIsPass(Joke.PASS);
		joke.setType(Joke.TYPE_MEITU);
		joke.setCreateDate(new Date());
		int jokeId = jokeService.addJoke(joke);
		Joke newJoke = jokeService.getJokeById(jokeId);
		new GetImgSizeThread(newJoke, jokeService).start();
		logger.info("添加美图----------------------------end");
		return "admin/addSuccess";
	}
	
}
