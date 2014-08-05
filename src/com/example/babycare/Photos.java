/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.babycare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.babycare.imagecache.ImageFileCache;
import com.example.babycare.imagecache.ImageMemoryCache;
import com.example.babycare.popwindow.TitlePopup;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.AdapterView;  
import android.widget.AdapterView.OnItemSelectedListener;  
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;  

public class Photos extends Activity  { 
	
	static final private int SETTINGS_ID = Menu.FIRST + 1;
	static final private int REFRESH_ID = Menu.FIRST + 2;
	
    private ProgressBar progress;
    private FrameLayout frameLayout;
    private Bitmap bitmap=null;
    ProgressDialog dialog=null;
    private Gallery gallery;  
    private ImageView imageView;
    private TextView des;
    private ImageMemoryCache memoryCache;
    
    private ImageFileCache fileCache;
    
    private List<String> addressList = new ArrayList<String>();
    private List<String> descriptionList = new ArrayList<String>();
    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    public static Photos instance = null;  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download);

       instance=this;
        
      progress=(ProgressBar)this.findViewById(R.id.progressBar1); 
       progress.setVisibility(View.GONE);

        frameLayout=(FrameLayout)this.findViewById(R.id.frameLayout);
        
        gallery = (Gallery) findViewById(R.id.gallery1);  
        imageView = (ImageView) findViewById(R.id.image);  
        
        memoryCache = new ImageMemoryCache(this); 
        fileCache = new ImageFileCache();
        des = (TextView) findViewById(R.id.descri);
        
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/xiongtu.ttf");
        des.setTypeface(typeFace);
        des.setTextSize(16);
        
        ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
		NetworkInfo info = cManager.getActiveNetworkInfo();
		  if (info != null && info.isAvailable()){
			  new Thread(runnable).start();
		  }else{   
			  Toast.makeText(getBaseContext(), "当前未连接网络，请先连接后再尝试" ,Toast.LENGTH_LONG).show();
		  }   
    }
   
    private Runnable runnable = new Runnable(){
        @Override
        public void run() {
        	geneItems(IPAD.user);
        	for(int i = 0; i<addressList.size();i++){
    	        Bitmap icon =memoryCache.getBitmapFromCache(addressList.get(i));
				if(icon == null) {
				    // 文件缓存中获取
					icon = fileCache.getImage(addressList.get(i));
				    if (icon == null) {
				    	
				    	
				    	
				    	//ImageView iv = (ImageView)findViewById(R.id.ImageView01);
				        
				        HttpClient client = new DefaultHttpClient();
						HttpPost post = new HttpPost("http://"+IPAD.ip+"/uploadfile/getpic.php");
						try{
							Log.e("log_tag", "Error converting result "+addressList.get(i));
							List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(1);
							namevaluepairs.add(new BasicNameValuePair("pic",addressList.get(i)));
							post.setEntity(new UrlEncodedFormEntity(namevaluepairs));
							HttpResponse response = client.execute(post);
							HttpEntity entity = response.getEntity();
							InputStream is = entity.getContent();
							//Drawable d = Drawable.createFromStream(is, "pic");
							icon = BitmapFactory.decodeStream(is);
						//	iv.setImageDrawable(d);
							
							
						}catch(ClientProtocolException e){
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
				    	
				    	if (icon != null) {
				            fileCache.saveBitmap(icon, addressList.get(i));
				            memoryCache.addBitmapToCache(addressList.get(i), icon);
				        }
				    } else {
				        // 添加到内存缓存
				        memoryCache.addBitmapToCache(addressList.get(i), icon);
				    }
				}
				
				int width = icon.getWidth();
				int height = icon.getHeight();
				int newWidth = 170;
				int newHeight = 170;
				float scaleWidth = ((float) newWidth) / width;
				float scaleHeight = ((float) newHeight) / height;

				Matrix bMatrix = new Matrix();
				bMatrix.postScale(scaleWidth, scaleHeight);

				bitmaps.add(Bitmap.createBitmap(icon, 0, 0, width, height, bMatrix, true));
	           // BitmapFactory.decodeStream());  
        	}
	        
        
        	Message msg=new Message();
	        msg.what=1;
	        handler.sendMessage(msg);
	        
	        
        }
    };

	
 
    
	 private Handler handler=new Handler(){
	        @Override
	        public void handleMessage(Message msg){
	            switch(msg.what){
	            case 1:
	            	
	            	
	            	
	                //关闭
	            	BaseAdapter adapter = new BaseAdapter() {  
	                    
	                    @Override  
	                    public View getView(int position, View convertView, ViewGroup parent) {  
	                        // 创建一个ImageView  
	                        ImageView imageView = new ImageView(Photos.this);  
	                        //imageView.setImageResource(imageIds[position]);  
	                        //imageView.setImageBitmap(bitmap);
	                        imageView.setImageBitmap(bitmaps.get(position));
	                        // 设置ImageView的缩放类型  
	                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);  
	                        // 为imageView设置布局参数  
	                        imageView.setLayoutParams(new Gallery.LayoutParams(200, 150));  
	                        
	                        TypedArray typedArray = obtainStyledAttributes(  
	                                R.styleable.Gallery);  
	                        imageView.setBackgroundResource(typedArray.getResourceId(  
	                                R.styleable.Gallery_android_galleryItemBackground, 0));  
	                        return imageView;  
	                    }  
	                      
	                    @Override  
	                    public long getItemId(int position) {  
	                        // TODO Auto-generated method stub  
	                        return position;  
	                    }  
	                      
	                    @Override  
	                    public Object getItem(int item) {  
	                        // TODO Auto-generated method stub  
	                        return item;  
	                    }  
	                      
	                    @Override  
	                    public int getCount() {  
	                        // TODO Auto-generated method stub  
	                        return addressList.size();  
	                    }  
	                };  
	                
	                
	                gallery.setAdapter(adapter);  
	                gallery.setOnItemSelectedListener(new OnItemSelectedListener() {  
	          
	                    @Override  
	                    public void onItemSelected(AdapterView<?> parent, View view,  
	                            int position, long id) {  
	                    //    imageView.setImageResource(imageIds[position]);  
	                        imageView.setImageBitmap(bitmaps.get(position));
	                        des.setText(descriptionList.get(position)) ;
	                    }  
	          
	                    @Override  
	                    public void onNothingSelected(AdapterView<?> view) {  
	                          
	                          
	                    }  
	                });  
	                
	             //   dialog.dismiss();
	                break;
	            }
	        }
	    };
	    
   
	
	    private void geneItems(String user) {
			 String result = null;
			 JSONArray jArray;
			 StringBuilder sb=null;
			 InputStream is = null;
			 int a=0;
			 //http get
		    try{
		    	 HttpClient httpclient = new DefaultHttpClient();
		         HttpPost httpget = new HttpPost("http://"+IPAD.ip+"/babycare/getphotoaddress.php");                
		         List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			     postParameters.add(new BasicNameValuePair("usuario", user));
		         UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters,"UTF-8");
		         httpget.setEntity(formEntity);
		         HttpResponse response = httpclient.execute(httpget);
		         HttpEntity entity = response.getEntity();   
		         is = entity.getContent();
		    }catch(Exception e){
		         Log.e("log_tag", "Error in http connection"+e.toString());
		    }
		    //convert response to string     
		    try{
		         BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		         sb = new StringBuilder();
		         sb.append(reader.readLine() + "\n");
		
		         String line="0";
		         while ((line = reader.readLine()) != null) {
		              sb.append(line + "\n");
		         }
		         is.close();
		         result=sb.toString();
		    }catch(Exception e){
		         Log.e("log_tag", "Error converting result "+e.toString());
		    }
		    if(result=="")return;
		    try{
		         jArray = new JSONArray(result);
		         JSONObject json_data=null;
		         for(int i=0;i<jArray.length();i++){
		              json_data = jArray.getJSONObject(i);
		            //  IPAD.year=json_data.getInt("year");
		              //"http://"+IPAD.ip+"/var/vcap/store/fss_backend1/7d72c4aa-36ca-4283-baee-637ac973b197"+
		              addressList.add(json_data.getString("address"));
		              descriptionList.add(json_data.getString("description"));
		         }
		    }catch(JSONException e1){
		    	 Log.e("log_tag", "Error converting result "+e1.toString());
		          Toast.makeText(getBaseContext(), "No id Found" ,Toast.LENGTH_LONG).show();
		    } catch (ParseException e1) {
		         e1.printStackTrace();
		    }
		    
	    }
	    
	    
	    @Override
		public boolean onCreateOptionsMenu(Menu menu) {
		        super.onCreateOptionsMenu(menu);
		   //     menu.add(0, STATE_ID, 0, R.string.state).setShortcut('0', 'b');
		        menu.add(0, SETTINGS_ID, 0, "上传照片").setShortcut('1', 'c');
		        menu.add(0, REFRESH_ID, 0, "刷新").setShortcut('2', 'd');

		        return true;
		    }
		
		 @Override
		 public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {

	        case SETTINGS_ID:  
	        	Intent i1=new Intent(Photos.this, UploadPic.class);
				startActivity(i1); 
				return true;
	        case REFRESH_ID:
	        	IPAD.tab1=2;
	        	IPAD.tab2=1;
	        	Intent i2=new Intent(Photos.this, Photos.class);
				startActivity(i2); 
	            return true;
	            
	        }
	        return super.onOptionsItemSelected(item);
		 }
	    
	  
}