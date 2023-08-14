package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private LinearLayout questionContainer;
    private boolean flag = true;
    private boolean sign;

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
                        if (flag) {
                            sign = true;
                            addSingleChoice();
                        } else {
                            Toast.makeText(NewActivity.this, "请添加选项", Toast.LENGTH_SHORT).show();
                        }
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

    private void addSingleChoice() {//中英切换太麻烦了，以下全部以垃圾英语注释
        flag = false;
        final int[] cnt = {0};
        //New a LinearLayout of a section
        LinearLayout sectionLinearLayout = new LinearLayout(this);
        sectionLinearLayout.setOrientation(LinearLayout.VERTICAL);
        sectionLinearLayout.setGravity(Gravity.CENTER);
        //New a LinearLayout of a question of a section
        LinearLayout questionLinearLayout = new LinearLayout(this);
        questionLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        questionLinearLayout.setGravity(Gravity.CENTER);
        //New an EditText of a choice
        EditText questionEditText = new EditText(this);
        //Set the hint of the EditText of a choice
        questionEditText.setHint("请输入该单选题题目描述");
        //Add the EditText of a question to the LinearLayout of a question
        questionLinearLayout.addView(questionEditText);
        //New a Button for adding
        Button addButton = new Button(this);
        //Set the text of the Button
        addButton.setText("增加选项");
        //Add the Button for adding to the LinearLayout of a question
        questionLinearLayout.addView(addButton);
        //Add the LinearLayout of a question to the LinearLayout of a section
        sectionLinearLayout.addView(questionLinearLayout);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //New an EditText of a choice
                cnt[0]++;
                if (cnt[0] <= 4 && sign) {
                    addChoice(sectionLinearLayout);
                } else {
                    cnt[0] = 0;
                    sign = false;
                    Toast.makeText(NewActivity.this, "单选题至多添加四个选项", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Add the LinearLayout of a section to the Container
        questionContainer.addView(sectionLinearLayout);
    }

    private void addChoice(LinearLayout sectionLinearLayout) {
        flag = true;
        EditText choiceEditText = new EditText(this);
        choiceEditText.setHint("请输入一个选项");
        LinearLayout.LayoutParams params600W = new LinearLayout.LayoutParams(600, LinearLayout.LayoutParams.WRAP_CONTENT);
        choiceEditText.setLayoutParams(params600W);
        choiceEditText.setX(-200);
        //Add the EditText of a choice to the LinearLayout of a section
        sectionLinearLayout.addView(choiceEditText);
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