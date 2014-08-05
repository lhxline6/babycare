package com.example.babycare;

import com.example.babycare.library.ListItem;
import com.example.babycare.library.MyAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;



































import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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











import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
/*��ӭ��Ļ*/
public class Records extends Activity  {
	TextView settitle, logoff;
	//static final private int STATE_ID = Menu.FIRST;
    static final private int SETTINGS_ID = Menu.FIRST + 1;
    static final private int RENAME_ID = Menu.FIRST + 2;
    static final private int MANAGE_ID = Menu.FIRST + 3;

    public static Records instance = null;   
    private ArrayList<ListItem> list_GroupItem;
    private ListView listView;
   
    
    ImageButton imgBtn01;
    Button bt1,bt2,bt3;
    private MyAdapter mAdapter_ListGroup;
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
		 	setContentView(R.layout.am);
		 	instance = this;

			listView = (ListView)findViewById(R.id.listView1);
			listView.setDivider(null);
	        list_GroupItem = new ArrayList<ListItem>();

			new Thread(runnable).start();
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
	            	mAdapter_ListGroup = new MyAdapter(getBaseContext(), list_GroupItem);

	 	 			mAdapter_ListGroup.AddType(R.layout.list_item);

	 	 			listView.setAdapter(mAdapter_ListGroup);
	                break;
	            }
	        }
	    };
 
	 private void geneItems(String user) {
		 String result = null;
		 JSONArray jArray;
		 StringBuilder sb=null;
		 InputStream is = null;
		 //http get
	    try{
	    	 HttpClient httpclient = new DefaultHttpClient();
	         HttpPost httpget = new HttpPost("http://"+IPAD.ip+"/babycare/getpara.php");                
	         List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		     postParameters.add(new BasicNameValuePair("usuario", user));
		     postParameters.add(new BasicNameValuePair("task", "allrecords"));
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

	              ListItem a = new ListItem(0, getHashMap(json_data.getDouble("height"),json_data.getDouble("weight"),json_data.getDouble("breast"),json_data.getInt("year"),json_data.getInt("month"),json_data.getInt("day")));
	              list_GroupItem.add(a);
	         }

	    }catch(JSONException e1){
	    	 Log.e("log_tag", "Error converting result "+e1.toString());
	    //      Toast.makeText(getBaseContext(), "No id Found" ,Toast.LENGTH_LONG).show();
	    } catch (ParseException e1) {
	         e1.printStackTrace();
	    } 
   }

	 
	 
	 
	    
	    private HashMap<Integer, Object> getHashMap(Double rHeight,Double rWeight,Double rBreast, int Year,int Month,int Day) {	
			HashMap<Integer, Object> map1 = new HashMap<Integer, Object>();
			map1.put(R.id.date, Year+"."+Month+"."+Day+":");
			map1.put(R.id.para, "身高:"+rHeight+"\t体重:"+rWeight+"\t头围:"+rBreast);
			String height_output="级别:";
			String weight_output="\t级别:";
			String breast_output="\t级别:";
			int index=0;
			
			int cYear,cMonth,mDay;
			if(IPAD.month <= Year){
				cYear = Year - IPAD.year; 
				cMonth = Month - IPAD.month;
			}
	        else{
	        	cYear = Year - IPAD.year - 1; 
	        	cMonth = Month - IPAD.month + 12;
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
	        }
	        else{
	        	 map1.put(R.id.compare, height_output+weight_output+breast_output);
	        	 return map1;
	        }
	       if(index<=0) index=0;
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
        	}	
			map1.put(R.id.compare, height_output+weight_output+breast_output);
			return map1;
		}
	    
	
	
	    
	   
	    
		
		/*
		 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
		        super.onCreateOptionsMenu(menu);
		   //     menu.add(0, STATE_ID, 0, R.string.state).setShortcut('0', 'b');
		        menu.add(0, SETTINGS_ID, 0, R.string.settings).setShortcut('1', 'c');
		        menu.add(0, MANAGE_ID, 0, R.string.manage).setShortcut('2', 'd');

		        return true;
		    }
		
		 @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		        switch (item.getItemId()) {

		        case SETTINGS_ID:  
		        	Intent i1=new Intent(OpenClose.this, Settings.class);
					
					startActivity(i1); 
		      
		            return true;
		        case RENAME_ID:        
		            return true;
		        case MANAGE_ID:
		            finish();
		            return true;
		        }
		        return super.onOptionsItemSelected(item);
		    } 
		    */
		 
		 private void sendMessage(String URL,String user,String socket,String state,String name){
		   	 try{
		    	     HttpClient httpclient = new DefaultHttpClient();
		    	     HttpPost httpget = new HttpPost(URL);                
		    	     List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		    	     postParameters.add(new BasicNameValuePair("usuario", user));
		 	         postParameters.add(new BasicNameValuePair("socket", socket));
		 	         if(state!=null)
		 	         postParameters.add(new BasicNameValuePair("state", state));
		 	         if(name!=null)
		 	        	 postParameters.add(new BasicNameValuePair("name", name));
		 	         UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters,"UTF-8");
		 	         httpget.setEntity(formEntity);    
		 	         httpclient.execute(httpget); 
		 	         }catch(Exception e){
		 	        	 Log.e("log_tag", "Error in http connection"+e.toString());
		 	         }
		   	
		   }
		 
		 
	}
