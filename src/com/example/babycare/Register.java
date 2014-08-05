

package com.example.babycare;


import java.util.ArrayList;
import java.util.Calendar;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends Activity
{
	 private Button bRegister,Age;
	 private RadioGroup radgroup;
	 private int sex;
	 private int index;  
	 private int mYear,mMonth,mDay;
	 private EditText username,password,name;
    @Override
	protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        radgroup = (RadioGroup) findViewById(R.id.radioGroup);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        Age = (Button) findViewById(R.id.age);
        Age.setText(mYear+"-"+mMonth+"-"+mDay);
        Age.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                showDialog(1);
            }
        });
        
        bRegister= (Button) findViewById(R.id.button1);
        bRegister.setOnClickListener(new View.OnClickListener(){ 
        	@Override
			public void onClick(View view){
        		ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
        		NetworkInfo info = cManager.getActiveNetworkInfo();   
        		if (info != null && info.isAvailable()){
        			for(int i = 0;i < radgroup.getChildCount();i++)  
                    {  
                        RadioButton rd = (RadioButton) radgroup.getChildAt(i);  
                        if(rd.isChecked())  
                        {  
                        	if(rd.getText().toString().equals("男"))
                        		sex=1;
                        	else
                        		sex=2;
                            break;  
                        }  
                    }  
        				
        			new Thread(runnable).start();
        			  
        			 // Intent i=new Intent(Register.this, Tabs.class);
        			 // startActivity(i); 
        			finish();
   				}else{   
        		       //do something   
        		       //不能联网   
        			  Toast.makeText(getBaseContext(), "当前未连接网络，请先连接后再尝试" ,Toast.LENGTH_LONG).show();
        		       // return false;   
        		  }   
        		
        	}
        });
       /* 
        btnchange= (Button) findViewById(R.id.btnpost);  
        radgroup = (RadioGroup) findViewById(R.id.radioGroup);  
        //第一种获得单选按钮值的方法  
        //为radioGroup设置一个监听器:setOnCheckedChanged()  
          
        btnchange.setOnClickListener(new OnClickListener() {  
              
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                for(int i = 0;i < radgroup.getChildCount();i++)  
                {  
                    RadioButton rd = (RadioButton) radgroup.getChildAt(i);  
                      
                    if(rd.isChecked())  
                    {  
                        Toast.makeText(getApplicationContext(), "点击提交按钮,获取你选择的是:"+rd.getText(), Toast.LENGTH_LONG).show();  
                        break;  
                    }  
                }  
            }  
        });
        */
        /*
      
        Age.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	// private ButtonOnClick buttonOnClick = new ButtonOnClick(1);
            	 AlertDialog ad = new AlertDialog.Builder(Record.this)
            	 .setTitle("年龄段")
            	 .setSingleChoiceItems(ages,0,new DialogInterface.OnClickListener(){
            		 @Override
            		 public void onClick(DialogInterface dialog, int which) {
 						// TODO Auto-generated method stub
            			 index = which;
            		 }  
            	 })
            	 .setPositiveButton("确定",new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Age.setText(index+" ");
					}  	
            	 })
            	 .setNegativeButton("取消", new DialogInterface.OnClickListener(){

 					@Override
 					public void onClick(DialogInterface dialog, int which) {
 						// TODO Auto-generated method stub
 						
 					}  	
             	 })
            	 .create();  
  	    	  // areaCheckListView = ad.getListView();  
            	 ad.show();    	 
            }
        });*/
        //edit= (EditText) findViewById(R.id.editText1);
      	//text.setText("添加活动");
      		
   //     TextView text = (TextView) findViewById(R.id.titlefortime);
//		text.setText("添加活动");
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
		 //http get
	    try{
	    	 HttpClient httpclient = new DefaultHttpClient();
	         HttpPost httpget = new HttpPost("http://"+IPAD.ip+"/babycare/register.php");                
	         List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		    // postParameters.add(new BasicNameValuePair("usuario", user));
		     postParameters.add(new BasicNameValuePair("username", username.getText().toString()));
		     postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
		     postParameters.add(new BasicNameValuePair("name", name.getText().toString()));
		     postParameters.add(new BasicNameValuePair("sex", String.valueOf(sex)));
		     postParameters.add(new BasicNameValuePair("year",String.valueOf(mYear)));
		     postParameters.add(new BasicNameValuePair("month",String.valueOf(mMonth)));
		     postParameters.add(new BasicNameValuePair("day", String.valueOf(mDay)));
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
            	
                  //显示用户选择的是第几个列表项。
               /*   final AlertDialog ad = new AlertDialog.Builder(
                		  Record.this).setMessage(
                          "你选择的地区是：" + index + ":" + areas[index]).show();
                  //五秒钟后自动关闭。
             /     Handler hander = new Handler();
                  Runnable runnable = new Runnable()
                  {
                     @Override
                     public void run()
                     {
                         ad.dismiss();
                     }
                  };
                  hander.postDelayed(runnable, 5 * 1000);*/
              }
              //用户单击的是【取消】按钮
              else if (which == DialogInterface.BUTTON_NEGATIVE)
              {
                  Toast.makeText(Register.this, "你没有选择任何东西",
                          Toast.LENGTH_LONG);
              }
           }
       }
    }

    /*
    
    private void showDialog_Layout(Context context,final String item_name) {  
        LayoutInflater inflater = LayoutInflater.from(this);  
        final View textEntryView = inflater.inflate(  
                R.layout.record, null);  
        final AlertDialog.Builder builder = new AlertDialog.Builder(context); 
        builder.setCancelable(false);  
       // builder.setIcon(R.drawable.icon);  
        builder.setTitle("选择新分组");  
        builder.setView(textEntryView);  

        sp=(Spinner)textEntryView.findViewById(R.id.spinner1);  
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spanner_list);
	
	
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setSelection(shuzi.get(item_name),true);

        sp.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    @Override
					public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                    	 selected = parent.getItemAtPosition(position).toString();  
                    }

                    @Override
					public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        builder.setPositiveButton("确定",  
                new DialogInterface.OnClickListener() {  
                    @Override
					public void onClick(DialogInterface dialog, int whichButton) {  
                    	if(xiaobiao.get(item_name).equals(selected)){
                    		finish();
                    	}
                      // sendMessage("http://"+IPAD.ip+"/droidlogin/setgroup.php",IPAD.user,xiaobiao.get(item_name),selected,item_name);
        		    	//Intent i1=new Intent(SetGroup.this, SetGroup.class);
        			//	startActivity(i1); 
        	         //   finish();
                    }  
                });  
        builder.setNegativeButton("取消",  
                new DialogInterface.OnClickListener() {  
                    @Override
					public void onClick(DialogInterface dialog, int whichButton) {  
                    }  
                });  
        builder.show();  
    }  
*/
}

