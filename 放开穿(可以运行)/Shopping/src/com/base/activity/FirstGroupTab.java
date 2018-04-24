package com.base.activity;


import com.example.shopping.ClassifyFragment;

import android.app.ActivityGroup; 
import android.content.Intent; 
import android.os.Bundle; 
import android.view.View; 
import android.view.Window; 

public class FirstGroupTab extends ActivityGroup { 
         
       
        public static ActivityGroup group;   // һ����̬��ActivityGroup���������ڹ���Group�е�Activity 
         
        @Override 
        protected void onCreate(Bundle savedInstanceState) { 
             
                super.onCreate(savedInstanceState); 
                 
                group = this; 
                 
        } 

        @Override 
        public void onBackPressed() { 
            
                super.onBackPressed();       //�Ѻ����¼�������Activity���� 
          
                group.getLocalActivityManager() 
                        .getCurrentActivity().onBackPressed(); 
        } 

        @Override 
        protected void onResume() { 
            
                super.onResume(); 
                //�ѽ����л��ŵ�onResume����������Ϊ��������ѡ��л�����ʱ�� 
                //���ø����onResume���� 
                 
                //Ҫ��ת�Ľ��� 
                Intent intent = new Intent(this, ClassifyFragment.class). 
                      addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
                //��һ��Activityת����һ��View 
                Window w = group.getLocalActivityManager().startActivity("ClassifyActivity",intent); 
            View view = w.getDecorView(); 
            //��View��Ӵ�ActivityGroup�� 
            group.setContentView(view); 
        } 

}
