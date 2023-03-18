package com.example.jump;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jump.database.RijiDBHelper;
import com.example.jump.database.UserDBHelper;
import com.example.jump.enity.Riji;
import com.example.jump.enity.User;
import com.example.jump.util.FileUtil;
import com.example.jump.util.ToastUtil;

import java.io.File;

public class first extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    private TextView tv_date;
    private EditText et_guanjianzi;
    private EditText et_zongjie;
    private EditText et_tianshu;
    private EditText et_zuichang;
    private RijiDBHelper mHelper;
    private String path;
    private TextView tv_txt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        et_guanjianzi=findViewById(R.id.et_guanjianzi);
        et_zongjie=findViewById(R.id.et_zongjie);

        findViewById(R.id.btn_act_next).setOnClickListener(this);
        findViewById(R.id.btn_date).setOnClickListener(this);
        findViewById(R.id.btn_act_addwai).setOnClickListener(this);
        findViewById(R.id.btn_act_read).setOnClickListener(this);
        findViewById(R.id.btn_act_add).setOnClickListener(this);
         tv_date = findViewById(R.id.tv_date);
        tv_txt=findViewById(R.id.tv_txt);
    }
    @Override
    protected void onStart(){
        super.onStart();
        mHelper = RijiDBHelper.getInstance(this);
        mHelper.openWriteLink();
        mHelper.openReadLink();
    }
    @Override
    protected void onStop(){
        super.onStop();
        mHelper.closeLink();
    }
    @Override
    public void onClick(View v) {
        String guanjianzi=et_guanjianzi.getText().toString();
        String zongjie=et_zongjie.getText().toString();
        String tianshu= "2";
        String zuichang="5";
        String riqi=String.format(tv_date.getText().toString());
        Riji riji=null;
        //字符串的拼接
        StringBuilder sb=new StringBuilder();
        sb.append("关键字：").append(guanjianzi);
        sb.append("\n总结：").append(zongjie);
        sb.append("\n日期：").append(riqi);
        sb.append("\n最长：").append(zuichang);
        sb.append("\n天数：").append(tianshu);

        switch (v.getId()){
            case R.id.btn_act_next:
                startActivity(new Intent(this,Actsecond.class));

                break;
            case R.id.btn_date:
                DatePickerDialog dialog=new DatePickerDialog( this,  this,2023,3,6);
                dialog.show();
                break;
            case R.id.btn_act_add:
                //以下声明用户对象
                riji=new Riji(guanjianzi,zongjie,tianshu,zuichang,riqi);
                System.out.println("add ... ");
               /* long sendreed=mHelper.insert(riji);
                Intent intent=new Intent(this,Actsecond.class);
                //创建一个新包裹
                Bundle bundle=new Bundle();
                bundle.putString("request_content", String.valueOf(sendreed));
                intent.putExtras(bundle);
                startActivity(intent);*/
                if(mHelper.insert(riji)>0){
                    ToastUtil.show(this,"添加成功");
                }
                break;
            case R.id.btn_act_addwai:
                String filename=System.currentTimeMillis()+".txt";
                String directory=null;
                //外部存储的私有空间
                directory=getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
                path=directory+ File.separatorChar+filename;
                FileUtil.saveText(path,sb.toString());
                ToastUtil.show(this,"保存成功");
                break;
            case R.id.btn_act_read:
                tv_txt.setText(FileUtil.openText(path));
                break;

        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String desc=String.format("您选择地日期是%d年%d月%d日",i,i1,i2);
        tv_date.setText(desc);
    }
}