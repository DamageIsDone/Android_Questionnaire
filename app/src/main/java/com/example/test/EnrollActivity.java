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

public class EnrollActivity extends AppCompatActivity {

    private EditText phoneEditText;
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

        phoneEditText = findViewById(R.id.phoneEditText);
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
                String inputPhone = phoneEditText.getText().toString();
                boolean isElevenDigits = isElevenDigits(inputPhone);
                //电话号码输入不为空时才可获得验证码
                if(!isElevenDigits) {
                    Toast.makeText(EnrollActivity.this, "请正确输入手机号", Toast.LENGTH_SHORT).show();
                } else {
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
                String inputPhone = phoneEditText.getText().toString();
                String inputPassword = passwordEditText.getText().toString();
                String inputEmail = emailEditText.getText().toString();
                int inputCode = Integer.parseInt(codeEditText.getText().toString());
                boolean isElevenDigits = isElevenDigits(inputPhone);
                boolean isAlphaNumeric = isAlphaNumeric(inputPassword);
                boolean isCode = isCode (inputCode);
                if (isCode && isElevenDigits && isAlphaNumeric && !inputEmail.isEmpty()) {
                    User user = new User();
                    user.phone = inputPhone;
                    user.password = inputPassword;
                    userDao.insert(user);
                    Log.d("flag","输入的是2-20位字母数字\n输入的是11位数字\n输入是8-20位字母加数字\n邮箱不为空");
                } else {
                    Toast.makeText(EnrollActivity.this, "请检查输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 生成六位随机数的辅助方法
    private int generateRandomNumber() {
        int min = 100000;
        int max = 999999;
        return new Random().nextInt(max - min + 1) + min;
    }

    private boolean isCode(int input) {
        return randomNumber==input;
    }

    private boolean isElevenDigits(String input) {
        String pattern = "^\\d{11}$"; // 使用正则表达式匹配11位数字
        return input.matches(pattern);
    }

    private boolean isAlphaNumeric(String input) {
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$"; // 使用正则表达式匹配8-20位字母加数字
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

}