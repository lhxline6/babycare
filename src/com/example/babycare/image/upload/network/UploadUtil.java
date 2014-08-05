package com.example.babycare.image.upload.network;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import com.example.babycare.IPAD;

import android.util.Log;

/**
 * 
 * 上传工具类
 * 支持上传文件和参数
 */
public class UploadUtil {
	private static UploadUtil uploadUtil;
	private static final String BOUNDARY =  UUID.randomUUID().toString(); // 边界标识 随机生成
	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
	private UploadUtil() {

	}

	/**
	 * 单例模式获取上传工具类
	 * @return
	 */
	public static UploadUtil getInstance() {
		if (null == uploadUtil) {
			uploadUtil = new UploadUtil();
		}
		return uploadUtil;
	}

	private static final String TAG = "UploadUtil";
	private int readTimeOut = 10 * 1000; // 读取超时
	private int connectTimeout = 10 * 1000; // 超时时间
	/***
	 * 请求使用多长时间
	 */
	private static int requestTime = 0;
	
	private static final String CHARSET = "utf-8"; // 设置编码

	/***
	 * 上传成功
	 */
	public static final int UPLOAD_SUCCESS_CODE = 1;
	/**
	 * 文件不存在
	 */
	public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
	/**
	 * 服务器出错
	 */
	public static final int UPLOAD_SERVER_ERROR_CODE = 3;
	protected static final int WHAT_TO_UPLOAD = 1;
	protected static final int WHAT_UPLOAD_DONE = 2;
	
	/**
	 * android上传文件到服务器
	 * 
	 * @param filePath
	 *            需要上传的文件的路径
	 * @param fileKey
	 *            在网页上<input type=file name=xxx/> xxx就是这里的fileKey
	 * @param RequestURL
	 *            请求的URL
	 */
	public void uploadFile(String filePath, String fileKey, String RequestURL,
			Map<String, String> param) {
		if (filePath == null) {
			sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE,"文件不存在");
			return;
		}
		try {
			File file = new File(filePath);
			uploadFile(file, fileKey, RequestURL, param);
		} catch (Exception e) {
			sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE,"文件不存在");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * android上传文件到服务器
	 * 
	 * @param file
	 *            需要上传的文件
	 * @param fileKey
	 *            在网页上<input type=file name=xxx/> xxx就是这里的fileKey
	 * @param RequestURL
	 *            请求的URL
	 */
	public void uploadFile(final File file, final String fileKey,
			final String RequestURL, final Map<String, String> param) {
		if (file == null || (!file.exists())) {
			sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE,"文件不存在");
			return;
		}

