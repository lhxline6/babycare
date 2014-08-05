package com.example.babycare;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
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

import com.example.babycare.library.ChartDrawing;
import com.example.babycare.library.ChartDrawing1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;

public class WeightChart extends Activity {
	
	private GraphicalView lChart,sChart;
	ChartDrawing1 lineChart;
	private ChartDrawing scatterChart;
	private String[] mMonth = new String[] {
				"0","1","2","3","4","5","6","7","8","9","10","11","12","15","18","21","24","27","30","33","36","39","42","45","48","51","54","57","60","63","66","69","72","75","78","81"
		};
	int length;
	double userSD[] = new double [36];
	double userSD1[];
	double minus3SD[];
    double minus2SD[];
    double minus1SD[];
    double middleSD[];
    double positive1SD[];
    double positive2SD[];
    double positive3SD[];
    
   
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);   
        	
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
            	userSD1= new double[length];
        		minus3SD = new double[length];
        	    minus2SD= new double[length];
        	    minus1SD= new double[length];
        	    middleSD= new double[length];
        	    positive1SD= new double[length];
        	    positive2SD= new double[length];
        	    positive3SD= new double[length];
            	// Adding data to Series
            	if(IPAD.sex==1){
	        		for (int i = 0; i < length; i++) {
	        			userSD1[i] = userSD[i];
	        			minus3SD[i]=IPAD.weight_boy[i][0];
	        			minus2SD[i]=IPAD.weight_boy[i][1];
	        			minus1SD[i]=IPAD.weight_boy[i][2];
	        			middleSD[i]=IPAD.weight_boy[i][3];
	        			positive1SD[i]=IPAD.weight_boy[i][4];
	        			positive2SD[i]=IPAD.weight_boy[i][5];
	        			positive3SD[i]=IPAD.weight_boy[i][6];
	        			
	        		}
        		}
            	else{
            		for (int i = 0; i < length; i++) {
	        			minus3SD[i]=IPAD.weight_girl[i][0];
	        			minus2SD[i]=IPAD.weight_girl[i][1];
	        			minus1SD[i]=IPAD.weight_girl[i][2];
	        			middleSD[i]=IPAD.weight_girl[i][3];
	        			positive1SD[i]=IPAD.weight_girl[i][4];
	        			positive2SD[i]=IPAD.weight_girl[i][5];
	        			positive3SD[i]=IPAD.weight_girl[i][6];
	        			
	        		}
            	}
            	 //double[] income = { 1000,3700,3800,2000,2500,6000,1000,8000,9000,5000,2000};
                lineChart=new ChartDrawing1("月龄","体重\\kg","Test Chart",mMonth);
                
                lineChart.set_XYSeries(minus3SD, "-3SD");
                lineChart.set_XYSeries(minus2SD, "-2SD");
                lineChart.set_XYSeries(minus1SD, "-1SD\n");
                lineChart.set_XYSeries(middleSD, "中位数\n");
                
                lineChart.set_XYSeries(positive1SD, "+1SD");
                lineChart.set_XYSeries(positive2SD, "+2SD");
                lineChart.set_XYSeries(positive3SD, "+3SD");
                lineChart.set_XYSeries(userSD1, "用户曲线\n");
                
                lineChart.set_XYMultipleSeriesRenderer_Style(lineChart.set_XYSeriesRender_Style_Minus(3));
                lineChart.set_XYMultipleSeriesRenderer_Style(lineChart.set_XYSeriesRender_Style_Minus(2));
                lineChart.set_XYMultipleSeriesRenderer_Style(lineChart.set_XYSeriesRender_Style_Minus(1));
                lineChart.set_XYMultipleSeriesRenderer_Style(lineChart.set_XYSeriesRender_Style_Middle());
                
                lineChart.set_XYMultipleSeriesRenderer_Style(lineChart.set_XYSeriesRender_Style_Positive(1));
                lineChart.set_XYMultipleSeriesRenderer_Style(lineChart.set_XYSeriesRender_Style_Positive(2));
                lineChart.set_XYMultipleSeriesRenderer_Style(lineChart.set_XYSeriesRender_Style_Positive(3));
                lineChart.set_XYMultipleSeriesRenderer_Style(lineChart.set_XYSeriesRender_Style_User());
                LinearLayout lineChartContainer = (LinearLayout) findViewById(R.id.lineChart_container);

           		lChart = (GraphicalView) ChartFactory.getLineChartView(getBaseContext(), lineChart.getDataset(), lineChart.getMultiRenderer());    	

           		lineChartContainer.addView(lChart);

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
            HttpPost httpget = new HttpPost("http://"+IPAD.ip+"/babycare/getweight.php");                
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
   	     	postParameters.add(new BasicNameValuePair("username", user));
   	     	
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
           	userSD[i]=json_data.getDouble("weight");
           	if(userSD[i]>0)
         		  length = i+1;
            }
       }catch(JSONException e1){
       	 Log.e("log_tag", "Error converting result "+e1.toString());
       } catch (ParseException e1) {
            e1.printStackTrace();
       } 
   }
    
    
    
    
    
   
}