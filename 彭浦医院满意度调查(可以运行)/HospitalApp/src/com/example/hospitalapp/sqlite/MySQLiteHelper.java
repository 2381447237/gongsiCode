package com.example.hospitalapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public MySQLiteHelper(Context context) {
		// context:应用上下文
		// name:数据库的名称
		// factory:创建游标的工厂
		// version:数据库的版本
		super(context, "test", null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table personInfo(Id integer primary key autoincrement,Name varchar(30),Phone integer,Create_Time varchar(50),Create_UserId integer,MasterId integer)");
		// personInfo字段有:Id用户Id,Name用户姓名,Phone用户电话,Create_Time创建时间,Create_UserId调查人Id,MasterId答卷Id
		db.execSQL("create table answerInfo(Pid integer,Aid integer,AnswerStr varchar(100))");
		//answerInfo答案信息表，Pid用户Id,Aid选中答案的Id,AnswerStr填空题的答案
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
