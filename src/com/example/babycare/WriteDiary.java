

package com.example.babycare;


import java.util.ArrayList;
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
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class WriteDiary extends Activity
{
	 private Button bSubmit,bCancel,Age;

	 private int mYear,mMonth,mDay;
	 private EditText context,title;
    @Override
	protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_diary);
        title = (EditText) findViewById(R.id.title);
        context = (EditText) findViewById(R.id.context);
       
        
        bSubmit= (Button) findViewById(R.id.button1);
        bSubmit.setOnClickListener(new View.OnClickListener(){ 
        	@Override
			public void onClick(View view){
        		ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
        		NetworkInfo info = cManager.getActiveNetworkInfo();   
        		if (info != null && info.isAvailable()){
        		
        			new Thread(runnable).start();
        		
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
        		
        			Intent mIntent = new Intent();
        			 mIntent.putExtra("change01", "cancel");  
        		        //.putExtra("change02", "2000");  
        		        // 设置结果，并进行传送  
        		        setResult(0, mIntent);  
        		//	Intent i1=new Intent(WriteDiary.this, Tabs.class);
        		//	startActivity(i1); 
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
        	Diary.instance.finish();  
        	IPAD.tab1=2;
        	IPAD.tab2=0;
            Intent intent = new Intent(WriteDiary.this, Diary.class);  
            startActivity(intent); 
        	
        	
        	//Intent mIntent = new Intent();
  			 //mIntent.putExtra("change01", "submit");  
  		        //.putExtra("change02", "2000");  
  		        // 设置结果，并进行传送  
  		       // setResult(0, mIntent);
   			finish();
        }
    };
    
    
    
    private void sendItems(String user) {
		 //http get
	    try{
	    	 HttpClient httpclient = new DefaultHttpClient();
	         HttpPost httpget = new HttpPost("http://"+IPAD.ip+"/babycare/senddiary.php");                
	         List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	         postParameters.add(new BasicNameValuePair("username", user));
		     postParameters.add(new BasicNameValuePair("title", title.getText().toString()));
		     postParameters.add(new BasicNameValuePair("context", context.getText().toString()));

	         UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters,"UTF-8");
	         httpget.setEntity(formEntity);
	         HttpResponse response = httpclient.execute(httpget);
	         HttpEntity entity = response.getEntity();   
	         entity.getContent();
	         Toast.makeText(getBaseContext(), "No id Found" ,Toast.LENGTH_LONG).show();
	    }catch(Exception e){
	         Log.e("log_tag", "Error in http connection"+e.toString());
	    }
	
   }
    
}

