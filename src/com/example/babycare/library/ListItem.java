package com.example.babycare.library;

import java.util.HashMap;
import java.util.Map;

/**
 * ListView�е��б���
 * @author PiaoHong
 *
 */
public class ListItem 
{
	/**����*/
	public int mType;
	/**��ֵ��ӦMap*/
	public Map<Integer, ?> mMap ;
	
	public ListItem(int type, HashMap<Integer, ?> map)
	{
		mType = type;
		mMap = map;
	}
	
	public int getType(){
		return mType;
	}
}
