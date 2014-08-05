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


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Diary extends Activity {
	
	static final private int SETTINGS_ID = Menu.FIRST + 1;
	static final private int REFRESH_ID = Menu.FIRST + 2;
	private ArrayList<ArrayList<String>> contextList = new ArrayList<ArrayList<String>>();
	private ArrayList<String> titleList = new ArrayList<String>();
	
	public static Diary instance = null;   
	  
	           
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        new Thread(runnable).start();
        ImageView imgIndicator = new ImageView(Diary.this);
        imgIndicator.setBackgroundResource(R.drawable.chengzhangrizhi);
      
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
	            	 ExpandableListView list = new ExpandableListView(getBaseContext());
	                 ExpandableListAdapter mAdapter = new MyExpandableListAdapter(titleList, contextList);
	                 list.setAdapter(mAdapter);

	                 list.setCacheColorHint(0x00000000);
	                 list.setSelector(new ColorDrawable(Color.TRANSPARENT));
	                 list.setGroupIndicator(null);

	                 setContentView(list);
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
	         HttpPost httpget = new HttpPost("http://"+IPAD.ip+"/babycare/getdiary.php");                
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
	        	  titleList.add(json_data.getString("title"));
	        	  ArrayList<String> itemList = new ArrayList<String>();
	        	  itemList.add(json_data.getString("context"));
	        	  contextList.add(itemList);
	         }
	    }catch(JSONException e1){
	    	 Log.e("log_tag", "Error converting result "+e1.toString());
	    } catch (ParseException e1) {
	         e1.printStackTrace();
	    } 
  }

    
    
    

    private class MyExpandableListAdapter extends BaseExpandableListAdapter {
        private ArrayList<String> groupList;
        private ArrayList<ArrayList<String>> childList;

        MyExpandableListAdapter(ArrayList<String> groupList, ArrayList<ArrayList<String>> childList) {
            this.groupList = groupList;
            this.childList = childList;
        }

        @Override
		public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
        }

        private int selectedGroupPosition = -1;
        private int selectedChildPosition = -1;

        public void setSelectedPosition(int selectedGroupPosition, int selectedChildPosition) {
            this.selectedGroupPosition = selectedGroupPosition;
            this.selectedChildPosition = selectedChildPosition;
        }

        @Override
		public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
		public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
		public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView textView = null;
            if (convertView == null) {
                textView = new TextView(Diary.this);
                textView.setPadding(32, 10, 0, 10);
                textView.setTextSize(16);
                convertView = textView;
            } else {
                textView = (TextView) convertView;
            }

            textView.setText(getChild(groupPosition, childPosition).toString());

            if (groupPosition == selectedGroupPosition) {
                if (childPosition == selectedChildPosition) {
                	 Log.e("log_tag",selectedChildPosition+ " "+selectedGroupPosition );
              //  	Intent i=new Intent(Diary.this, EmergencyContent.class);
    				
    		//		i.putExtra("group",selectedGroupPosition);
    		//		i.putExtra("child",selectedChildPosition);
    		//		startActivity(i); 
                    textView.setBackgroundColor(0xffb6ddee);
                } else {
                    textView.setBackgroundColor(Color.TRANSPARENT);
                }
            }

            textView.setOnClickListener(new OnClickListener() {
                @Override
				public void onClick(View v) {
                    setSelectedPosition(groupPosition, childPosition);
                    notifyDataSetChanged();
                }
            });
            return textView;
        }

        @Override
		public Object getGroup(int groupPosition) {
            return groupList.get(groupPosition);
        }

        @Override
		public int getGroupCount() {
            return groupList.size();
        }

        @Override
		public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            LinearLayout cotain = new LinearLayout(Diary.this);
            cotain.setPadding(5, 20, 0, 20);
            cotain.setGravity(Gravity.CENTER_VERTICAL);

            ImageView imgIndicator = new ImageView(Diary.this);
            TextView textView = new TextView(Diary.this);
            textView.setText(getGroup(groupPosition).toString());
            textView.setTextSize(17);
            textView.setPadding(15, 0, 0, 0);

            if (isExpanded) {
                imgIndicator.setBackgroundResource(R.drawable.diary);
                
            } else {
                imgIndicator.setBackgroundResource(R.drawable.diary);
             
            }
            cotain.addView(imgIndicator);
            cotain.addView(textView);
            return cotain;
        }

        @Override
		public boolean hasStableIds() {
            return true;
        }

        @Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	        super.onCreateOptionsMenu(menu);
	   //     menu.add(0, STATE_ID, 0, R.string.state).setShortcut('0', 'b');
	        menu.add(0, SETTINGS_ID, 0, "写日记").setShortcut('1', 'c');
	        menu.add(0, REFRESH_ID, 0, "刷新").setShortcut('2', 'd');
	       

	        return true;
	    }
	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        case SETTINGS_ID:  
        	Intent i1=new Intent(Diary.this, WriteDiary.class);
			startActivity(i1); 
            return true;
        case REFRESH_ID:
        	IPAD.tab1=2;
        	IPAD.tab2=0;
        	Intent i2=new Intent(Diary.this, Diary.class);
			startActivity(i2); 
			return true;
        }
        return super.onOptionsItemSelected(item);
	 }
}