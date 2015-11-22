package com.lling.qiqu.thread;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lling.qiqu.beans.Joke;
import com.lling.qiqu.service.IJokeService;

/**
 * @ClassName: GetImgSizeThread
 * @Description: 获取图片大小的线程
 * @author lling
 * @date 2015-7-12
 */
public class GetImgSizeThread extends Thread {
	private static final Log log = LogFactory.getLog(GetImgSizeThread.class);
	private Joke joke;
	private IJokeService jokeService;
	
	public GetImgSizeThread(Joke joke, IJokeService jokeService) {
		this.joke = joke;
		this.jokeService = jokeService;
	}
	
	/*@Override
	public void run() {
		if(joke == null) {
			return;
		}
		String url;
		if(joke.getImgUrl().contains("?")) {
			url = joke.getImgUrl() + "&imageInfo";
		} else {
			url = joke.getImgUrl() + "?imageInfo";
		}
		String response = HttpConnHelper.doHttpGetRequest(url);
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(response);
			int width = jsonObj.getInt("width");
			int height = jsonObj.getInt("height");
			joke.setImgWidth(width);
			joke.setImgHeight(height);
			jokeService.update(joke);
			log.info("get image size success, jokeId is " + joke.getId());
		} catch (Exception e) {
			log.error("GetImgSizeThread Exception.", e);
		}
	}*/
	
	@Override
	public void run() {
		if(joke == null) {
			return;
		}
		BufferedImage image = getBufferedImage(joke.getImgUrl()); 
        if (image != null) { 
            joke.setImgWidth(image.getWidth());
			joke.setImgHeight(image.getHeight());
			jokeService.update(joke);
			image = null;
        } else { 
            log.info("图片不存在！, url is " + joke.getImgUrl());
        } 
	}
	
	/**
     * 根据图片地址获取BufferedImage
     * @param imgUrl 图片地址
     * @return 
     */ 
    public static BufferedImage getBufferedImage(String imgUrl) { 
        URL url = null; 
        InputStream is = null; 
        BufferedImage img = null; 
        try { 
            url = new URL(imgUrl); 
            is = url.openStream(); 
            img = ImageIO.read(is); 
        } catch (MalformedURLException e) { 
        	log.error("GetImgSizeThread Exception.", e); 
        } catch (IOException e) { 
        	log.error("GetImgSizeThread Exception.", e);
        } finally { 
            try { 
                is.close(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
        return img; 
    } 
}
