package com.example.jump;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.ViewGroupUtils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.ViewUtils;

import java.util.Random;

public class Actdenglu extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
   private TextView tv_password;
   private EditText et_password;
    private Button btn_forget;
    private CheckBox ck_remember;
    private EditText et_phone;
    
    private RadioButton rb_verifycode;
    private ActivityResultLauncher<Intent> register;
    private Button btn_login;
    private String verifyCode;
    private String mPassword="111111";
    private Button btn_zhuce;
    private RadioButton rb_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actdenglu);
        RadioGroup rb_login=findViewById(R.id.rg_login);
         tv_password=findViewById(R.id.tv_password);
         et_password=findViewById(R.id.et_password);
         rb_password = findViewById(R.id.rb_password);
        rb_verifycode=findViewById(R.id.rb_verifycode);
        btn_forget=findViewById(R.id.btn_forget);
        ck_remember=findViewById(R.id.ck_remember);
        et_phone=findViewById(R.id.et_phone);
        btn_login=findViewById(R.id.btn_login);
        btn_zhuce=findViewById(R.id.btn_zhuce);
        //单选监听器
        rb_login.setOnCheckedChangeListener(this);
        //给et_phone设置文本变更监听器
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone,11));
        et_password.addTextChangedListener(new HideTextWatcher(et_password,6));
        //按钮监听
        btn_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_zhuce.setOnClickListener(this);
         register =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch(i){
            //选择密码登录
            case R.id.rb_password:
                tv_password.setText(getString(R.string.login_password));
                et_password.setHint(getString(R.string.input_password));
                btn_forget.setText(getString(R.string.forget_password));
                ck_remember.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_verifycode:
                tv_password.setText(getString(R.string.verifycode));
                et_password.setHint(getString(R.string.input_verifycode));
                btn_forget.setText(getString(R.string.get_verifycode));
                ck_remember.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onClick(View v) {
        String phone=et_phone.getText().toString();

        switch (v.getId()){
            case R.id.btn_forget:
                if(phone.length()<11){
                    Toast.makeText(this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(rb_password.isChecked()){
                    Intent intent=new Intent(this,Wangjimima.class);
                    intent.putExtra("phone",phone);
                    register.launch(intent);
                } else if (rb_verifycode.isChecked()) {
                    //生成随机的验证码
                     verifyCode=String.format("%06d",new Random().nextInt(999999));
                    //弹出对话框，提示六位数字
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("请记住验证码");
                    builder.setMessage("手机号"+phone+",本次验证码是"+verifyCode+",请输入验证码");
                    builder.setPositiveButton("好的",null);
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                break;
            case R.id.btn_login:
                if(phone.length()<11){
                    Toast.makeText(this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                //密码校验
                if(rb_password.isChecked()){
                  if(!mPassword.equals(et_password.getText().toString())) {
                      Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                      return;
                  }
                    loginSuccess();
                    startActivity(new Intent(this,first.class));
                  }
                  //提示登录成功

                  //验证码登录
                  else if (rb_verifycode.isChecked()) {
                    if (!verifyCode.equals(et_password.getText().toString())) {
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loginSuccess();
                    startActivity(new Intent(this,first.class));
                }
                break;
            case R.id.btn_zhuce:
                startActivity(new Intent(this,Actzhuce.class));
                break;
        }
    }

    private void loginSuccess() {
        String desc=String.format("您的手机号是%s，通过验证",et_phone.getText().toString());
        //弹出对话框，提示登录成功
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setNegativeButton("确定返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("我再看看",null);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    //长度达到之后键盘的隐藏
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxlength;
        public HideTextWatcher(EditText et, int i) {
            this.mView=et;
            this.mMaxlength=i;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().length()==mMaxlength){

            }
        }
    }
}