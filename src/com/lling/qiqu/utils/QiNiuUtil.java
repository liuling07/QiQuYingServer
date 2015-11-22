/**
 * 
 */
package com.lling.qiqu.utils;

import java.io.InputStream;
import java.util.UUID;

import org.json.JSONException;

import com.lling.qiqu.commons.InitConfig;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;

/**
 * @author zhr
 * @project c001
 * @create_date 2014-9-30 下午4:26:59
 */
public class QiNiuUtil {
	/** 七牛初始化*/
	private static Mac mac = new Mac(InitConfig.get("qiniu.ak"), InitConfig.get("qiniu.sk"));    
	public static String bucketName = InitConfig.get("qiniu.bucketName");// 请确保该bucket已经存在
	private static PutPolicy putPolicy = new PutPolicy(bucketName);	
	private static PutExtra extra = new PutExtra();
    private static PutRet ret = null;
    private static String urlPrefix = InitConfig.get("qiniu.url");
    /**
     * 上传文件
       * @author zhr
       * @create_date 2014-9-30 下午4:38:48
       * @param key
       * @param inputStream
       * @param isCover
       * @return
     */
    public static String upload2Stream(String key,InputStream inputStream,boolean isCover){
    	try{
    		if(isCover)
    			putPolicy.scope = bucketName+":"+key;
    		else
    			putPolicy.scope = bucketName;
            String uptoken = putPolicy.token(mac);				
    		ret = IoApi.Put(uptoken, key, inputStream, extra);
    		if(ret!=null){
    			if(ret.getStatusCode()==200) {
    				System.out.println(ret.response);
    				return ret.getKey();
    			}
    			if(ret.getStatusCode()!=200)
    				return ret.getException().getMessage();
    		}    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}    	
		return null;
    }
    
//    public static MyRet
    
    public static String uploadByLocal(String key,String localFile,boolean isCover){
    	try{
    		if(isCover)
    			putPolicy.scope = bucketName+":"+key;
    		else
    			putPolicy.scope = bucketName;
            String uptoken = putPolicy.token(mac);				
    		ret = IoApi.putFile(uptoken, key, localFile, extra);
    		if(ret!=null){
    			if(ret.getStatusCode()==200)
    				return ret.getKey();
    			if(ret.getStatusCode()!=200)
    				return ""+ret.getStatusCode();
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}    	
		return null;
    }
    
    /**
     * 删除文件
       * @author zhr
       * @create_date 2014-9-30 下午4:39:31
       * @param key
       * @return
     */
    public static int deleteByKey(String key){
    	RSClient client = new RSClient(mac);
    	CallRet ret = client.delete(bucketName, key);
    	return ret.getStatusCode();
    }
    
    /**
     * 复制文件
       * @author zhr
       * @create_date 2014-10-8 上午10:28:10
       * @param key
       * @return
     */
    public static int copy(String keySrc,String keyDest){
    	RSClient client = new RSClient(mac);
    	CallRet ret = client.copy(bucketName, keySrc, bucketName, keyDest);
    	return ret.getStatusCode();
    }
    
    public static String getQiniuToken(String key,boolean isCover){
    	if(isCover)
			putPolicy.scope = bucketName+":"+key;
		else
			putPolicy.scope = bucketName;
        try {
			return putPolicy.token(mac);
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 获取水印路径
     * @param urlPath
     * @return
     */
    public static String getWaterMark(String urlPath) {
    	if(!urlPath.startsWith(urlPrefix)) {
    		return urlPath;
    	}
    	String waterMark= "?watermark/1/image/aHR0cDovLzd4amVoZy5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS93YXRlcm1hcmsucG5n/dissolve/50/gravity/SouthEast";
    	return urlPath + waterMark;
    }
    
    public static void main(String[] args){
    	String key = UUID.randomUUID().toString()+".jpg";
    	System.out.println(getQiniuToken(key,true)+"\n"+key);
    }
}
