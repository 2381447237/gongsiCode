package com.example.hospitalapp.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.example.hospitalapp.nonetwork.entity.AnswerNonetWorkContent;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PersonDao {

	MySQLiteHelper helper;

	public PersonDao(Context context) {

		helper = new MySQLiteHelper(context);

	}

	// 批量插入answerInfo表中(答案表)
	public void addAll(AnswerNonetWorkContent ac) {

		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();

		// 执行sql语句
		db.execSQL("insert into answerInfo values(?,?,?)",
				new Object[] { ac.getPid(), ac.getID(), ac.getAnswerStr() });
	}

	// 插入(用户信息表)
	public void add(RecordNonetWorkContent ct) {

		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();

		// 执行sql语句
		db.execSQL("insert into personInfo values(null,?,?,?,?,?)",
				new Object[] { ct.getNAME(), ct.getPHONE(), ct.getCreateTime(),
						ct.getCREATE_USERID(), ct.getMASTERID() });
	}

	// 删除(用户信息表)
	public void delete(String id) {

		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();

		// 执行sql语句
		db.execSQL("delete from personInfo where Id=?", new Object[] { id });

	}

	// 创建(用户信息表)
	public void createPinfo() {
		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();
		// 执行sql语句
		db.execSQL("create table personInfo(Id integer primary key autoincrement,Name varchar(30),Phone integer,Create_Time varchar(50),Create_UserId integer,MasterId integer)");
	}

	// 创建(答案信息表)
	public void createAinfo() {
		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();
		// 执行sql语句
		db.execSQL("create table answerInfo(Pid integer,Aid integer,AnswerStr varchar(100))");
	}

	// 彻底删除(用户信息表)
	public void dropPinfo() {
		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();
		// 执行sql语句
		db.execSQL("drop table personInfo");
	}

	// 彻底删除(答案信息表)
	public void dropAinfo() {
		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();
		// 执行sql语句
		db.execSQL("drop table answerInfo");
	}

	// 删除(用户信息表)所有信息
	public void deletePinfo() {

		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();
		// 执行sql语句
		db.execSQL("delete from personInfo");
	}

	// 删除(答案信息表)所有信息
	public void deleteAinfo() {

		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();
		// 执行sql语句
		db.execSQL("delete from answerInfo");
	}

	// update personInfo set UpId=1 where Id=1;

	// 修改(用户信息表)的标记
	// public void userUpdate(int Id) {
	//
	// // 拿到工具类的实例，然后去操作数据库
	// SQLiteDatabase db = helper.getWritableDatabase();
	//
	// // 执行sql语句
	// db.execSQL("update personInfo set UpId=1 where Id=?",
	// new Object[] { String.valueOf(Id) });
	//
	// }

	// 修改(答案表)的标记
	// public void answerUpdate(int Id) {
	//
	// // 拿到工具类的实例，然后去操作数据库
	// SQLiteDatabase db = helper.getWritableDatabase();
	//
	// // 执行sql语句
	// db.execSQL("update answerInfo set UaId=1 where Pid=?",
	// new Object[] { String.valueOf(Id) });
	//
	// }

	// // 修改(用户信息表)
	// public void update(RecordNonetWorkContent ct) {
	//
	// // 拿到工具类的实例，然后去操作数据库
	// SQLiteDatabase db = helper.getWritableDatabase();
	//
	// // 执行sql语句
	// db.execSQL(
	// "update personInfo set Name=?,Phone=?,Create_Time=?,Create_UserId=?,MasterId=? where Id=?",
	// new Object[] { ct.getNAME(), ct.getPHONE(), ct.getCreateTime(),
	// ct.getID(), ct.getMASTERID() });
	//
	// }

	// 对应的SQL语句 select id from personInfo order by id desc limit 1(用户信息表)
	// select MAX(id) from personInfo
	public int findMaxId() {
		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();

		int SubmitterID = 0;

		Cursor cursor = db.rawQuery("select MAX(Id) from personInfo", null);
		// 将游标向下移一位
		boolean result = cursor.moveToNext();

		if (result) {
			SubmitterID = cursor.getInt(0);
		}

		// 释放资源
		cursor.close();

		return SubmitterID;
	}

	// 计数(用户信息表)
	public int countpersonInfo() {

		int count = 0;
		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();

		Cursor cursor = db.rawQuery("select count(1) from personInfo", null);
		// 将游标向下移一位
		boolean result = cursor.moveToNext();

		if (result) {
			count = cursor.getInt(0);
		}

		// 释放资源
		cursor.close();

		return count;
	}

	// (用户信息表)
	public int findMasterId(int position) {

		int mId = 0;

		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();

		Cursor cursor = db.rawQuery(
				"select MasterId from personInfo where Id=?",
				new String[] { String.valueOf(position) });

		// 将游标向下移一位
		boolean result = cursor.moveToNext();

		if (result) {
			mId = cursor.getInt(cursor.getColumnIndex("MasterId"));
		}

		// 释放资源
		cursor.close();

		return mId;
	}

	// 查询(用户信息表)
	public RecordNonetWorkContent find(String id) {

		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getWritableDatabase();

		Cursor cursor = db.rawQuery("select *from personInfo where Id=?",
				new String[] { id });
		// 将游标向下移一位
		boolean result = cursor.moveToNext();

		RecordNonetWorkContent ct = null;

		if (result) {

			int Id = cursor.getInt(cursor.getColumnIndex("Id"));

			String Name = cursor.getString(cursor.getColumnIndex("Name"));

			String Phone = cursor.getString(cursor.getColumnIndex("Phone"));

			String Create_Time = cursor.getString(cursor
					.getColumnIndex("Create_Time"));

			int Create_UserId = cursor.getInt(cursor
					.getColumnIndex("Create_UserId"));

			int MasterId = cursor.getInt(cursor.getColumnIndex("MasterId"));

			ct = new RecordNonetWorkContent(Id, Create_UserId, Name, Phone,
					MasterId, Create_Time);

		}

		// 释放资源
		cursor.close();

		return ct;
	}

	// 查询返回答案的信息(答案表)
	public List<AnswerNonetWorkContent> getAllAnswersByUserId(String userId) {
		List<AnswerNonetWorkContent> MyAnswerList = new ArrayList<AnswerNonetWorkContent>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from answerInfo where Pid =?",
				new String[] { userId });
		while (cursor.moveToNext()) {
			int Pid = cursor.getInt(cursor.getColumnIndex("Pid"));
			int Aid = cursor.getInt(cursor.getColumnIndex("Aid"));
			String AnswerStr = cursor.getString(cursor
					.getColumnIndex("AnswerStr"));
			AnswerNonetWorkContent ac = new AnswerNonetWorkContent(Pid, Aid,
					AnswerStr);
			MyAnswerList.add(ac);
		}
		// 释放资源
		cursor.close();
		return MyAnswerList;
	}

	// 查询返回所有个人的信息(用户信息表)
	public List<RecordNonetWorkContent> getAllUsers() {
		List<RecordNonetWorkContent> list = new ArrayList<RecordNonetWorkContent>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from personInfo", null);
		while (cursor.moveToNext()) {
			int Id = cursor.getInt(cursor.getColumnIndex("Id"));
			String Name = cursor.getString(cursor.getColumnIndex("Name"));
			String Phone = cursor.getString(cursor.getColumnIndex("Phone"));
			String Create_Time = cursor.getString(cursor
					.getColumnIndex("Create_Time"));
			int Create_UserId = cursor.getInt(cursor
					.getColumnIndex("Create_UserId"));
			int MasterId = cursor.getInt(cursor.getColumnIndex("MasterId"));

			RecordNonetWorkContent ct = new RecordNonetWorkContent(Id,
					Create_UserId, Name, Phone, MasterId, Create_Time);
			list.add(ct);
		}
		// 释放资源
		cursor.close();
		return list;
	}

	// 查询返回所有个人的信息(用户信息表)
	public List<RecordNonetWorkContent> selectAllUsers() {
		List<RecordNonetWorkContent> list = new ArrayList<RecordNonetWorkContent>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from personInfo order by Id desc", null);
		while (cursor.moveToNext()) {
			int Id = cursor.getInt(cursor.getColumnIndex("Id"));
			String Name = cursor.getString(cursor.getColumnIndex("Name"));
			String Phone = cursor.getString(cursor.getColumnIndex("Phone"));
			String Create_Time = cursor.getString(cursor
					.getColumnIndex("Create_Time"));
			int Create_UserId = cursor.getInt(cursor
					.getColumnIndex("Create_UserId"));
			int MasterId = cursor.getInt(cursor.getColumnIndex("MasterId"));

			RecordNonetWorkContent ct = new RecordNonetWorkContent(Id,
					Create_UserId, Name, Phone, MasterId, Create_Time);
			list.add(ct);
		}
		// 释放资源
		cursor.close();
		return list;
	}

	// 查看个人信息(用户信息表)
	public List<RecordNonetWorkContent> findPersonInfo(int position) {

		List<RecordNonetWorkContent> rc = new ArrayList<RecordNonetWorkContent>();
		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from personInfo where Id=?",
				new String[] { String.valueOf(position) });

		while (cursor.moveToNext()) {
			String Name = cursor.getString(cursor.getColumnIndex("Name")); // 名字
			String Phone = cursor.getString(cursor.getColumnIndex("Phone"));// 电话
			String Create_Time = cursor.getString(cursor
					.getColumnIndex("Create_Time"));
			int Create_UserId = cursor.getInt(cursor
					.getColumnIndex("Create_UserId"));
			int MasterId = cursor.getInt(cursor.getColumnIndex("MasterId"));

			RecordNonetWorkContent rContent = new RecordNonetWorkContent(
					position, Create_UserId, Name, Phone, MasterId, Create_Time);
			rc.add(rContent);
		}
		// 释放资源
		cursor.close();
		return rc;
	}

	// 查询答案(答案表)
	public List<AnswerNonetWorkContent> findAnswer(int position) {
		List<AnswerNonetWorkContent> ac = new ArrayList<AnswerNonetWorkContent>();
		// 拿到工具类的实例，然后去操作数据库
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select Aid, AnswerStr from answerInfo where Pid=?",
				new String[] { String.valueOf(position) });
		while (cursor.moveToNext()) {
			int Aid = cursor.getInt(cursor.getColumnIndex("Aid"));
			String AnswerStr = cursor.getString(cursor
					.getColumnIndex("AnswerStr"));
			AnswerNonetWorkContent aContent = new AnswerNonetWorkContent(
					position, Aid, AnswerStr);
			ac.add(aContent);
		}
		// 释放资源
		cursor.close();
		return ac;
	}
}
