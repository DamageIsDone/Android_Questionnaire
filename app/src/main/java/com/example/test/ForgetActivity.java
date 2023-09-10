package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.dao.UserDao;
import com.example.test.db.AppDatabase;

import java.util.Random;

public class ForgetActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText codeEditText;
    private Button codeButton;
    private Button confirmButton;
    private int randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        usernameEditText = findViewById(R.id.usernameEditText);
        codeEditText = findViewById(R.id.codeEditText);
        codeButton = findViewById(R.id.codeButton);
        confirmButton = findViewById(R.id.confirmButton);

        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = usernameEditText.getText().toString();
                //用户名输入不为空时才可获得验证码
                if(inputUsername != null) {
                    Toast.makeText(ForgetActivity.this, "请正确输入手机号", Toast.LENGTH_SHORT).show();
                } else {
                    randomNumber = generateRandomNumber(); // 生成随机数
                    Log.d("flag","生成随机数"+randomNumber);
                    // 创建AlertDialog
                    AlertDialog alertDialog = new AlertDialog.Builder(ForgetActivity.this)
                            .setTitle("验证码")
                            .setMessage("您的验证码为：" + randomNumber)
                            .setPositiveButton("确定", null) // 设置确定按钮的点击事件
                            .show();
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = usernameEditText.getText().toString();
                int inputCode = Integer.parseInt(codeEditText.getText().toString());
                boolean isCode = isCode (inputCode);
                Log.d("flag","isCode是" + isCode);
                if (isCode) {
                    Log.d("flag","验证码正确");
                    AppDatabase db = Room.databaseBuilder( getApplicationContext(), AppDatabase.class, "user_database")
                            .allowMainThreadQueries().build();
                    UserDao userDao = db.userDao();
                    String password = userDao.findUserByPhone(inputUsername).password;
                    Log.d("flag","密码是" + password);
                    // 创建AlertDialog
                    AlertDialog alertDialog = new AlertDialog.Builder(ForgetActivity.this)
                            .setTitle("找回密码")
                            .setMessage("您的密码为：" + password)
                            .setPositiveButton("确定", null) // 设置确定按钮的点击事件
                            .show();
                } else {
                    Toast.makeText(ForgetActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isCode(int input) {
        return randomNumber==input;
    }

    private int generateRandomNumber() {
        int min = 100000;
        int max = 999999;
        return new Random().nextInt(max - min + 1) + min;
    }

    private boolean isElevenDigits(String input) {
        String pattern = "^\\d{11}$"; // 使用正则表达式匹配11位数字
        return input.matches(pattern);
    }
}