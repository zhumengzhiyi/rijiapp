package com.example.jump.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jump.enity.Riji;
import com.example.jump.enity.User;

import java.util.ArrayList;
import java.util.List;

public class RijiDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="text.db";
    private static final String TABLE_NAME="riji";
    private static final int  DB_VERSION=1;
    private static RijiDBHelper mHelper  =null;
    private SQLiteDatabase mRDB  =null;
    private SQLiteDatabase mWDB  =null;


    private RijiDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    //利用单利模式获取数据链接安全
    public static RijiDBHelper getInstance(Context context){
        if(mHelper==null){
            mHelper=new RijiDBHelper(context);

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
                "guanjianzi varchar not null,"+
                "riqi varchar not null,"+
                "zongjie varchar not null,"+
                "tianshu varchar not null,"+
                "zuichang varchar not null);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insert(Riji riji){
        ContentValues values=new ContentValues();
        //values.put("id",riji.id);
        values.put("guanjianzi",riji.guanjianzi);
        values.put("riqi",riji.riqi);
        values.put("zongjie",riji.zongjie);
        values.put("tianshu",riji.tianshu);
        values.put("zuichang",riji.zuichang);
        return mWDB.insert(TABLE_NAME,null,values);
    }
    public long deleteByName(String name){
        //全部删除
       // return mWDB.delete(TABLE_NAME,"1=1",null);
        //名字删除
        return mWDB.delete(TABLE_NAME,"guanjianzi=?",new String[]{name});
    }
    public long updata(User user){
        ContentValues values=new ContentValues();
        values.put("id",user.id);
        values.put("name",user.name);
        values.put("shouji",user.shouji);
        values.put("banji",user.banji);
        return mWDB.update(TABLE_NAME,values,"name=?",new String[]{user.name});
    }
    public List<Riji> queryAll(){
        List<Riji> list=new ArrayList<>();
        //结果集
        System.out.println("查看");
        SQLiteDatabase mSQLiteDatabase = null;
        Cursor cursor=mRDB.query(TABLE_NAME,null,null,null,null,null,null);
        System.out.println("查看2");
        //循环游标
        while(cursor.moveToNext()){
            Riji riji=new Riji();
            riji.guanjianzi= cursor.getString(1);
            riji.zongjie=cursor.getString(3);
            riji.riqi=cursor.getString(2);
            //riji.banji=cursor.getString(3);
            list.add(riji);
        }
        return  list;
    }
    //查一个
    public Riji queryByName(String name){
        Riji riji=new Riji();
        //结果集
        Cursor cursor=mRDB.query(TABLE_NAME,null,"guanjianzi=?",new String[]{name},null,null,null);
        //循环游标
        while(cursor.moveToNext()){

            riji.guanjianzi= cursor.getString(1);
            riji.zongjie=cursor.getString(3);
            riji.riqi=cursor.getString(2);

        }
        return  riji;
    }
}
