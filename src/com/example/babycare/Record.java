

package com.example.babycare;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class Record extends Activity
{
	
	 Button bRecord,Age,bCancel;
	 private int index;  
	 private int mYear,mMonth,mDay;
	 private EditText weight,height,breast;
	 private String[] months = new String[] {
			 "0","1","2","3","4","5","6","7","8","9","10","11","12","15","18","21","24","27","30","33","36","39","42","45","48","51","54","57","60","63","66","69","72","75","78","81"
		};
    @Override
	protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        height = (EditText) findViewById(R.id.shenggao);
        weight = (EditText) findViewById(R.id.tizhong);
        breast = (EditText) findViewById(R.id.xiongwei);
        
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        Age = (Button) findViewById(R.id.age);
        Age.setText(mYear+"-"+(mMonth+1)+"-"+mDay);
        Age.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                showDialog(1);
            }
        });
        
        bRecord= (Button) findViewById(R.id.button1);
        bRecord.setOnClickListener(new View.OnClickListener(){ 
        	@Override
			public void onClick(View view){
        		ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
        		NetworkInfo info = cManager.getActiveNetworkInfo();   
        		if (info != null && info.isAvailable()){
        			  new Thread(runnable).start();
        			  
        			  MainActivity.instance.finish();  
        			  IPAD.tab1=0;
        			  Intent i=new Intent(Record.this, MainActivity.class);
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
        		        int tmpindex;
        		        
        		        
        		        if(tmp<=12)
        		        	tmpindex = tmp;
        		        else
        		        	tmpindex = (tmp - 12)/3 + 12;
        		        
        		        if(tmpindex < 0)
        		        	tmpindex = 0;
        		        
        		        if(tmpindex>=36)
        		        	tmpindex=35;
        			  Toast.makeText(getBaseContext(), "Start"+months[tmpindex],Toast.LENGTH_LONG).show();
        		//	  Toast.makeText(getBaseContext(), "记录成功！" ,Toast.LENGTH_LONG).show();
        			  startActivity(i); 
        			  finish();
   				}else{   
        		       //do something   
        		       //不能联网   
        			  Toast.makeText(getBaseContext(), "当前未连接网络，请先连接后再尝试" ,Toast.LENGTH_LONG).show();
        		       // return false;   
        		  }   
        		
        	}
        });
        
        
        bCancel= (Button) findViewById(R.id.button2);
        bCancel.setOnClickListener(new View.OnClickListener(){ 
        	@Override
			public void onClick(View view){
        		finish();
        	}
        });
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
                return new DatePickerDialog(this,
                		beginDateSetListener,
                            mYear, mMonth, mDay);
    }

      
    
    DatePickerDialog beginDate;
    private DatePickerDialog.OnDateSetListener beginDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
           // beginDate.setTitle("起始日期");
            Age.setText(mYear+"-"+mMonth+"-"+mDay);
            
        }
    };
    
    
    private Runnable runnable = new Runnable(){
        @Override
        public void run() {     	 
        	sendItems(IPAD.user); 	
        }
    };
    
    
    
    private void sendItems(String user) {
    	
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
        int tmpindex,tmpindexforhead=0;
        
        
        if(tmp<=12){
        	tmpindex = tmp;
        	tmpindexforhead = tmp;
        }
        else if(tmp<=36){
        	tmpindex = (tmp - 12)/3 + 12;
        	tmpindexforhead  = (tmp - 12)/3 + 12;
        }
        else if(tmp<=72){
        	tmpindex = (tmp - 12)/3 + 12;
        	tmpindexforhead  = (tmp - 36)/6 + 36;
        }
        else 
        	tmpindex = (tmp - 12)/3 + 12;
        if(tmpindex < 0)
        	tmpindex = 0;
        
        //if(tmpindex>=36)
        //	tmpindex=35;
		 //http get
	    try{
	    	if(tmpindex<36){
				 HttpClient httpclient = new DefaultHttpClient();
				 HttpPost httpget = new HttpPost("http://"+IPAD.ip+"/babycare/sendrecords.php");                
				 List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				 postParameters.add(new BasicNameValuePair("usuario", user));
				 postParameters.add(new BasicNameValuePair("weight", weight.getText().toString()));
				 postParameters.add(new BasicNameValuePair("height", height.getText().toString()));
				 
				 postParameters.add(new BasicNameValuePair("breast", breast.getText().toString()));
				 postParameters.add(new BasicNameValuePair("year",String.valueOf(mYear)));
				 postParameters.add(new BasicNameValuePair("month",String.valueOf(mMonth)));
				 postParameters.add(new BasicNameValuePair("day", String.valueOf(mDay)));				   
				 postParameters.add(new BasicNameValuePair("index", String.valueOf(tmpindex)));
				 postParameters.add(new BasicNameValuePair("indexforhead", String.valueOf(tmpindexforhead)));
				 UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters,"UTF-8");
				 httpget.setEntity(formEntity);
				 HttpResponse response = httpclient.execute(httpget);
				 HttpEntity entity = response.getEntity();   
				 entity.getContent();
	         }
	         //Toast.makeText(getBaseContext(), "No id Found" ,Toast.LENGTH_LONG).show();
	    }catch(Exception e){
	         Log.e("log_tag", "Error in http connection"+e.toString());
	    }
	
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
    
    
    private class ButtonOnClick implements DialogInterface.OnClickListener
    {
       private int index; // 表示选项的索引
       public ButtonOnClick(int index)
       {
           this.index = index;
       }


       @Override
       public void onClick(DialogInterface dialog,int which)
       {
           // which表示单击的按钮索引，所有的选项索引都是大于0，按钮索引都是小于0的。
           if (which >= 0)
           {
              //如果单击的是列表项，将当前列表项的索引保存在index中。
              //如果想单击列表项后关闭对话框，可在此处调用dialog.cancel()
              //或是用dialog.dismiss()方法。
              index = which;
           }
           else
           {
              //用户单击的是【确定】按钮
              if (which == DialogInterface.BUTTON_POSITIVE)
              {
            
              }
              //用户单击的是【取消】按钮
              else if (which == DialogInterface.BUTTON_NEGATIVE)
              {
                  Toast.makeText(Record.this, "你没有选择任何东西",
                          Toast.LENGTH_LONG);
              }
           }
       }
    }

   
}

