package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.Update;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.dao.UserDao;
import com.example.test.db.AppDatabase;
import com.example.test.entity.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnrollActivity extends AppCompatActivity {

    private EditText idEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private Button enrollButton;
    private Button deleteButton;
    private AppDatabase appDatabase;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        idEditText = findViewById(R.id.idEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        enrollButton = findViewById(R.id.enrollButton);
        deleteButton =findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDao.deleteAll();
            }
        });

        appDatabase = Room.databaseBuilder(this,AppDatabase.class,"user_database")
                        .allowMainThreadQueries()
                                .build();
        userDao = appDatabase.userDao();

        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputId = idEditText.getText().toString();
                String inputPhone = phoneEditText.getText().toString();
                String inputPassword = passwordEditText.getText().toString();
                boolean isLetters = isLetters(inputId);
                boolean isElevenDigits = isElevenDigits(inputPhone);
                boolean isAlphaNumeric = isAlphaNumeric(inputPassword);
                if (isLetters && isElevenDigits && isAlphaNumeric) {
                    User user = new User();
                    user.id = inputId;
                    user.phone = inputPhone;
                    user.password = inputPassword;
                    userDao.insert(user);
                    Toast.makeText(EnrollActivity.this, "输入的是2-20位字母数字\n输入的是11位数字\n输入是8-20位字母加数字", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EnrollActivity.this, "请检查输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isLetters(String input) {
        String pattern = "^[A-Za-z\\d]{2,20}$";//使用正则表达式匹配2-20位字母和数字
        return input.matches(pattern);
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