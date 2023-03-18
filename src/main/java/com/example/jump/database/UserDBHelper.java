package com.example.jump.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jump.enity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="test.db";
    private static final String TABLE_NAME="user_info";
    private static final int  DB_VERSION=1;
    private static UserDBHelper mHelper  =null;
    private SQLiteDatabase mRDB  =null;
    private SQLiteDatabase mWDB  =null;


    private UserDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    //利用单利模式获取数据链接安全
    public static UserDBHelper getInstance(Context context){
        if(mHelper==null){
            mHelper=new UserDBHelper(context);

        }
        return mHelper;
    }
    //打开数据库的读链接
    public SQLiteDatabase openReadLink(){
        if(mRDB==null||!mRDB.isOpen()){
            mRDB=mHelper.getReadableDatabase();
        }
        return mRDB;
    }
    //打开数据库的写链接
    public SQLiteDatabase openWriteLink(){
        if(mWDB==null||!mWDB.isOpen()){
            mWDB=mHelper.getWritableDatabase();
        }
        return mWDB;
    }
    //数据库链接关闭
    public void closeLink(){
        if(mRDB!=null&&mRDB.isOpen()){
            mRDB.close();
            mRDB=null;
        }
        if(mWDB!=null&&mWDB.isOpen()){
            mWDB.close();
            mWDB=null;
        }
    }
    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
    String sql="create table if not exists "+TABLE_NAME+" ("+
            "id integer primary key autoincrement not null,"+
            "name varchar not null,"+
            "shouji varchar not null,"+
            "banji varchar not null);";
            db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insert(User user){
        ContentValues values=new ContentValues();
        values.put("id",user.id);
        values.put("name",user.name);
        values.put("shouji",user.shouji);
        values.put("banji",user.banji);
        return mWDB.insert(TABLE_NAME,null,values);
    }
    public long deleteByName(String name){
        //全部删除
        //mWDB.delete(TABLE_NAME,"1=1",null);
        //名字删除
        return mWDB.delete(TABLE_NAME,"name=?",new String[]{name});
    }
    public long updata(User user){
        ContentValues values=new ContentValues();
        values.put("id",user.id);
        values.put("name",user.name);
        values.put("shouji",user.shouji);
        values.put("banji",user.banji);
        return mWDB.update(TABLE_NAME,values,"name=?",new String[]{user.name});
    }
    public List<User> queryAll(){
        List<User> list=new ArrayList<>();
        //结果集
        Cursor cursor=mRDB.query(TABLE_NAME,null,null,null,null,null,null);
        //循环游标
        while(cursor.moveToNext()){
            User user=new User();
            user.id= cursor.getInt(0);
            user.name=cursor.getString(1);
            user.shouji=cursor.getString(2);
            user.banji=cursor.getString(3);
            list.add(user);
        }
        return  list;
    }
    //查一个
    public List<User> queryByName(String name){
        List<User> list=new ArrayList<>();
        //结果集
        Cursor cursor=mRDB.query(TABLE_NAME,null,"name=?",new String[]{name},null,null,null);
        //循环游标
        while(cursor.moveToNext()){
            User user=new User();
            user.id= cursor.getInt(0);
            user.name=cursor.getString(1);
            user.shouji=cursor.getString(2);
            user.banji=cursor.getString(3);
            list.add(user);
        }
        return  list;
    }
}
