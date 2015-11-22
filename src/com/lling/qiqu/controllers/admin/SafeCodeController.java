package com.lling.qiqu.controllers.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lling.qiqu.commons.Constants;
import com.lling.qiqu.commons.ResponseInfo;
import com.lling.qiqu.enums.CodeRespEnum;

/**
 * @ClassName: AdminController
 * @Description: 验证码控制器
 * @author lling
 * @date 2015-8-4
 */
@Controller
public class SafeCodeController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	//产生随机颜色
	Color getRandColor(int fc,int bc){ //给定范围获得随机颜色
		Random random = new Random();
		if(fc>255)
			fc = 255;
		if(bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r,g,b);
	}
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/code")
	public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("获取验证码");
		HttpSession session = request.getSession();
		String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		//设置页面不缓冲
	 	response.setHeader("Pragma","No-cache");
	 	response.setHeader("Cache-Control","no-cache");
	 	response.setDateHeader("Expires",0);
	 	//在内存中创建图像
	 	int width = 60;
	 	int height = 20;
	 	BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	 	
	 	//取得图形上下文
	 	Graphics g = image.getGraphics();
	 	
	 	//生成随机类
	 	Random random = new Random();
	 	
	 	//设定背景色
	 	g.setColor(getRandColor(200,250));
	 	g.fillRect(0,0,width-1,height-1);
	 	
	 	//设定字体
	 	g.setFont(new Font("Times New Roman",Font.PLAIN,18));
	 	
	 	//随机产生155条干扰线，使图像中的认证码不易被其他程序探测到
	 	g.setColor(getRandColor(160,200));
	 	for(int i=0;i<155;i++){
	 		int x = random.nextInt(width);
	 		int y = random.nextInt(height);
	 		int x1 = random.nextInt(12);
	 		int y1 = random.nextInt(12);
	 		g.drawLine(x,y,x+x1,y+y1);
	 	}
	 	//随机产生4位认证码
	 	String sRand = "";
	 	for(int i=0;i<4;i++){
	 		String rand = String.valueOf(str.charAt(random.nextInt(62))) ;
	 		sRand = sRand + rand;
	 		//将认证码显示到图像中
	 		g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
	 		g.drawString(rand,13*i+6,16);
	 	}
	 	//将认证码存入session中
	 	session.setAttribute(Constants.KEY_SAFE_CODE, sRand);
	 	//图像生效
	 	g.dispose();
	 	response.reset();
	 	OutputStream out = response.getOutputStream();
	 	//输出图像到页面
	 	ImageIO.write(image, "JPEG", out);
	 	out.close();
	}
	
	@ResponseBody
	@RequestMapping(value="/checkcode")
	public ResponseInfo checkCode(@RequestParam(value = "code", required = true) String code, HttpSession session) {
		ResponseInfo responseInfo = new ResponseInfo();
		String safeCode = (String) session.getAttribute(Constants.KEY_SAFE_CODE);
		if(safeCode != null && safeCode.equalsIgnoreCase(code)) {
			responseInfo.setCode(CodeRespEnum.CODE_SUCCESS.getCode());
		} else {
			responseInfo.setCode(CodeRespEnum.CODE_PARAM_INVALUD.getCode());
		}
		return responseInfo;
	}
	
}
