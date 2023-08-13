package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private LinearLayout questionContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        fab = findViewById(R.id.fab);
        questionContainer = findViewById(R.id.questionContainer);

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
                        addSingleChoice();
                    }
                });

                multipleChoiceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addMultipleChoice();
                    }
                });

                essayQuestionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addEssayQuestion();
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

    private void addSingleChoice() {//中英切换太麻烦了，本方法中全部以垃圾英语注释
        //New a LinearLayout of a section
        LinearLayout sectionLinearLayout = new LinearLayout(this);
        sectionLinearLayout.setOrientation(LinearLayout.VERTICAL);
        //New a LinearLayout of a question of a section
        LinearLayout questionLinearLayout = new LinearLayout(this);
        questionLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        questionLinearLayout.setGravity(Gravity.CENTER);
        // 创建一个新的EditText
        EditText questionEditText = new EditText(this);
        //设置EditText的提示文本
        questionEditText.setHint("请输入该单选题题目描述");
        //将EditText添加到LinearLayout中
        questionLinearLayout.addView(questionEditText);
        //创建一个新的Button
        Button button = new Button(this);
        // 设置按钮的文本
        button.setText("确认");
        //将EditText添加到LinearLayout中
        questionLinearLayout.addView(button);
        //Add a LinearLayout to another
        sectionLinearLayout.addView(questionLinearLayout);
        //New an EditText of a choice
        EditText choiceEditText = new EditText(this);
        choiceEditText.setGravity(Gravity.CENTER);
        choiceEditText.setHint("请输入一个选项");
        //Add an EditText to a LinearLayout
        sectionLinearLayout.addView(choiceEditText);
        //将LinearLayout添加到布局中
        questionContainer.addView(sectionLinearLayout);
    }

    private void addMultipleChoice() {
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

    private void addEssayQuestion() {
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