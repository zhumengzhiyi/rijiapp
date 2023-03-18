package com.example.jump;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jump.database.UserDBHelper;
import com.example.jump.enity.User;
import com.example.jump.util.ToastUtil;

import java.util.List;

public class Actzhuce extends AppCompatActivity implements View.OnClickListener{

    private EditText et_xuehao;
    private EditText et_name;
    private EditText et_shouji;
    private EditText et_banji;
    private UserDBHelper mHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actzhuce);
        et_xuehao = findViewById(R.id.et_xuehao);
        et_name = findViewById(R.id.et_name);
        et_shouji = findViewById(R.id.et_shouji);
        et_banji = findViewById(R.id.et_banji);

        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_updata).setOnClickListener(this);
        findViewById(R.id.btn_select).setOnClickListener(this);



    }
    @Override
    protected void onStart(){
        super.onStart();
         mHelper = UserDBHelper.getInstance(this);
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
        String id= et_xuehao.getText().toString();
        String name=et_name.getText().toString();
        String shouji= et_shouji.getText().toString();
        String banji=et_banji.getText().toString();
        User user=null;
        switch (v.getId()){
            case R.id.btn_confirm:
                //以下声明用户对象
                user=new User(Integer.parseInt(id),name,shouji,banji);
                mHelper.insert(user);
                if(mHelper.insert(user)>0){
                    ToastUtil.show(this,"添加成功");
                }
                break;
            case R.id.btn_delete:
                mHelper.deleteByName(name);
                System.out.println("delete ... ");
                if(mHelper.deleteByName(name)>0){
                    ToastUtil.show(this,"删除成功");
                }
                break;
            case R.id.btn_updata:
                user=new User(Integer.parseInt(id),name,shouji,banji);
                mHelper.updata(user);
                if(mHelper.updata(user)>0){
                    ToastUtil.show(this,"修改成功");
                }
                break;
            case R.id.btn_select:
                List<User> list=mHelper.queryAll();
                for(User u:list){
                    Log.d("ning",u.toString());
                }

                et_xuehao.setText(String.valueOf(list.get(0).id));
               // System.out.println(list.get(0).id);
                et_name.setText(list.get(0).name);
                et_banji.setText(list.get(0).banji);
                et_shouji.setText(list.get(0).shouji);
                break;

        }


    }
}