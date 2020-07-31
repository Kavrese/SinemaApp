package com.example.sinemaapp.classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinemaapp.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class StartActivity extends AppCompatActivity {
TextView reg;
Button enter;
EditText pas,log;
MotionLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        pas = findViewById(R.id.password_user);
        log = findViewById(R.id.name_user);
        enter = findViewById(R.id.enter);
        reg = findViewById(R.id.reg);
        layout = findViewById(R.id.motionLayout);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pas.getText().toString().isEmpty()){
                    Snackbar.make(enter,"Введите пароль", BaseTransientBottomBar.LENGTH_SHORT);
                }else if(log.getText().toString().isEmpty()) {
                    Snackbar.make(enter,"Введите логин/ник", BaseTransientBottomBar.LENGTH_SHORT);
                }else{
                    Intent in = new Intent(StartActivity.this,MainActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        });
    }
}