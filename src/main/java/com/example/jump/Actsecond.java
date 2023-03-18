package com.example.jump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jump.database.RijiDBHelper;
import com.example.jump.enity.Riji;
import com.example.jump.util.ToastUtil;

import java.util.List;


public class Actsecond extends AppCompatActivity implements View.OnClickListener {
    static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;
    private EditText et_second_guanjianzi;
    private EditText et_name;
    private EditText et_shouji;
    private EditText et_banji;
    private RijiDBHelper mHelper;
    private TextView tv_txt_look;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actsecond);
        tv_txt_look=findViewById(R.id.tv_txt_look);
        et_second_guanjianzi=findViewById(R.id.et_second_guanjianzi);
       /* findViewById(R.id.btn_act_back).setOnClickListener(this);
        //从上一个界面获取包裹
        Bundle bundle = getIntent().getExtras();
        String request_content = bundle.getString("request_content");
        System.out.println(request_content);*/



        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQ_CODE);
        }



//设置按钮
        findViewById(R.id.btn_act_look).setOnClickListener(this);
        findViewById(R.id.btn_act_shanchu).setOnClickListener(this);
        findViewById(R.id.btn_second_read_).setOnClickListener(this);
        findViewById(R.id.btn_act_back).setOnClickListener(this);


    }



    @Override
    protected void onStart() {
        super.onStart();
        mHelper = RijiDBHelper.getInstance(this);
        mHelper.openWriteLink();
        mHelper.openReadLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }



    @Override
    public void onClick(View v) {
        String ul="";
        String guanjianzi=et_second_guanjianzi.getText().toString();
        /*String id = et_xuehao.getText().toString();
        String  = et_name.getText().toString();
        String shouji = et_shouji.getText().toString();
        String banji = et_banji.getText().toString();*/

        switch (v.getId()) {
            case R.id.btn_act_back:
                startActivity(new Intent(this,MainActivity.class));

            case R.id.btn_act_shanchu:
                mHelper.openWriteLink();
                mHelper.deleteByName(guanjianzi);
                System.out.println("delete ... ");
                if (mHelper.deleteByName(guanjianzi) > 0) {
                    ToastUtil.show(this, "删除成功");
                }
                break;
            case R.id.btn_second_read_:
                mHelper.openReadLink();
                Riji riji=new Riji();
                riji=mHelper.queryByName(guanjianzi);
                ul="关键字"+riji.guanjianzi+"总结"+riji.zongjie;
                tv_txt_look.setText(ul);
                break;
            case R.id.btn_act_look:
                mHelper.openReadLink();
                List<Riji> list = mHelper.queryAll();
                for (Riji u : list) {

                    Log.d("ning", u.toString());

                }
                int i;
                for(i=0;i<list.size();i++)
                {
                    ul+="关键字："+list.get(i).guanjianzi+",总结："+list.get(i).zongjie+"\n";
                }
                tv_txt_look.setText(ul);

               /* et_xuehao.setText(String.valueOf(list.get(0).id));
                // System.out.println(list.get(0).id);
                //et_name.setText(list.get(0).name);
                et_banji.setText(list.get(0).banji);
                et_shouji.setText(list.get(0).shouji);*/
                break;

        }

    }
}
