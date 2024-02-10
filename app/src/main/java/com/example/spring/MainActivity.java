package com.example.spring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
   private TextInputEditText mEtUerName;
   private TextInputEditText mEtPassWord;
   private Button mBtnLogin;
   private Button mBtnRegister;
private CheckBox mCbRemember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialView();
        Register()
    }

    private void Register() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void initialView() {
        mEtUerName=findViewById(R.id.et_main_username);
        mEtPassWord=findViewById(R.id.et_main_password);
        mBtnLogin=findViewById(R.id.btn_main_login);
        mBtnRegister=findViewById(R.id.btn_main_register);
        mCbRemember=findViewById(R.id.cb_main_remember);
    }
}