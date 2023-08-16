package com.example.test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class NewActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private LinearLayout questionContainer;
    private TextView returnTextView;
    private TextView finishTextView;
    private boolean flag = true;
    private boolean sign;
    private int sequence = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        ActionBar actionBar = getSupportActionBar(); //获取ActionBar
        actionBar.hide(); //隐藏ActionBar

        fab = findViewById(R.id.fab);
        questionContainer = findViewById(R.id.questionContainer);
        returnTextView = findViewById(R.id.returnTextView);
        finishTextView = findViewById(R.id.finishTextView);

        returnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog();
            }
        });

        finishTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
                        popupWindow.dismiss();
                    }
                });

                multipleChoiceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag) {
                            sign = true;
                            addMultipleChoice();
                        } else {
                            Toast.makeText(NewActivity.this, "请添加选项", Toast.LENGTH_SHORT).show();
                        }
                        popupWindow.dismiss();
                    }
                });

                essayQuestionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag) {
                            sign = true;
                            addEssayQuestion();
                        } else {
                            Toast.makeText(NewActivity.this, "请添加选项", Toast.LENGTH_SHORT).show();
                        }
                        popupWindow.dismiss();
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

    private void tipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewActivity.this);
        builder.setTitle("提示：");
        builder.setMessage("退出将不会保存您的修改");

        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("flag","确认退出新建问卷");
                finish();
                Intent intent = new Intent(NewActivity.this, MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("flag","取消退出新建问卷");
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        //对话框显示的监听事件
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.d("flag", "对话框显示了");
            }
        });
        //对话框消失的监听事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d("flag", "对话框消失了");
            }
        });
        dialog.show();                              //显示对话框
    }

    private void addSingleChoice() {
        sequence++;
        flag = false;
        final int[] cnt = {0};
        //New a LinearLayout of a section
        LinearLayout sectionLinearLayout = new LinearLayout(this);
        sectionLinearLayout.setOrientation(LinearLayout.VERTICAL);
        sectionLinearLayout.setGravity(Gravity.CENTER);

        //New a LinearLayout of a title
        LinearLayout titleLinearLayout = new LinearLayout(this);
        titleLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleLinearLayout.setGravity(Gravity.CENTER);
        //New a TextView of the sequence of a section
        TextView sequenceTextView = new TextView(this);
        //Set the sequence
        sequenceTextView.setText(sequence + ".                                                  ");
        titleLinearLayout.addView(sequenceTextView);
        //New a Button to delete the present section
        Button deleteButton = new Button(this);
        deleteButton.setText("删除问题");
        titleLinearLayout.addView(deleteButton);
        sectionLinearLayout.addView(titleLinearLayout);

        //New a LinearLayout of a question of a section
        LinearLayout questionLinearLayout = new LinearLayout(this);
        questionLinearLayout.setOrientation(LinearLayout.VERTICAL);
        questionLinearLayout.setGravity(Gravity.CENTER);
        //New an EditText of a question
        EditText questionEditText = new EditText(this);

        LinearLayout.LayoutParams params1000W = new LinearLayout.LayoutParams(1000, LinearLayout.LayoutParams.WRAP_CONTENT);
        questionEditText.setLayoutParams(params1000W);
        questionEditText.setX(-10);

        //Set the hint of the EditText of a choice
        questionEditText.setHint("请输入该单选题题目描述");
        //Add the EditText of a question to the LinearLayout of a question
        questionLinearLayout.addView(questionEditText);
        //New a Button for adding
        Button addButton = new Button(this);
        addButton.setLayoutParams(params1000W);
        addButton.setX(-10);
        //Set the text of the Button
        addButton.setText("添加选项");
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
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("flag","删除");
                deleteSection(sectionLinearLayout);
            }
        });
    }

    private void addMultipleChoice() {
        sequence++;
        flag = false;
        final int[] cnt = {0};
        //New a LinearLayout of a section
        LinearLayout sectionLinearLayout = new LinearLayout(this);
        sectionLinearLayout.setOrientation(LinearLayout.VERTICAL);
        sectionLinearLayout.setGravity(Gravity.CENTER);

        //New a LinearLayout of a title
        LinearLayout titleLinearLayout = new LinearLayout(this);
        titleLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleLinearLayout.setGravity(Gravity.CENTER);
        //New a TextView of the sequence of a section
        TextView sequenceTextView = new TextView(this);
        //Set the sequence
        sequenceTextView.setText(sequence + ".                                                  ");
        titleLinearLayout.addView(sequenceTextView);
        //New a Button to delete the present section
        Button deleteButton = new Button(this);
        deleteButton.setText("删除问题");
        titleLinearLayout.addView(deleteButton);
        sectionLinearLayout.addView(titleLinearLayout);

        //New a LinearLayout of a question of a section
        LinearLayout questionLinearLayout = new LinearLayout(this);
        questionLinearLayout.setOrientation(LinearLayout.VERTICAL);
        questionLinearLayout.setGravity(Gravity.CENTER);
        //New an EditText of a question
        EditText questionEditText = new EditText(this);

        LinearLayout.LayoutParams params1000W = new LinearLayout.LayoutParams(1000, LinearLayout.LayoutParams.WRAP_CONTENT);
        questionEditText.setLayoutParams(params1000W);
        questionEditText.setX(-10);

        //Set the hint of the EditText of a choice
        questionEditText.setHint("请输入该多选题题目描述");
        //Add the EditText of a question to the LinearLayout of a question
        questionLinearLayout.addView(questionEditText);
        //New a Button for adding
        Button addButton = new Button(this);
        addButton.setLayoutParams(params1000W);
        addButton.setX(-10);
        //Set the text of the Button
        addButton.setText("添加选项");
        //Add the Button for adding to the LinearLayout of a question
        questionLinearLayout.addView(addButton);
        //Add the LinearLayout of a question to the LinearLayout of a section
        sectionLinearLayout.addView(questionLinearLayout);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //New an EditText of a choice
                cnt[0]++;
                if (cnt[0] <= 8 && sign) {
                    addChoice(sectionLinearLayout);
                } else {
                    cnt[0] = 0;
                    sign = false;
                    Toast.makeText(NewActivity.this, "多选题至多添加八个选项", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Add the LinearLayout of a section to the Container
        questionContainer.addView(sectionLinearLayout);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("flag","删除");
                deleteSection(sectionLinearLayout);
            }
        });
    }

    private void addChoice(LinearLayout sectionLinearLayout) {
        flag = true;
        EditText choiceEditText = new EditText(this);
        choiceEditText.setHint("请输入一个选项");
        LinearLayout.LayoutParams params1000W = new LinearLayout.LayoutParams(1000, LinearLayout.LayoutParams.WRAP_CONTENT);
        choiceEditText.setLayoutParams(params1000W);
        choiceEditText.setX(-10);
        //Add the EditText of a choice to the LinearLayout of a section
        sectionLinearLayout.addView(choiceEditText);
    }

    private void addEssayQuestion() {
        flag = true;
        sequence++;
        //New a LinearLayout of a section
        LinearLayout sectionLinearLayout = new LinearLayout(this);
        sectionLinearLayout.setOrientation(LinearLayout.VERTICAL);
        sectionLinearLayout.setGravity(Gravity.CENTER);

        //New a LinearLayout of a title
        LinearLayout titleLinearLayout = new LinearLayout(this);
        titleLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleLinearLayout.setGravity(Gravity.CENTER);
        //New a TextView of the sequence of a section
        TextView sequenceTextView = new TextView(this);
        //Set the sequence
        sequenceTextView.setText(sequence + ".                                                  ");
        titleLinearLayout.addView(sequenceTextView);
        //New a Button to delete the present section
        Button deleteButton = new Button(this);
        deleteButton.setText("删除问题");
        titleLinearLayout.addView(deleteButton);
        sectionLinearLayout.addView(titleLinearLayout);

        //New a LinearLayout of a question of a section
        LinearLayout questionLinearLayout = new LinearLayout(this);
        questionLinearLayout.setOrientation(LinearLayout.VERTICAL);
        questionLinearLayout.setGravity(Gravity.CENTER);
        //New an EditText of a question
        EditText questionEditText = new EditText(this);

        LinearLayout.LayoutParams params1000W = new LinearLayout.LayoutParams(1000, LinearLayout.LayoutParams.WRAP_CONTENT);
        questionEditText.setLayoutParams(params1000W);
        questionEditText.setX(-10);

        //Set the hint of the EditText of a choice
        questionEditText.setHint("请输入该问答题题目描述");
        //Add the EditText of a question to the LinearLayout of a question
        questionLinearLayout.addView(questionEditText);
        //Add the LinearLayout of a question to the LinearLayout of a section
        sectionLinearLayout.addView(questionLinearLayout);
        //Add the LinearLayout of a section to the Container
        questionContainer.addView(sectionLinearLayout);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("flag","删除");
                deleteSection(sectionLinearLayout);
            }
        });
    }

    private void deleteSection(LinearLayout sectionLinearLayout) {
        sequence--;
        flag = true;
        questionContainer.removeViewInLayout(sectionLinearLayout);
    }
}