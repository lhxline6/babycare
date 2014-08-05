package com.example.babycare;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EmergencyList extends Activity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> groupList = new ArrayList<String>();

        groupList.add("亟需注意的紧急症状");
        groupList.add("抗生素：什么时候能够缓解病情");
        groupList.add("咳嗽和感冒：医药或家庭疗法？");
        groupList.add("小于三个月的婴儿哭闹不停");
        groupList.add("大于3月龄的孩子哭闹 -易激惹- 烦躁");
        groupList.add("发热");
        groupList.add("发热 - 谣言VS科学");
        groupList.add("颅脑损伤");
        groupList.add("头痛");
        groupList.add("热暴露和热反应");
        groupList.add("流感");

        

        ArrayList<String> itemList1 = new ArrayList<String>();
        itemList1.add("新生儿疾病");
        itemList1.add("重度嗜睡");
        itemList1.add("意识模糊");
        itemList1.add("剧烈疼痛");
        itemList1.add("哭闹不停");
        itemList1.add("不能走路");
        itemList1.add("腹部疼痛");
        itemList1.add("睾丸或阴囊疼痛");
        itemList1.add("呼吸困难");
        itemList1.add("发绀");
        itemList1.add("流口水");
        itemList1.add("脱水");
        itemList1.add("凸起斑点");
        itemList1.add("颈部僵硬");
        itemList1.add("颈部受伤");
        itemList1.add("紫色或血红色的斑点");
        itemList1.add("小于3月龄的孩子发热（超过100.4°F或38°C）");
        itemList1.add("发热超过105°F（40.6°C）");
        itemList1.add("慢性疾病");
        ArrayList<String> itemList2 = new ArrayList<String>();
        itemList2.add("定义");
        itemList2.add("细菌感染：抗生素可以缓解病情");
        itemList2.add("病毒感染：抗生素不能缓解病情");
        itemList2.add("感冒症状多变");
        itemList2.add("抗生素的副作用");
        itemList2.add("给予抗生素治疗病毒感染：将会有什么后果");
        itemList2.add("你能做什么");
        ArrayList<String> itemList3 = new ArrayList<String>();
        itemList3.add("药品");
        itemList3.add("家庭疗法");
        itemList3.add("治疗并非总是必须的");
        itemList3.add("摘要");
        ArrayList<String> itemList4 = new ArrayList<String>();
        itemList4.add("定义");
        itemList4.add("原因");
        itemList4.add("绞痛的定义");
        ArrayList<String> itemList5 = new ArrayList<String>();
        itemList5.add("定义");
        itemList5.add("原因");
        ArrayList<String> itemList6 = new ArrayList<String>();
        itemList6.add("定义");
        itemList6.add("原因");
        itemList6.add("发热和哭泣");
        itemList6.add("温度变化正常范围");
        itemList6.add("回到学校");
        ArrayList<String> itemList7 = new ArrayList<String>();
        itemList7.add("发热 - 谣言VS科学");
       
        ArrayList<String> itemList8 = new ArrayList<String>();
        itemList8.add("定义&原因");
        ArrayList<String> itemList9 = new ArrayList<String>();
        itemList9.add("定义");
        itemList9.add("原因");
        ArrayList<String> itemList10 = new ArrayList<String>();
        itemList10.add("定义");
        itemList10.add("热反应的类型");
        itemList10.add("原因");
        itemList10.add("中暑及日射病的急救");
        itemList10.add("热衰竭的急救");
        ArrayList<String> itemList11 = new ArrayList<String>();
        itemList11.add("患流感儿童哪些可能有较高的风险并发其他疾病");
        itemList11.add("抗流感病毒处方药");
     
        
        

        
    
        ArrayList<ArrayList<String>> childList = new ArrayList<ArrayList<String>>();
        childList.add(itemList1);
        childList.add(itemList2);
        childList.add(itemList3);
        childList.add(itemList4);
        childList.add(itemList5);
        childList.add(itemList6);
     
        childList.add(itemList7);
        childList.add(itemList8);
        childList.add(itemList9);
        childList.add(itemList10);
        childList.add(itemList11);
        
        

        ExpandableListView list = new ExpandableListView(this);
        ExpandableListAdapter mAdapter = new MyExpandableListAdapter(groupList, childList);
        list.setAdapter(mAdapter);

        list.setCacheColorHint(0x00000000);
        list.setSelector(new ColorDrawable(Color.TRANSPARENT));
        list.setGroupIndicator(null);

        setContentView(list);
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
                textView = new TextView(EmergencyList.this);
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
                	Intent i=new Intent(EmergencyList.this, EmergencyContent.class);
    				
    				i.putExtra("group",selectedGroupPosition);
    				i.putExtra("child",selectedChildPosition);
    				startActivity(i); 
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
            LinearLayout cotain = new LinearLayout(EmergencyList.this);
            cotain.setPadding(0, 20, 0, 20);
            cotain.setGravity(Gravity.CENTER_VERTICAL);

            ImageView imgIndicator = new ImageView(EmergencyList.this);
            TextView textView = new TextView(EmergencyList.this);
            textView.setText(getGroup(groupPosition).toString());
            textView.setTextSize(17);
            textView.setPadding(15, 0, 0, 0);

            if (isExpanded) {
                imgIndicator.setBackgroundResource(R.drawable.mushroom);
                
            } else {
                imgIndicator.setBackgroundResource(R.drawable.mushroom);
             
            }
            cotain.addView(imgIndicator);
            cotain.addView(textView);
            cotain.setBackgroundColor(0xf391a9);
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
}