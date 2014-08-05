package com.example.babycare;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.babycare.IPAD;
import com.example.babycare.R;
import com.example.babycare.Login.asynclogin;
import com.example.babycare.popwindow.ActionItem;
import com.example.babycare.popwindow.TitlePopup;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {
	private ArrayList<View> views;
	private int cYear,cMonth;
	private int rYear,rMonth,rDay;
	private int index;
	private TextView height_text,breast_text,weight_text,info_text,new_text;
	private Double rWeight,rHeight,rBreast;
//	private Button Record,Records;
	private LinearLayout record_button,chart,note,health_tips,growth,records,shopping,tuisong,serach,readme;
	
	 //定义标题栏上的按钮  
    private ImageButton titleBtn;  
    //定义标题栏弹窗按钮  
    private TitlePopup titlePopup;
	
    public static MainActivity instance = null;  
    
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        instance=this;
        
        record_button  = (LinearLayout)findViewById(R.id.record_button);
        record_button.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View view){
        		 Intent i=new Intent(MainActivity.this, Record.class);
 					startActivity(i); 
        	}
        	});
        
        records  = (LinearLayout)findViewById(R.id.records);
        records.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View view){
        		
        		ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
        		NetworkInfo info = cManager.getActiveNetworkInfo();   
        		  if (info != null && info.isAvailable()){   
        			  Intent i=new Intent(MainActivity.this, Records.class);
   					  startActivity(i); 
        		  }else{   
        			  Toast.makeText(getBaseContext(), "当前未连接网络，请先连接后再尝试" ,Toast.LENGTH_LONG).show(); 
        		  }  
        		
        		
        		
        		 
        	}
        	});
        
        
        
        chart  = (LinearLayout)findViewById(R.id.chart);
        chart.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View view){
        		ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
        		NetworkInfo info = cManager.getActiveNetworkInfo();   
        		  if (info != null && info.isAvailable()){   
        			  Intent i=new Intent(MainActivity.this, Tabs.class);
   					startActivity(i); 
        		  }else{   
        			  Toast.makeText(getBaseContext(), "当前未连接网络，请先连接后再尝试" ,Toast.LENGTH_LONG).show(); 
        		  }  
        	}
        	});
        
        note  = (LinearLayout)findViewById(R.id.note);
        note.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View view){
        		ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
        		NetworkInfo info = cManager.getActiveNetworkInfo();   
				if (info != null && info.isAvailable()){   
					Intent i=new Intent(MainActivity.this, Diary.class);
					startActivity(i); 
				}else{   
					Toast.makeText(getBaseContext(), "当前未连接网络，请先连接后再尝试" ,Toast.LENGTH_LONG).show(); 
				}  
        	}
        	});
        
        
        health_tips  = (LinearLayout)findViewById(R.id.health_tips);
        health_tips.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View view){
        		Intent i=new Intent(MainActivity.this, EmergencyList.class);
 				startActivity(i); 
        	}
        	});
        
        growth  = (LinearLayout)findViewById(R.id.growth);
        growth.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View view){
        		ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
        		NetworkInfo info = cManager.getActiveNetworkInfo();   
				if (info != null && info.isAvailable()){   
					if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
					{
	        		 Intent i=new Intent(MainActivity.this, Photos.class);
	 					startActivity(i);
					}
	        		else{
	        			Toast.makeText(getBaseContext(), "SD卡已拔出，成长点滴功能暂时不可用" ,Toast.LENGTH_LONG).show();
	        		}
				}else{   
					Toast.makeText(getBaseContext(), "当前未连接网络，请先连接后再尝试" ,Toast.LENGTH_LONG).show(); 
				} 
        		
        		
        	}
        	});
        
        shopping  = (LinearLayout)findViewById(R.id.shopping);
        shopping.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View view){
        		Toast.makeText(getBaseContext(), "宝宝商城还在筹备当中！" ,Toast.LENGTH_LONG).show();

        	}
        	});
        
        tuisong  = (LinearLayout)findViewById(R.id.tuisong);
        tuisong.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View view){
        		Intent i=new Intent(MainActivity.this, MainActivityForPush.class);
    			startActivity(i);
        	}
        });
        
        serach  = (LinearLayout)findViewById(R.id.search);
        serach.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View view){
        		Intent i=new Intent(MainActivity.this, SimpleSearch.class);
    			startActivity(i);
        	}
        });
         
        
        readme  = (LinearLayout)findViewById(R.id.readme);
        readme.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View view){
        		Intent i=new Intent(MainActivity.this, Shuoming.class);
    			startActivity(i);
        	}
        });
         
        
        
        
        height_text = (TextView)findViewById(R.id.height_info);
    	weight_text = (TextView)findViewById(R.id.weight_info);  
    	breast_text = (TextView)findViewById(R.id.breast_info);
    	info_text = (TextView)findViewById(R.id.info);
    	new_text = (TextView)findViewById(R.id.newest);
    	
    	Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/xiongtu.ttf");
    	
    	info_text.setTypeface(typeFace);
    	weight_text.setTypeface(typeFace);
    	breast_text.setTypeface(typeFace);
    	height_text.setTypeface(typeFace);
    	new_text.setTypeface(typeFace);
    	
    	
    	
    	
    	
    	Time time = new Time("GMT+8");    
        time.setToNow(); 
        
        Date dateNow =  new Date();
        dateNow.setYear(time.year);
        dateNow.setMonth(time.month);
        dateNow.setDate(time.monthDay);
        Date dateBirth =  new Date();
        dateBirth.setYear(IPAD.year);
        dateBirth.setMonth(IPAD.month);
        dateBirth.setDate(IPAD.day);
     
        int tmp = daysBetween(dateNow,dateBirth)/30;
        
        
        
        if(tmp<=12)
        	index = tmp;
        else
        	index = (tmp - 12)/3 + 12;
        
        if(index < 0)
        	index = 0;
        
        if(index>=36)
        	index=35;
      
        
       
       
        initView();
        initData();
     /*   if(IPAD.month <= time.month){
			cYear = time.year - IPAD.year; 
			cMonth = time.month - IPAD.month;
		}
        else{
        	cYear = time.year - IPAD.year - 1; 
        	cMonth = time.month - IPAD.month + 12;
        }
        
        if(cYear < 1){
        	if(cMonth <= 5)
        		index = cMonth;
        	else if(cMonth < 8)
        		index = 6;
        	else if(cMonth < 10)
        		index = 7;
        	else
        		index = 8;
        }
        else if(cYear < 2){
        	if(cMonth < 3)
        		index = 9;
        	else if(cMonth < 6)
        		index = 10;
        	else if(cMonth < 9)
        		index = 11;
        	else index = 12;
        }
        else if(cYear >= 2 && cYear < 6){
        	index = 13 + (cYear - 2) * 2;
        	if(cMonth > 6)
        		index++;
        }
        else if(cYear >= 6 && cYear < 18){
        	index = 21 + cYear - 6;
        }*/
        
        if(IPAD.sex==1)// "\n性别：\n\t男"
    		info_text.setText("宝宝：\n\t"+IPAD.name+"\n性别：\n\t男\n生日：\n\t"+IPAD.year+"."+IPAD.month+"."+IPAD.day);
    	else if(IPAD.sex==2)
    		info_text.setText("宝宝：\n\t"+IPAD.name+"\n性别：\n\t女\n生日：\n\t"+IPAD.year+"."+IPAD.month+"."+IPAD.day);
        if(IPAD.isrecord==0)
        	new_text.setText("当前没有宝宝的 记录，请点击“添加记录” ^.^");
        else
        	new Thread(runnable).start();
    }
    
    
    private int daysBetween(Date now, Date returnDate) {
	    Calendar cNow = Calendar.getInstance();
	    Calendar cReturnDate = Calendar.getInstance();
	    cNow.setTime(now);
	    cReturnDate.setTime(returnDate);
	    setTimeToMidnight(cNow);
	    setTimeToMidnight(cReturnDate);
	    long todayMs = cNow.getTimeInMillis();
	    long returnMs = cReturnDate.getTimeInMillis();
	    long intervalMs = todayMs - returnMs;
	    return millisecondsToDays(intervalMs);
	  }

	  private int millisecondsToDays(long intervalMs) {
	    return (int) (intervalMs / (1000 * 86400));
	  }

	  private void setTimeToMidnight(Calendar calendar) {
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	  }
    
    private Runnable runnable = new Runnable(){
        @Override
        public void run() {        
        	geneItems(IPAD.user); 	
        	
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
	            	new_text.setText("最近记录:\n"+rYear+"."+rMonth+"."+rDay);
	            	
	            	String weight_output="体重："+String.valueOf(rWeight)+"kg \n";
	            	String height_output="身高："+String.valueOf(rHeight)+"cm \n";
	            	String breast_output="头围："+String.valueOf(rBreast)+"cm \n";
	            	/* if(index<=0) index=0;
	            	if(IPAD.sex==1){
	            		if(rWeight < IPAD.weight_boy[index][0])
	            			weight_output+="下等";
	            		else if(rWeight < IPAD.weight_boy[index][1]){
	            			weight_output+="中下等";
	            		}
	            		else if(rWeight < IPAD.weight_boy[index][2]){
	            			weight_output+="中等";
	            		}
	            		else if(rWeight < IPAD.weight_boy[index][3]){
	            			weight_output+="中上等";
	            		}
	            		else if(rWeight < IPAD.weight_boy[index][4]){
	            			weight_output+="上等";
	            		}
	            		else
	            			weight_output+="上上等";
     		
	            		if(rHeight < IPAD.height_boy[index][0])
	            			height_output+="下等";
	            		else if(rHeight < IPAD.height_boy[index][1]){
	            			height_output+="中下等";
	            		}
	            		else if(rHeight < IPAD.height_boy[index][2]){
	            			height_output+="中等";
	            		}
	            		else if(rHeight < IPAD.height_boy[index][3]){
	            			height_output+="中上等";
	            		}
	            		else if(rHeight < IPAD.height_boy[index][4]){
	            			height_output+="上等";
	            		}
	            		else
	            			height_output+="上上等";
	            		
	            		if(cYear < 7){
	            			if(rBreast < IPAD.breast_boy[index][0])
		            			breast_output+="下等";
		            		else if(rBreast < IPAD.breast_boy[index][1]){
		            			breast_output+="中下等";
		            		}
		            		else if(rBreast < IPAD.breast_boy[index][2]){
		            			breast_output+="中等";
		            		}
		            		else if(rBreast < IPAD.breast_boy[index][3]){
		            			breast_output+="中上等";
		            		}
		            		else
		            			breast_output+="上等";
	            		}
	            		else{
	            			breast_output+="无";
	            		}
	            	}	
	            	else{
	            		if(rWeight < IPAD.weight_girl[index][0])
	            			weight_output+="下等";
	            		else if(rWeight < IPAD.weight_girl[index][1]){
	            			weight_output+="中下等";
	            		}
	            		else if(rWeight < IPAD.weight_girl[index][2]){
	            			weight_output+="中等";
	            		}
	            		else if(rWeight < IPAD.weight_girl[index][3]){
	            			weight_output+="中上等";
	            		}
	            		else if(rWeight < IPAD.weight_girl[index][4]){
	            			weight_output+="上等";
	            		}
	            		else
	            			weight_output+="上上等";
     		
	            		if(rHeight < IPAD.height_girl[index][0])
	            			height_output+="下等";
	            		else if(rHeight < IPAD.height_girl[index][1]){
	            			height_output+="中下等";
	            		}
	            		else if(rHeight < IPAD.height_girl[index][2]){
	            			height_output+="中等";
	            		}
	            		else if(rHeight < IPAD.height_girl[index][3]){
	            			height_output+="中上等";
	            		}
	            		else if(rHeight < IPAD.height_girl[index][4]){
	            			height_output+="上等";
	            		}
	            		else
	            			height_output+="上上等";
	            		
	            		if(cYear < 7){
	            			if(rBreast < IPAD.breast_girl[index][0])
		            			breast_output+="下等";
		            		else if(rBreast < IPAD.breast_girl[index][1]){
		            			breast_output+="中下等";
		            		}
		            		else if(rBreast < IPAD.breast_girl[index][2]){
		            			breast_output+="中等";
		            		}
		            		else if(rBreast < IPAD.breast_girl[index][3]){
		            			breast_output+="中上等";
		            		}
		            		else
		            			breast_output+="上等";
	            		}
	            		else{
	            			breast_output+="无";
	            		}
	            	}	*/
	            
	            	height_output=height_output+"\n"+weight_output+"\n"+breast_output;
	            	height_text.setText(height_output);
	            	//weight_text.setText(weight_output);
	            //	breast_text.setText(breast_output);
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
         HttpPost httpget = new HttpPost("http://"+IPAD.ip+"/babycare/getpara.php");                
         List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	     postParameters.add(new BasicNameValuePair("usuario", user));
	     postParameters.add(new BasicNameValuePair("task", "onerecord"));
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
    
    try{
         jArray = new JSONArray(result);
         JSONObject json_data=null;
        
         for(int i=0;i<jArray.length();i++){
        	  json_data = jArray.getJSONObject(i);
        	  rHeight=json_data.getDouble("height");
        	  rWeight=json_data.getDouble("weight");
        	  rBreast=json_data.getDouble("breast");
              rYear=json_data.getInt("year");
              rMonth=json_data.getInt("month");
              rDay=json_data.getInt("day");
         }

    }catch(JSONException e1){
    	 Log.e("log_tag", "Error converting result "+e1.toString());
    } catch (ParseException e1) {
         e1.printStackTrace();
    } 
}

/**
 * 将相应的Activity转换成View对象
 */
public void initView(){
	//oneView=getViews(MainActivity.class,"one");
	//twoView=getViews(EmergencyList.class,"one");
	//threeView=getViews(CopyOfTabs.class,"one");
	 //实例化标题栏按钮并设置监听  
    titleBtn = (ImageButton) findViewById(R.id.title_btn);  
    titleBtn.setOnClickListener(new OnClickListener() {  
        @Override  
        public void onClick(View v) {  
            titlePopup.show(v);  
        }  
    });  
              
    //实例化标题栏弹窗  
    titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
}



  private void initData(){  
        //给标题栏弹窗添加子类  
        titlePopup.addAction(new ActionItem(this, "修改信息", R.drawable.mm_title_btn_compose_normal));  
        titlePopup.addAction(new ActionItem(this, "记录日志", R.drawable.mm_title_btn_compose_normal));  
        titlePopup.addAction(new ActionItem(this, "上传图片", R.drawable.mm_title_btn_compose_normal));  
        titlePopup.addAction(new ActionItem(this, "修改密码",  R.drawable.mm_title_btn_compose_normal)); 
        titlePopup.addAction(new ActionItem(this, "退出账号",  R.drawable.mm_title_btn_compose_normal)); 
        titlePopup.setItemOnClickListener(new TitlePopup.OnItemOnClickListener() {
			
			@Override
			public void onItemClick(ActionItem item, int position) {
				// TODO Auto-generated method stub
				if(position == 0){
					//Intent i=new Intent(ViewPage.this, BabyInfo.class);
 					//startActivity(i); 
					Toast.makeText(getBaseContext(), "那天心情好再写" ,Toast.LENGTH_LONG).show();
				}
				if(position == 1){
					Intent i=new Intent(MainActivity.this, WriteDiary.class);
 					startActivity(i); 
				}
				if(position == 2){
					Intent i=new Intent(MainActivity.this, UploadPic.class);
 					startActivity(i); 
				}
				
				if(position == 4){
					Login.instance.sp.edit().putBoolean("AUTO_ISCHECK", false).commit(); 
					finish();
				}
				if(position == 3){
					Toast.makeText(getBaseContext(), "那天心情好再写" ,Toast.LENGTH_LONG).show();
				}
			}
		});
        	
      
    }  


}