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

import com.example.babycare.popwindow.ActionItem;
import com.example.babycare.popwindow.TitlePopup;
import com.example.babycare.R;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.content.Intent;

/**
 * An example of tab content that launches an activity via {@link android.widget.TabHost.TabSpec#setContent(android.content.Intent)}
 */






public class Tabs extends TabActivity {

	 //定义标题栏上的按钮  
    private ImageButton titleBtn;  
      
    //定义标题栏弹窗按钮  
    private TitlePopup titlePopup;  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

  	  	final TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("身高曲线")
                .setContent(new Intent(this, HeightChart.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("体重曲线")
                .setContent(new Intent(this, WeightChart.class)));
                
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("头围曲线")
                .setContent(new Intent(this, HeadChart.class)));
        
        tabHost.setCurrentTab(IPAD.tab1);
    }
    
    
    private void initView(){  
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
      
    /** 
     * 初始化数据 
     */  
    private void initData(){  
        //给标题栏弹窗添加子类  
        titlePopup.addAction(new ActionItem(this, "发起聊天", R.drawable.mm_title_btn_compose_normal));  
        titlePopup.addAction(new ActionItem(this, "听筒模式", R.drawable.mm_title_btn_receiver_normal));  
        titlePopup.addAction(new ActionItem(this, "登录网页", R.drawable.mm_title_btn_keyboard_normal));  
        titlePopup.addAction(new ActionItem(this, "扫一扫",  R.drawable.mm_title_btn_qrcode_normal));  
    }  
}
