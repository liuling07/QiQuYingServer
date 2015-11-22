package com.lling.qiqu.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * HTTP请求工具类
 * 
 * @author Kevin Chang
 */
public class HttpConnHelper {
	private static final Log log = LogFactory.getLog(HttpConnHelper.class);
	/**
	 * 创建HTTP客户端 HttpClient已经实现了线程安全
	 */
	private static CloseableHttpClient client = HttpClientBuilder.create()
			.build();
	private static Gson gson = new GsonBuilder().create();
	/**
	 * 创建响应处理器ResponseHandler
	 * 
	 * @return
	 */
	public static <T> ResponseHandler<T> prepareResponseHandler(final Class<T> c) {
		ResponseHandler<T> rh = new ResponseHandler<T>() {
			@Override
			public T handleResponse(final HttpResponse response)
					throws IOException {

				StatusLine statusLine = response.getStatusLine();
				HttpEntity entity = response.getEntity();
				if (statusLine.getStatusCode() >= 300) {
					throw new HttpResponseException(statusLine.getStatusCode(),
							statusLine.getReasonPhrase());
				}
				if (entity == null) {
					throw new ClientProtocolException(
							"Response contains no content");
				}
				ContentType contentType = ContentType.getOrDefault(entity);
				Charset charset = contentType.getCharset();
				Reader reader = new InputStreamReader(entity.getContent(),
						charset);
				long hand_start = System.currentTimeMillis();
				T t = gson.fromJson(reader, c);
				long hand_end = System.currentTimeMillis();
				double cost = (hand_end-hand_start)/1000.0;
				log.info("handleResponse convert cost time "+cost+"s");
				return t;
			}
		};
		return rh;
	}

	/**
	 * GET请求
	 */
	public static String doHttpGetRequest(String url) {
		if (StringUtils.isEmpty(url)) {
			return "";
		}
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(10000).setConnectTimeout(10000)
				.setSocketTimeout(10000).build();
		HttpRequestBase request = null;
		CloseableHttpResponse response = null;
		String content = "";
		try {
			request = new HttpGet(url);
			request.setConfig(requestConfig);
			/**
			 * 发送基本的GET请求
			 */
			response = client.execute(request);
			// HTTP响应的状态码
			int statusCode = response.getStatusLine().getStatusCode();
			// 媒体类型
			String contentMimeType = ContentType.getOrDefault(
					response.getEntity()).getMimeType();
			// 响应的body部分
			String bodyString = EntityUtils.toString(response.getEntity(),
					Consts.UTF_8);
			log.info("doHttpGetRequest request url is " + url
					+ " response result|statusCode=" + statusCode
					+ "|contentMimeType=" + contentMimeType);
			return bodyString;
		} catch (Exception e) {
			log.error("doHttpGetRequest Exception|reqUrl="+url, e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
			}
		}
		return content;
	}

