package com.example.jump;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivty extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        AlertDialog elart=new AlertDialog.Builder(this).create();
        elart.setIcon(R.drawable.ic_launcher_background);
        elart.setTitle("时间到了");
        elart.setMessage("日记时间到");
        //添加按钮
        elart.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
                public  void onClick(DialogInterface dialog,int which){}

            });
        elart.show();

    }
}
