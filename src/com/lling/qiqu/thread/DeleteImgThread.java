package com.lling.qiqu.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.lling.qiqu.beans.Joke;
import com.lling.qiqu.commons.InitConfig;
import com.lling.qiqu.service.IJokeService;
import com.lling.qiqu.utils.HttpConnHelper;
import com.lling.qiqu.utils.QiNiuUtil;

/**
 * @ClassName: DeleteImgThread
 * @Description: 删除七牛图片的线程
 * @author lling
 * @date 2015年7月30日
 */
public class DeleteImgThread extends Thread {
	private static final Log log = LogFactory.getLog(DeleteImgThread.class);
	private String imgKey;
	
	public DeleteImgThread(String imgKey) {
		this.imgKey = imgKey;
	}
	
	@Override
	public void run() {
		if(imgKey == null) {
			return;
		}
		QiNiuUtil.deleteByKey(imgKey);
	}
}
