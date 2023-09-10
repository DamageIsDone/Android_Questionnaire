package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.Update;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.dao.UserDao;
import com.example.test.db.AppDatabase;
import com.example.test.entity.User;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class EnrollActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private Button enrollButton;
    private Button deleteButton;
    private AppDatabase appDatabase;
    private UserDao userDao;
    private EditText codeEditText;
    private Button codeButton;
    private int randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        enrollButton = findViewById(R.id.enrollButton);
        deleteButton = findViewById(R.id.deleteButton);
        codeEditText = findViewById(R.id.codeEditText);
        codeButton = findViewById(R.id.codeButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDao.deleteAll();
            }
        });

        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = emailEditText.getText().toString();
                //邮箱输入不为空时才可获得验证码
                if(inputEmail == null) {
                    Toast.makeText(EnrollActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                } else {
                    //以下是虚假的生成验证码操作
                    randomNumber = generateRandomNumber(); // 生成随机数
                    Log.d("flag","生成随机数"+randomNumber);
                    // 创建AlertDialog
                    AlertDialog alertDialog = new AlertDialog.Builder(EnrollActivity.this)
                            .setTitle("验证码")
                            .setMessage("您的验证码为：" + randomNumber)
                            .setPositiveButton("确定", null) // 设置确定按钮的点击事件
                            .show();
                }
            }
        });

        appDatabase = Room.databaseBuilder(this,AppDatabase.class,"user_database")
                        .allowMainThreadQueries()
                                .build();
        userDao = appDatabase.userDao();

        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = usernameEditText.getText().toString();
                String inputPassword = passwordEditText.getText().toString();
                String inputEmail = emailEditText.getText().toString();
                int inputCode = Integer.parseInt(codeEditText.getText().toString());
                boolean isAlphaNumeric = isAlphaNumeric(inputPassword);
                boolean isCode = isCode (inputCode);
                if (isCode && inputUsername != null && isAlphaNumeric && inputEmail != null) {

//                    //尝试网络请求
//                    // 创建OkHttpClient实例
//                    OkHttpClient okHttpClient = new OkHttpClient();
//                    String loginUrl = "https://"; // 替换为login地址
//                    RequestBody requestBody = new FormBody.Builder()
//                            .add("username", inputUsername)
//                            .add("password", inputPassword)
//                            .add("email",inputEmail)
//                            .build();

                    //先用Room写了
                    User user = new User();
                    user.phone = inputUsername;
                    user.password = inputPassword;
                    userDao.insert(user);
                    Log.d("flag","输入正确");
                    Toast.makeText(EnrollActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EnrollActivity.this, "请检查输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 生成六位随机数的方法
    private int generateRandomNumber() {
        int min = 100000;
        int max = 999999;
        return new Random().nextInt(max - min + 1) + min;
    }

    private boolean isCode(int input) {
        return randomNumber==input;
    }

    private boolean isAlphaNumeric(String input) {
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$"; // 使用正则表达式匹配8-20位字母加数字
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

}