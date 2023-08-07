package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.dao.UserDao;
import com.example.test.db.AppDatabase;
import com.example.test.entity.User;

public class LoginActivity extends AppCompatActivity {//启动页面

    private Button loginButton;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private CheckBox checkBox;

    private View.OnClickListener listener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            login();
        }
    };

    private void SucceededLogin() {
        if (checkBox.isChecked()) {
            rememberme(); //如果选中，将把数据保存到xml文件
            Log.d("flag","记住密码选中");
        } else {
            unrememberme(); //如果取消选中，则清除xml文件数据
            Log.d("flag","记住密码取消选中");
        }
    }

    private void rememberme() {
        String phone = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        SharedPreferences sp = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("rememberme", true);
        editor.putString("phone", phone);
        editor.putString("password", password);
        editor.apply();
        Log.d("flag", "数据保存成功");
    }

    private void unrememberme() {
        SharedPreferences sp = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        Log.d("flag", "数据已删除");
    }

    private void readSP() {
        SharedPreferences sp = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        Boolean remember = sp.getBoolean("rememberme", false);
        if( remember ) {
            checkBox.setChecked(true);
            String phone = sp.getString("phone", "");
            String password = sp.getString("password", "");
            phoneEditText.setText(phone);
            passwordEditText.setText(password);
        }
    }

    private void login() {
        String phone = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        AppDatabase db = Room.databaseBuilder( getApplicationContext(), AppDatabase.class, "user_database")
                .allowMainThreadQueries().build();
        UserDao userDao = db.userDao();
        User user = userDao.findUser( phone, password );
        if ( user == null ) {
            Toast.makeText(LoginActivity.this, "电话号或密码错误", Toast.LENGTH_SHORT).show();
        } else {
            SucceededLogin();
            Log.d("flag","成功登录");
            Toast.makeText(LoginActivity.this, "登录中...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        checkBox = findViewById(R.id.checkBox);

        readSP();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //点击注册账号，跳转至注册页面
        //start
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView enrollTextView = findViewById(R.id.enrollTextView);
        enrollTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, EnrollActivity.class);
                startActivity(intent);
            }
        });
        //end

    }

}