	/**
	 * GET请求
	 */
	public static Object doHttpGetRequest(String url,
			ResponseHandler<?> responseHandler) {
		if (StringUtils.isEmpty(url)) {
			return null;
		}
		Object obj = null;
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(10000).setConnectTimeout(10000)
				.setSocketTimeout(10000).build();
		HttpRequestBase request = null;
		try {
			request = new HttpGet(url);
			request.setConfig(requestConfig);
			/**
			 * 发送基本的GET请求
			 */
			obj = client.execute(request, responseHandler);
		} catch (Exception e) {
			log.error("doHttpGetRequest Exception|reqUrl="+url, e);
		}
		return obj;
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param postParams
	 * @return HTTP响应返回内容
	 */
	public static String doHttpPostRequest(String url, Object postParams) {
		if (StringUtils.isEmpty(url)) {
			return "";
		}
		if (postParams == null) {
			return doHttpGetRequest(url);
		}
		String bodyString = "";
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(10000).setConnectTimeout(10000)
				.setSocketTimeout(10000).build();
		/**
		 * 创建HTTP客户端
		 */
		// CloseableHttpClient client = HttpClients.createDefault();
		HttpRequestBase request = null;
		CloseableHttpResponse response = null;
		try {
			request = new HttpPost(url);
			request.setConfig(requestConfig);
			if (postParams instanceof Map) {
				Map<String, String> params = (Map<String, String>) postParams;
				setPostParams(request, params);
			} else if (postParams instanceof String) {
				String params = (String) postParams;
				setPostParams(request, params);
			}
			response = client.execute(request);
			// HTTP响应的状态码
			int statusCode = response.getStatusLine().getStatusCode();
			// 媒体类型
			String contentMimeType = ContentType.getOrDefault(
					response.getEntity()).getMimeType();
			// 响应的body部分
			bodyString = EntityUtils.toString(response.getEntity(), "utf-8");
			log.info("doHttpRequest request url is " + url
					+ " response result|statusCode=" + statusCode
					+ "|contentMimeType=" + contentMimeType);
		} catch (Exception e) {
			log.error("doHttpPostRequest Exception.", e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
			}
		}
		return bodyString;
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param postParams
	 * @return HTTP响应返回内容
	 */
	public static Object doHttpPostRequest(String url, Object postParams,
			ResponseHandler<?> responseHandler) {
		if (StringUtils.isEmpty(url)) {
			return null;
		}
		if (postParams == null) {
			return doHttpGetRequest(url, responseHandler);
		}
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(10000).setConnectTimeout(10000)
				.setSocketTimeout(10000).build();
		HttpRequestBase request = null;
		Object obj = null;
		try {
			request = new HttpPost(url);
			request.setConfig(requestConfig);
			if (postParams instanceof Map) {
				Map<String, String> params = (Map<String, String>) postParams;
				setPostParams(request, params);
			} else if (postParams instanceof String) {
				String params = (String) postParams;
				setPostParams(request, params);
			}
			obj = client.execute(request, responseHandler);
		} catch (Exception e) {
			log.error("doHttpPostRequest Exception.", e);
		}
		return obj;
	}

	/**
	 * 设置POST请求参数
	 * 
	 * @param request
	 * @param postParams
	 * @throws UnsupportedEncodingException
	 */
	private static void setPostParams(HttpRequestBase request,
			Map<String, String> postParams) throws UnsupportedEncodingException {
		List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : postParams.entrySet()) {
			postData.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		AbstractHttpEntity entity = new UrlEncodedFormEntity(postData,
				Consts.UTF_8);
		((HttpPost) request).setEntity(entity);
	}

	/**
	 * 设置POST请求参数
	 * 
	 * @param request
	 * @param postParams
	 * @throws UnsupportedEncodingException
	 */
	private static void setPostParams(HttpRequestBase request, String postParams)
			throws UnsupportedEncodingException {
		AbstractHttpEntity entity = new StringEntity(postParams, Consts.UTF_8);
		((HttpPost) request).setEntity(entity);
	}

	/**
	 * PUT 请求
	 * @author Dijt
	 * @param url
	 * @param putParams
	 * @return
	 * @throws Exception 
	 * @create_at 2014年11月7日下午3:02:29
	 */
	public static String doHttpPutRequest(String url, Object putParams) throws Exception{
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(10000).setConnectTimeout(10000)
				.setSocketTimeout(10000).build();
		/**
		 * 创建HTTP客户端
		 */
		// CloseableHttpClient client = HttpClients.createDefault();
		HttpRequestBase request = null;
		CloseableHttpResponse response = null;
		try {
			request = new HttpPut(url);
			request.setConfig(requestConfig);
			if (putParams instanceof Map) {
				Map<String, String> params = (Map<String, String>) putParams;
				List<BasicNameValuePair> putData = new ArrayList<BasicNameValuePair>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					putData.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				AbstractHttpEntity entity = new UrlEncodedFormEntity(putData,
						Consts.UTF_8);
				((HttpPut) request).setEntity(entity);
			} else if (putParams instanceof String) {
				String params = (String) putParams;
				AbstractHttpEntity entity = new StringEntity(params, Consts.UTF_8);
				((HttpPut) request).setEntity(entity);
			}
			response = client.execute(request);
			// HTTP响应的状态码
			int statusCode = response.getStatusLine().getStatusCode();
			// 媒体类型
			String contentMimeType = ContentType.getOrDefault(
					response.getEntity()).getMimeType();
			// 响应的body部分
			String bodyString = EntityUtils.toString(response.getEntity(),
					Consts.UTF_8);
			log.info("doHttpGetRequest request url is " + url
					+ " response result|statusCode=" + statusCode
					+ "|contentMimeType=" + contentMimeType);
			return bodyString;
		} catch (Exception e) {
			log.error("doHttpPostRequest Exception.", e);
		}
		return response.toString();
	}
	
}
