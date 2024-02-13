package com.example.spring.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.spring.Database.BannerData;
import com.example.spring.MyNetRequest;
import com.example.spring.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText mEtUesrName;
    private TextInputEditText mEtPassWord;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private CheckBox mCbRemember;
    SharedPreferences loginPreference;
    private Handler mHandler;
    private final String mPostUrlLogin = "https://www.wanandroid.com/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        Register();
        Login();
    }

    private void Login() {
        mBtnLogin.setOnClickListener(v -> login());
    }

    private void login() {
        HashMap<String,String> sure=new HashMap<>();
        sure.put("username",mEtUesrName.getText().toString());
        sure.put("password",mEtPassWord.getText().toString());
        sure.put("repassword",mEtPassWord.getText().toString());
        MyNetRequest sureInformation=new MyNetRequest(mPostUrlLogin,sure,mHandler);
    }
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String responseData = msg.obj.toString();
            judge(decodeJson(responseData));
        }
    }

    private void judge  (BannerData bannerData) {
        if(bannerData.errorCode==-1){
            loginSuccess();
        }
        else {
            loginFailure();
        }
    }



    //登录失败
    private void loginFailure() {
        Toast.makeText(this, "账号或者密码好像输错了 :(",
                Toast.LENGTH_SHORT).show();
    }
    //登录成功并按要求保存账号和密码
    private void loginSuccess() {
        SharedPreferences.Editor registrar=loginPreference.edit();
        if(mCbRemember.isChecked()){
            registrar.putString("username",mEtUesrName.getText().toString());
            registrar.putString("password",mEtPassWord.getText().toString());
            registrar.putBoolean("check",mCbRemember.isChecked());
        }else{
            registrar.remove("username");
            registrar.remove("password");
            registrar.remove("check");
        }
        registrar.apply();
        Toast.makeText(this, "登陆成功!", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this, MusicActivity.class);
        startActivity(intent);
    }

    private BannerData decodeJson(String data) {
// 数据存储对象
        BannerData bannerData = new BannerData();
        try {
// 获得这个JSON对象{}
            JSONObject jsonObject = new JSONObject(data);
// 获取并列的三个，errorCode，errorMsg，data
            bannerData.errorCode = jsonObject.getInt("errorCode");
            bannerData.errorMsg = jsonObject.getString("errorMsg");
// data是⼀个对象集合
        }catch (Exception e){
            Log.w("lx","(JsonActivity.java:59)-->>",e);
        }
        return bannerData;
    }


    private void Register() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initial() {
        mEtUesrName = findViewById(R.id.et_main_username);
        mEtPassWord = findViewById(R.id.et_main_password);
        mBtnLogin = findViewById(R.id.btn_main_login);
        mBtnRegister = findViewById(R.id.btn_main_register);
        mBtnRegister.getBackground().setAlpha(0);
        mCbRemember = findViewById(R.id.cb_main_remember);
        mHandler=new MyHandler();
        //建立一个记住密码的储存，并且按要求读取
        loginPreference = getSharedPreferences("remember", MODE_PRIVATE);
        if (loginPreference.getBoolean("check", false)) {
            mEtPassWord.setText((CharSequence) loginPreference.getString("password", ""));
            mCbRemember.setChecked(loginPreference.getBoolean("check", false));
        }
    }
}