package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建PopupWindow对象
                View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);
                PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setContentView(popupView);
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);

                // 设置弹窗显示的位置
                popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);

                Button singleChoiceButton = popupView.findViewById(R.id.singleChoiceButton);
                Button multipleChoiceButton = popupView.findViewById(R.id.multipleChoiceButton);
                Button essayQuestionButton = popupView.findViewById(R.id.essayQuestionButton);
                Button cancelButton = popupView.findViewById(R.id.cancelButton);

                //设置popupWindow中Button显示的位置
                singleChoiceButton.setY(155);
                multipleChoiceButton.setY(115);
                essayQuestionButton.setY(75);

                singleChoiceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                multipleChoiceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                essayQuestionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    private void add() {
        // 创建一个新的按钮
        Button button = new Button(this);
        // 设置按钮的文本
        button.setText("Click Me!");
        // 设置按钮的大小
        button.setWidth(10);
        button.setHeight(10);
        // 将按钮添加到布局中
        setContentView(button);
    }
}