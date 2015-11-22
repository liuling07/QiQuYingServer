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
import com.lling.qiqu.commons.Limit;
import com.lling.qiqu.commons.PageResult;
import com.lling.qiqu.service.IJokeService;
import com.lling.qiqu.service.IUserService;

/**
 * @ClassName: QuShiAdminController
 * @Description: 管理趣事控制器
 * @author lling
 * @date 2015-8-6
 */
@Controller
@RequestMapping("/qushiadmin")
public class QuShiAdminController {
	
	@Resource
	private IJokeService jokeService;
	
	@Resource
	private IUserService userServie;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 进入添加趣事界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model, @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(value = "count", required = false, defaultValue = "10") int count) {
		Limit limit = Limit.buildLimit(offset, count);
		PageResult<Joke> pageResult = jokeService.getJokes(limit, Joke.TYPE_QUSHI, Joke.SORT_NEW);
		model.addAttribute("pageResult", pageResult);
		return "admin/qushiList";
	}
	
	/**
	 * 进入添加趣事界面
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
		logger.info("进入添加趣事界面");
		model.addAttribute("users", users);
		return "admin/addQushi";
	}
	
	/**
	 * 添加趣事
	 * @param content
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/addqushi", method=RequestMethod.POST)
	public String add(@RequestParam(required = true) String content,
			@RequestParam(required = false) Integer isJingXuan, @RequestParam(required = true) int userId,
			@RequestParam(required = false) Integer dingNum) {
		logger.info("添加趣事----------------------------start");
		User user = userServie.getUserById(userId);
		Joke joke = new Joke();
		joke.setContent(content);
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
			joke.setIsJingXuan(Joke.JINGXUAN); //默认是精选
		}
		joke.setSupportsNum(dingNum);
		joke.setIsPass(Joke.PASS);
		joke.setType(Joke.TYPE_QUSHI);
		joke.setCreateDate(new Date());
		jokeService.addJoke(joke);
		logger.info("添加趣事----------------------------end");
		return "admin/addSuccess";
	}
	
}