		Log.i(TAG, "请求的URL=" + RequestURL);
		Log.i(TAG, "请求的fileName=" + file.getName());
		Log.i(TAG, "请求的fileKey=" + fileKey);
		new Thread(new Runnable() {  //开启线程上传文件
			@Override
			public void run() {
				toUploadFile(file, fileKey, RequestURL, param);
			}
		}).start();
		
	}

	private void toUploadFile(File file, String fileKey, String RequestURL,
			Map<String, String> param) {
		String result = null;
		requestTime= 0;
		
		long requestTime = System.currentTimeMillis();
		long responseTime = 0;
		 String urlParameters = "username=\"" + IPAD.user+"\"";  
		 String boundary = "*****";  
		    try  
		    {  
		      URL url =new URL(RequestURL);  
		      HttpURLConnection con=(HttpURLConnection)url.openConnection();  
		      /* 允许Input、Output，不使用Cache */  
//		      con.setReadTimeout(5 * 1000);   
		      con.setDoInput(true);  
		      con.setDoOutput(true);  
		      con.setUseCaches(false);  
		      /* 设定传送的method=POST */  
		      con.setRequestMethod("POST");  
		      /* setRequestProperty */  
		      con.setRequestProperty("Charset", "UTF-8");  
		      con.setRequestProperty("username",IPAD.user);
		      con.setRequestProperty("description",IPAD.photodiscription);
		      con.setRequestProperty("Connection", "Keep-Alive");  
		      
		      con.setRequestProperty("enctype",  
		                         "multipart/form-data;boundary="+boundary);  
		  
		      con.setUseCaches(false);  
	            
	  
		     
		      /* 设定DataOutputStream */  
		      DataOutputStream ds =   
		        new DataOutputStream(con.getOutputStream());  
		      /*ds.writeBytes(twoHyphens + boundary + end); 
		      ds.writeBytes("Content-Disposition: form-data; " + 
		                    "name=\"file1\";filename=\"" + 
		                    newName +"\"" + end); 
		      ds.writeBytes(end);  */  
		      /* 取得文件的FileInputStream */  
		      FileInputStream fStream = new FileInputStream(file);  
		      /* 设定每次写入1024bytes */  
		      int bufferSize = 1024;  
		      byte[] buffer = new byte[bufferSize];  
		      int length = -1;  
		      /* 从文件读取数据到缓冲区 */  
		      while((length = fStream.read(buffer)) != -1)  
		      {  
		        /* 将数据写入DataOutputStream中 */  
		        ds.write(buffer, 0, length);  
		      }  
		      ds.writeBytes("\r\n"); 
		      ds.writeBytes(urlParameters);
		      ds.writeBytes("\r\n");  
//		      ds.writeBytes(twoHyphens + boundary + twoHyphens + end);  
		      /* close streams */  
		      fStream.close();  
		      ds.flush();  
//			
//			dos.write(tempOutputStream.toByteArray());
			/**
			 * 获取响应码 200=成功 当响应成功，获取响应的流
			 */
			int res = con.getResponseCode();
			responseTime = System.currentTimeMillis();
			UploadUtil.requestTime = (int) ((responseTime-requestTime)/1000);
			Log.e(TAG, "response code:" + res);
			if (res == 200) {
				Log.e(TAG, "request success");
				InputStream input = con.getInputStream();
				StringBuffer sb1 = new StringBuffer();
				int ss;
				while ((ss = input.read()) != -1) {
					sb1.append((char) ss);
				}
				result = sb1.toString();
				Log.e(TAG, "result : " + result);
				sendMessage(UPLOAD_SUCCESS_CODE, result);
				return;
			} else {
				Log.e(TAG, "request error");
				sendMessage(UPLOAD_SERVER_ERROR_CODE,"上传失败：code=" + res);
				return;
			}
		} catch (MalformedURLException e) {
			sendMessage(UPLOAD_SERVER_ERROR_CODE,"上传失败：error=" + e.getMessage());
			e.printStackTrace();
			return;
		} catch (IOException e) {
			sendMessage(UPLOAD_SERVER_ERROR_CODE,"上传失败：error=" + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 发送上传结果
	 * @param responseCode
	 * @param responseMessage
	 */
	private void sendMessage(int responseCode,String responseMessage)
	{
		onUploadProcessListener.onUploadDone(responseCode, responseMessage);
	}
	
	/**
	 * 下面是一个自定义的回调函数，用到回调上传文件是否完成
	 * 
	 * @author shimingzheng
	 * 
	 */
	public static interface OnUploadProcessListener {
		/**
		 * 上传响应
		 * @param responseCode
		 * @param message
		 */
		void onUploadDone(int responseCode, String message);
		/**
		 * 上传中
		 * @param uploadSize
		 */
		void onUploadProcess(int uploadSize);
		/**
		 * 准备上传
		 * @param fileSize
		 */
		void initUpload(int fileSize);
	}
	private OnUploadProcessListener onUploadProcessListener;
	
	

	public void setOnUploadProcessListener(
			OnUploadProcessListener onUploadProcessListener) {
		this.onUploadProcessListener = onUploadProcessListener;
	}

	public int getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	/**
	 * 获取上传使用的时间
	 * @return
	 */
	public static int getRequestTime() {
		return requestTime;
	}
	
	public static interface uploadProcessListener{
		
	}
	
	
	
	
}
