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

	// ��������answerInfo����(�𰸱�)
	public void addAll(AnswerNonetWorkContent ac) {

		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();

		// ִ��sql���
		db.execSQL("insert into answerInfo values(?,?,?)",
				new Object[] { ac.getPid(), ac.getID(), ac.getAnswerStr() });
	}

	// ����(�û���Ϣ��)
	public void add(RecordNonetWorkContent ct) {

		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();

		// ִ��sql���
		db.execSQL("insert into personInfo values(null,?,?,?,?,?)",
				new Object[] { ct.getNAME(), ct.getPHONE(), ct.getCreateTime(),
						ct.getCREATE_USERID(), ct.getMASTERID() });
	}

	// ɾ��(�û���Ϣ��)
	public void delete(String id) {

		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();

		// ִ��sql���
		db.execSQL("delete from personInfo where Id=?", new Object[] { id });

	}

	// ����(�û���Ϣ��)
	public void createPinfo() {
		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();
		// ִ��sql���
		db.execSQL("create table personInfo(Id integer primary key autoincrement,Name varchar(30),Phone integer,Create_Time varchar(50),Create_UserId integer,MasterId integer)");
	}

	// ����(����Ϣ��)
	public void createAinfo() {
		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();
		// ִ��sql���
		db.execSQL("create table answerInfo(Pid integer,Aid integer,AnswerStr varchar(100))");
	}

	// ����ɾ��(�û���Ϣ��)
	public void dropPinfo() {
		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();
		// ִ��sql���
		db.execSQL("drop table personInfo");
	}

	// ����ɾ��(����Ϣ��)
	public void dropAinfo() {
		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();
		// ִ��sql���
		db.execSQL("drop table answerInfo");
	}

	// ɾ��(�û���Ϣ��)������Ϣ
	public void deletePinfo() {

		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();
		// ִ��sql���
		db.execSQL("delete from personInfo");
	}

	// ɾ��(����Ϣ��)������Ϣ
	public void deleteAinfo() {

		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();
		// ִ��sql���
		db.execSQL("delete from answerInfo");
	}

	// update personInfo set UpId=1 where Id=1;

	// �޸�(�û���Ϣ��)�ı��
	// public void userUpdate(int Id) {
	//
	// // �õ��������ʵ����Ȼ��ȥ�������ݿ�
	// SQLiteDatabase db = helper.getWritableDatabase();
	//
	// // ִ��sql���
	// db.execSQL("update personInfo set UpId=1 where Id=?",
	// new Object[] { String.valueOf(Id) });
	//
	// }

	// �޸�(�𰸱�)�ı��
	// public void answerUpdate(int Id) {
	//
	// // �õ��������ʵ����Ȼ��ȥ�������ݿ�
	// SQLiteDatabase db = helper.getWritableDatabase();
	//
	// // ִ��sql���
	// db.execSQL("update answerInfo set UaId=1 where Pid=?",
	// new Object[] { String.valueOf(Id) });
	//
	// }

	// // �޸�(�û���Ϣ��)
	// public void update(RecordNonetWorkContent ct) {
	//
	// // �õ��������ʵ����Ȼ��ȥ�������ݿ�
	// SQLiteDatabase db = helper.getWritableDatabase();
	//
	// // ִ��sql���
	// db.execSQL(
	// "update personInfo set Name=?,Phone=?,Create_Time=?,Create_UserId=?,MasterId=? where Id=?",
	// new Object[] { ct.getNAME(), ct.getPHONE(), ct.getCreateTime(),
	// ct.getID(), ct.getMASTERID() });
	//
	// }

	// ��Ӧ��SQL��� select id from personInfo order by id desc limit 1(�û���Ϣ��)
	// select MAX(id) from personInfo
	public int findMaxId() {
		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();

		int SubmitterID = 0;

		Cursor cursor = db.rawQuery("select MAX(Id) from personInfo", null);
		// ���α�������һλ
		boolean result = cursor.moveToNext();

		if (result) {
			SubmitterID = cursor.getInt(0);
		}

		// �ͷ���Դ
		cursor.close();

		return SubmitterID;
	}

	// ����(�û���Ϣ��)
	public int countpersonInfo() {

		int count = 0;
		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();

		Cursor cursor = db.rawQuery("select count(1) from personInfo", null);
		// ���α�������һλ
		boolean result = cursor.moveToNext();

		if (result) {
			count = cursor.getInt(0);
		}

		// �ͷ���Դ
		cursor.close();

		return count;
	}

	// (�û���Ϣ��)
	public int findMasterId(int position) {

		int mId = 0;

		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();

		Cursor cursor = db.rawQuery(
				"select MasterId from personInfo where Id=?",
				new String[] { String.valueOf(position) });

		// ���α�������һλ
		boolean result = cursor.moveToNext();

		if (result) {
			mId = cursor.getInt(cursor.getColumnIndex("MasterId"));
		}

		// �ͷ���Դ
		cursor.close();

		return mId;
	}

	// ��ѯ(�û���Ϣ��)
	public RecordNonetWorkContent find(String id) {

		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getWritableDatabase();

		Cursor cursor = db.rawQuery("select *from personInfo where Id=?",
				new String[] { id });
		// ���α�������һλ
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

		// �ͷ���Դ
		cursor.close();

		return ct;
	}

	// ��ѯ���ش𰸵���Ϣ(�𰸱�)
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
		// �ͷ���Դ
		cursor.close();
		return MyAnswerList;
	}

	// ��ѯ�������и��˵���Ϣ(�û���Ϣ��)
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
		// �ͷ���Դ
		cursor.close();
		return list;
	}

	// ��ѯ�������и��˵���Ϣ(�û���Ϣ��)
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
		// �ͷ���Դ
		cursor.close();
		return list;
	}

	// �鿴������Ϣ(�û���Ϣ��)
	public List<RecordNonetWorkContent> findPersonInfo(int position) {

		List<RecordNonetWorkContent> rc = new ArrayList<RecordNonetWorkContent>();
		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from personInfo where Id=?",
				new String[] { String.valueOf(position) });

		while (cursor.moveToNext()) {
			String Name = cursor.getString(cursor.getColumnIndex("Name")); // ����
			String Phone = cursor.getString(cursor.getColumnIndex("Phone"));// �绰
			String Create_Time = cursor.getString(cursor
					.getColumnIndex("Create_Time"));
			int Create_UserId = cursor.getInt(cursor
					.getColumnIndex("Create_UserId"));
			int MasterId = cursor.getInt(cursor.getColumnIndex("MasterId"));

			RecordNonetWorkContent rContent = new RecordNonetWorkContent(
					position, Create_UserId, Name, Phone, MasterId, Create_Time);
			rc.add(rContent);
		}
		// �ͷ���Դ
		cursor.close();
		return rc;
	}

	// ��ѯ��(�𰸱�)
	public List<AnswerNonetWorkContent> findAnswer(int position) {
		List<AnswerNonetWorkContent> ac = new ArrayList<AnswerNonetWorkContent>();
		// �õ��������ʵ����Ȼ��ȥ�������ݿ�
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
		// �ͷ���Դ
		cursor.close();
		return ac;
	}
}
