package com.sqlite;
/**
 * @author Kiritor
 * ���ݿ����*/
import java.util.ArrayList;
import java.util.List;

import com.andorid.shu.love.BookInfo;
import com.andorid.shu.love.SetupInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "love_db";//���ݿ�����
	private final static int DATABASE_VERSION = 1;//���ݿ�汾
	private final static String TABLE_NAME = "book_mark";//�洢ͼ����Ϣ�ı�
	private final static String TABLE_SETUP = "book_setup";
	public final static String FIELD_ID = "_id";
	public final static String FIELD_FILENAME = "filename";//ͼ������
	public final static String FIELD_BOOKMARK = "bookmark";//��ǩ
	public final static String FONT_SIZE = "fontsize";//�����С
	public final static String ROW_SPACE = "rowspace";//�м��
	public final static String COLUMN_SPACE = "columnspace";//�ּ��

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		StringBuffer sqlCreateCountTb = new StringBuffer();
		sqlCreateCountTb.append("create table ").append(TABLE_NAME)
		   .append("(_id integer primary key autoincrement,")		   
		   .append(" filename text,")   
		   .append(" bookmark text);");
		db.execSQL(sqlCreateCountTb.toString());
		String sql = "insert into " + TABLE_NAME + "(filename,bookmark) values('���°ٿ�.txt','0')";
		//����һ����������
	//	db.execSQL(sql);
		//ϵͳ���ñ��洢һЩϵͳ��Ϣ
		StringBuffer setupTb = new StringBuffer();
		setupTb.append("create table ").append(TABLE_SETUP)
		   .append("(_id integer primary key autoincrement,")	
		   .append(" fontsize text,")  
		   .append(" rowspace text,")  
		   .append(" columnspace text);");
		db.execSQL(setupTb.toString());
		//����һ����������
		String setup = "insert into " + TABLE_SETUP + "(fontsize,rowspace,columnspace) values('6','0','0')";
		db.execSQL(setup);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
    //�õ�һ��������α�
	public Cursor select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null,
				" _id desc");
		return cursor;
	}
    //ͨ��id��ȡͼ����Ϣ
	public BookInfo getBookInfo(int id){
		BookInfo book = new BookInfo();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
		cursor = db.query(TABLE_NAME, null, "_id=" + id, null, null, null, null);
		cursor.moveToPosition(0);
		book.id = id;
		book.bookname = cursor.getString(1);
		book.bookmark = cursor.getInt(2);
		db.close();
		return book;
	}
	//�õ�ϵͳ�����Ϣ
	public SetupInfo getSetupInfo(){
		SetupInfo setup = new SetupInfo();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
		cursor = db.query(TABLE_SETUP, null, null, null, null, null, null);
		cursor.moveToPosition(0);
		setup.id = cursor.getInt(0); 
		setup.fontsize = cursor.getInt(1);
		setup.rowspace = cursor.getInt(2);
		setup.columnspace = cursor.getInt(3);
		cursor.close();
		db.close();
		return setup;
	}
	//�õ����е�ͼ�飬Ҳ����ͼ���б�
	public List<BookInfo> getAllBookInfo(){
		List<BookInfo> books = new ArrayList<BookInfo>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, " _id desc");
		int count = cursor.getCount();
		for (int i = 0; i < count; i++) {
			cursor.moveToPosition(i);
			BookInfo book = new BookInfo();
			book.id = cursor.getInt(0);
			book.bookname = cursor.getString(1);
			book.bookmark = cursor.getInt(2);
			books.add(book);
		}
		cursor.close();
		return books;
	}
	//������ǩ�Ĺ���
	public long insert(String Title) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		//�����Լ�ֵ�Ե���ʽ���뵽�ض����ݿ�
		cv.put(FIELD_BOOKMARK, Title);
		long row = db.insert(TABLE_NAME, null, cv);
		return row;
	}
	
	public long insert(String filename, String bookmark) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(FIELD_FILENAME, filename);
		cv.put(FIELD_BOOKMARK, bookmark);
		long row = db.insert(TABLE_NAME, null, cv);
		return row;
	}
	//ͨ��idɾ��һ����
	public void delete(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_ID + "=?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);
	}
    //����һ�������Ϣ
	public void update(int id, String filename, String bookmark) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_ID + "=?";
		String[] whereValue = { Integer.toString(id) };
		ContentValues cv = new ContentValues();
		cv.put(FIELD_FILENAME, filename);
		cv.put(FIELD_BOOKMARK, bookmark);
		db.update(TABLE_NAME, cv, where, whereValue);
	}
	//����ϵͳ��Ϣ
	public void updateSetup(int id, String fontsize, String rowspace,String columnspace) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_ID + "=?";
		String[] whereValue = { Integer.toString(id) };
		ContentValues cv = new ContentValues();
		cv.put(FONT_SIZE, fontsize);
		cv.put(ROW_SPACE, rowspace);
		cv.put(COLUMN_SPACE, columnspace);
		db.update(TABLE_SETUP, cv, where, whereValue);
	}
}
