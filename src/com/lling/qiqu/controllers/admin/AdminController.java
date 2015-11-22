package com.lling.qiqu.controllers.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lling.qiqu.beans.User;
import com.lling.qiqu.commons.Constants;
import com.lling.qiqu.commons.ResponseInfo;
import com.lling.qiqu.service.IUserService;

/**
 * @ClassName: AdminController
 * @Description: 管理界面控制器
 * @author lling
 * @date 2015-8-4
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Resource
	private IUserService userServie;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 进入添加趣事界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String admin(HttpSession session, Model model) {
		logger.info("进入管理界面");
		User user = (User) session.getAttribute(Constants.KEY_CURR_USER);
		if(user == null) {
			return "admin/login";
		}
		List<User> users = userServie.findAllAdminUsers();
		model.addAttribute("users", users);
		return "admin/index";
	}
	
	/**
	 * 用户登陆
	 * @param userName
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/2login", method=RequestMethod.POST)
	public ResponseInfo login(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password, HttpSession session) {
		logger.info("管理员登录----------------------------");
		return userServie.adminLogin(userName, password, session);
	}
	
	@RequestMapping(value="/loginout")
	public String loginOut(HttpSession session) {
		session.removeAttribute(Constants.KEY_CURR_USER);
		return "redirect:login";
	}
	
	@RequestMapping(value="/{page}")
	public String gotoPage(HttpSession session, @PathVariable String page) {
		User user = (User) session.getAttribute(Constants.KEY_CURR_USER);
		if(user == null) {
			return "admin/login";
		}
		return "admin/" + page;
	}
	
}
