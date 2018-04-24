package com.example.hospitalapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public MySQLiteHelper(Context context) {
		// context:Ӧ��������
		// name:���ݿ������
		// factory:�����α�Ĺ���
		// version:���ݿ�İ汾
		super(context, "test", null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table personInfo(Id integer primary key autoincrement,Name varchar(30),Phone integer,Create_Time varchar(50),Create_UserId integer,MasterId integer)");
		// personInfo�ֶ���:Id�û�Id,Name�û�����,Phone�û��绰,Create_Time����ʱ��,Create_UserId������Id,MasterId���Id
		db.execSQL("create table answerInfo(Pid integer,Aid integer,AnswerStr varchar(100))");
		//answerInfo����Ϣ��Pid�û�Id,Aidѡ�д𰸵�Id,AnswerStr�����Ĵ�
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
