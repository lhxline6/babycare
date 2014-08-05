package com.example.babycare;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EmergencyContent extends Activity {

	private Button back;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contents);
		
		
		 Intent intent =getIntent();
         /*取出Intent中附加的数据*/
         int first = intent.getIntExtra("group", 0);
         int second = intent.getIntExtra("child", 0);
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText(IPAD.content[first][second]);	
		
		
		back  = (Button)findViewById(R.id.button2);
		back.setBackgroundColor(Color.CYAN);
		back.setOnClickListener(new View.OnClickListener(){
	        	@Override
	        	public void onClick(View view){
					finish();   		
	        	}
	        	});
		
	}

	
}

