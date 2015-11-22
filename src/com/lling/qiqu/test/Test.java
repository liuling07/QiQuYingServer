package com.lling.qiqu.test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.lling.qiqu.utils.UrlBase64Coder;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String s = HttpConnHelper.doHttpGetRequest("http://7xixxm.com2.z0.glb.clouddn.com/ed8fc103-7fbd-40cc-8f38-b0f8393645e0.jpg?imageInfo");
		String s;
		try {
			s = UrlBase64Coder.encoded("http://7xnqm4.com1.z0.glb.clouddn.com/watermark.png");
			System.out.println(s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		/*String imageUrl="http://www.huabian.com/uploadfile/2014/0620/20140620052441928.jpg"; 
        BufferedImage image = getBufferedImage(imageUrl); 
        if (image!=null) { 
            System.out.println("图片高度:"+image.getHeight()); 
            System.out.println("图片宽度:"+image.getWidth()); 
        } else { 
            System.out.println("图片不存在！"); 
        } 
        image = null;*/
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
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
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
