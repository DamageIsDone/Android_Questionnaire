package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.dao.UserDao;
import com.example.test.db.AppDatabase;
import com.example.test.entity.User;

public class LoginActivity extends AppCompatActivity {//启动页面

    private Button loginButton;
    private EditText idEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }

            private void login() {
                String phone = idEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                AppDatabase db = Room.databaseBuilder( getApplicationContext(), AppDatabase.class, "user_database")
                        .allowMainThreadQueries().build();
                UserDao userDao = db.userDao();
                User user = userDao.findUser( phone, password );
                if ( user == null ) {
                    Toast.makeText(LoginActivity.this, "电话号或密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "登录中...", Toast.LENGTH_SHORT).show();
                }
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