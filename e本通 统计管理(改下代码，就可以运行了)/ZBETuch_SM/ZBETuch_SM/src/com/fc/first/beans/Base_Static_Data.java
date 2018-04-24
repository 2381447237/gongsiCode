package com.fc.first.beans;

import java.util.ArrayList;
import java.util.List;

public abstract class Base_Static_Data {

	public abstract  Boolean Check_This(String s);
}



class Help232{
	
	public static List<Base_Static_Data> FindAll(List<Base_Static_Data> list,String code)
	{
		List<Base_Static_Data> l = new ArrayList<Base_Static_Data>();
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).Check_This(code))
				l.add(list.get(i));
		}
		return l;
	}
	
}