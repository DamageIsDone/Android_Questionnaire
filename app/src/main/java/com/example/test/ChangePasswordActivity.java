package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.dao.UserDao;
import com.example.test.db.AppDatabase;
import com.example.test.entity.User;

import java.util.Timer;
import java.util.TimerTask;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText originEditText;
    private EditText newEditText;
    private EditText confirmEditText;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        originEditText = findViewById(R.id.originEditText);
        newEditText = findViewById(R.id.newEditText);
        confirmEditText = findViewById(R.id.confirmEditText);
        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputOrigin = originEditText.getText().toString();
                String inputNew = newEditText.getText().toString();
                String inputConfirm = confirmEditText.getText().toString();
                AppDatabase db = Room.databaseBuilder( getApplicationContext(), AppDatabase.class, "user_database")
                        .allowMainThreadQueries().build();
                UserDao userDao = db.userDao();
                User user = userDao.findUserByPassword(inputOrigin);
                boolean isPassword = inputOrigin.equals(user.password);
                Log.d("flag","isPassword为" + isPassword);
                if (inputNew.equals(inputConfirm) && isPassword){
                    Log.d("flag","原始密码为：" + user.password);
                    if ( user != null ) {
                        Log.d("flag","修改成功");
                        user.password = inputNew;
                        userDao.update(user);
                        Log.d("flag","新密码为：" + user.password);
                        Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        //设置修改成功2秒后再跳转页面
                        Timer timer=new Timer();
                        TimerTask timerTask=new TimerTask() {
                            @Override
                            public void run() {
                                startActivity(new Intent(ChangePasswordActivity.this, MainActivity.class));
                                finish();
                            }
                        };timer.schedule(timerTask,1000);
                    } else {
                        Log.d("flag","if未执行");
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}