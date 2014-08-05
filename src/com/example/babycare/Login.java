package com.example.babycare;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

import com.example.babycare.library.Httppostaux;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class Login extends Activity {
    /** Called when the activity is first created. */
	public SharedPreferences sp;  
	private EditText Euser;
	private EditText pass;
	private Button blogin;
	private TextView registrar;
	private CheckBox jizhu = null,denglu = null;
    com.example.babycare.library.Httppostaux post;

    String URL_connect="http://"+IPAD.ip+"/babycare/acces.php";
    int flag;
    
    boolean result_back;
    private ProgressDialog pDialog;
    public static Login instance = null; 
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        instance = this;
        post=new Httppostaux();
        
        //获得实例对象  
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE); 
        
        Euser= (EditText) findViewById(R.id.edusuario);
        pass= (EditText) findViewById(R.id.edpassword);
        blogin= (Button) findViewById(R.id.Blogin);
        registrar=(TextView) findViewById(R.id.link_to_register);
        jizhu=(CheckBox) findViewById(R.id.jizhu);
        denglu=(CheckBox) findViewById(R.id.denglu);

      //判断记住密码多选框的状态  
        if(sp.getBoolean("ISCHECK", false))  
          {  
            //设置默认是记录密码状态  
        	jizhu.setChecked(true);  
        	Euser.setText(sp.getString("USER_NAME", ""));  
        	pass.setText(sp.getString("PASSWORD", ""));  
            //判断自动登陆多选框状态  
            if(sp.getBoolean("AUTO_ISCHECK", false))  
            {  
                   //设置默认是自动登录状态  
            	denglu.setChecked(true);
            	ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
        		NetworkInfo info = cManager.getActiveNetworkInfo();   
        		  if (info != null && info.isAvailable()){
        			  new asynclogin().execute(sp.getString("USER_NAME", ""),sp.getString("PASSWORD", ""));  
        		  }else{   
        		//	  pDialog.dismiss();
        			  Toast.makeText(getBaseContext(), "当前未连接网络，请先连接后再尝试" ,Toast.LENGTH_LONG).show();
        		  }        
            } 
          }  
        
        
       // geneItems();
        blogin.setOnClickListener(new View.OnClickListener(){
       
        	@Override
			public void onClick(View view){

        		String usuario=Euser.getText().toString();
        		String passw=pass.getText().toString();
        		
        		ConnectivityManager cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
        		NetworkInfo info = cManager.getActiveNetworkInfo();   
        		  if (info != null && info.isAvailable()){   
        		       //do something   
        			  if( checklogindata( usuario , passw )==true){
              			new asynclogin().execute(usuario,passw);        		               
              		}else{
              			err_login();
              		} 
        		  }else{   
        		//	  pDialog.dismiss();
        			  Toast.makeText(getBaseContext(), "当前未连接网络，请先连接后再尝试" ,Toast.LENGTH_LONG).show();
        		       // return false;   
        		  }   	
        	}
        	});
        
        registrar.setOnClickListener(new View.OnClickListener(){
            
        	@Override
			public void onClick(View view){
        		Intent i=new Intent(Login.this, Register.class);
				startActivity(i); 
        		 		
        		}        	
    		});
        
        
        jizhu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                if(isChecked){ 
                	sp.edit().putBoolean("ISCHECK", true).commit();
                }else{ 
                	sp.edit().putBoolean("ISCHECK", false).commit();
                } 
            } 
        }); 
        
        denglu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                if(isChecked){ 
                	sp.edit().putBoolean("AUTO_ISCHECK", true).commit(); 
                }else{ 
                	 sp.edit().putBoolean("AUTO_ISCHECK", false).commit();  
                } 
            } 
        });
        
       
                
    }

    public void err_login(){
    	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"用户名或密码错误", Toast.LENGTH_SHORT);
 	    toast1.show();    	
    }
    
    public boolean loginstatus(String username ,String password ) {
    	int logstatus=-1;  	

    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
     		
		postparameters2send.add(new BasicNameValuePair("usuario",username));
		postparameters2send.add(new BasicNameValuePair("password",password));

		JSONArray jdata=post.getserverdata(postparameters2send, URL_connect);
      		
		SystemClock.sleep(950);
		    		
		    //�ǿ�
    	if (jdata!=null && jdata.length() > 0){

    		JSONObject json_data; 
			try {
				json_data = jdata.getJSONObject(0); 
				 logstatus=json_data.getInt("logstatus");
				 Log.e("loginstatus","logstatus= "+logstatus);
			} catch (JSONException e) {
				e.printStackTrace();
			}		            
    		 if (logstatus==0){
    			 Log.e("loginstatus ", "invalido");
    			 return false;
    		 }
    		 else{
    			 Log.e("loginstatus ", "valido");
    			 return true;
    		 }
	 	}else{
    			 Log.e("JSON  ", "ERROR");
	    		return false;
	 	}
    }
    
    public boolean checklogindata(String username ,String password ){
	    if 	(username.equals("") || password.equals("")){
	    	Log.e("Login ui", "checklogindata user or pass error");
	    return false;
	    
	    }else{
	    	
	    	return true;
	    } 
}           
    
    
    class asynclogin extends AsyncTask< String, String, String > {
    	 
    	String user,pass;
        @Override
		protected void onPreExecute() {

            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("登陆中....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
		@Override
		protected String doInBackground(String... params) {
			user=params[0];
			pass=params[1];
            
    		if (loginstatus(user,pass)==true){    
			//if(user.equalsIgnoreCase("5110309586") && pass.equalsIgnoreCase("123")){
    			if(jizhu.isChecked())  
                {  
                 //记住用户名、密码、  
                  Editor editor = sp.edit();  
                  editor.putString("USER_NAME", user);  
                  editor.putString("PASSWORD",pass);  
                  editor.commit();  
                }  
				geneItems(user);
    			return "ok"; //login valido
    			
    		}else{    		
    			return "err"; //login invalido     	          	  
    		}
        	
		}
       
        @Override
		protected void onPostExecute(String result) {
           pDialog.dismiss();
           Log.e("onPostExecute=",""+result);
           
           if (result.equals("ok")){
        	   	IPAD.user = Euser.getText().toString();
        	   	
				Intent i=new Intent(Login.this, MainActivity.class);
				startActivity(i); 
				
            }else{
            	err_login();
            }
	}
        }
    
  
    private void geneItems(String user) {
		 String result = null;
		 JSONArray jArray;
		 StringBuilder sb=null;
		 InputStream is = null;
		 int a=0;
		 //http get
	    try{
	    	 HttpClient httpclient = new DefaultHttpClient();
	         HttpPost httpget = new HttpPost("http://"+IPAD.ip+"/babycare/getinfo.php");                
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
	  
	    try{
	         jArray = new JSONArray(result);
	         JSONObject json_data=null;
	         for(int i=0;i<jArray.length();i++){
	              json_data = jArray.getJSONObject(i);
	              IPAD.name = json_data.getString("name");
	              IPAD.year = json_data.getInt("year");
	              IPAD.month = json_data.getInt("month");
	              IPAD.day = json_data.getInt("day");
	              IPAD.sex = json_data.getInt("sex");
	              IPAD.isrecord = json_data.getInt("isrecord");
	         }
	    }catch(JSONException e1){
	    	 Log.e("log_tag", "Error converting result "+e1.toString());
	          Toast.makeText(getBaseContext(), "No id Found" ,Toast.LENGTH_LONG).show();
	    } catch (ParseException e1) {
	         e1.printStackTrace();
	    }
	    
    }
    
  
    
 
    }
  

    
 

