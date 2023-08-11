package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//在Activity中重写onCreateOptionsMenu()方法
        getMenuInflater().inflate( R.menu.main_menu, menu );//将创建的菜单资源(名为main_menu)注入到Activity中
        return super.onCreateOptionsMenu(menu);//此行为默认代码
    }//放在MainActivity类内，onCreate方法外/下

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//菜单项参数
        switch ( item.getItemId() ) {//根据菜单id做分支
            case R.id.Change_Password:
                Intent intent1 = new Intent(MainActivity.this, ChangePasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.Log_Out:
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);//此行为默认代码
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        switch (item.getItemId()) {
                            case R.id.navigation_main:
                                selectedFragment = new Fragment1();
                                break;
                            case R.id.navigation2:
                                selectedFragment = new Fragment2();
                                break;
                            case R.id.navigation_profile:
                                selectedFragment = new Fragment3();
                                break;
                        }

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment).commit();

                        return true;
                    }
                });

        // 设置默认显示的Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Fragment1()).commit();
    }
}