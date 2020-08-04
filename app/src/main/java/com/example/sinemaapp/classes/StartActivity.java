package com.example.sinemaapp.classes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinemaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
TextView reg;
Button enter,email_good;
EditText pas,log;
MotionLayout layout;
LinearLayout lin,lin_email;
boolean ver_good;
boolean exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        lin = findViewById(R.id.lin);
        lin_email = findViewById(R.id.lin_email);
        pas = findViewById(R.id.password_user);
        log = findViewById(R.id.name_user);
        email_good = findViewById(R.id.good_email);
        enter = findViewById(R.id.enter);
        reg = findViewById(R.id.reg);
        layout = findViewById(R.id.motionLayout);
        if(FirebaseAuth.getInstance().getCurrentUser() != null){        //Пользователь уже входил раньше
            Intent in = new Intent(StartActivity.this,MainActivity.class);
            startActivity(in);
            finish();
        }
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pas.getText().toString().isEmpty()){
                    Snackbar.make(enter,"Введите пароль", BaseTransientBottomBar.LENGTH_SHORT).show();
                }else if(log.getText().toString().isEmpty()) {
                    Snackbar.make(enter,"Введите логин/ник", BaseTransientBottomBar.LENGTH_SHORT).show();
                }else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(log.getText().toString(),pas.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        sendVer(log.getText().toString(),pas.getText().toString());
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(reg,e.getMessage(),BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pas.getText().toString().isEmpty()){
                    Snackbar.make(enter,"Введите пароль", BaseTransientBottomBar.LENGTH_SHORT).show();
                }else if(log.getText().toString().isEmpty()) {
                    Snackbar.make(enter,"Введите логин/ник", BaseTransientBottomBar.LENGTH_SHORT).show();
                }else{
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(log.getText().toString(),pas.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent in = new Intent(StartActivity.this,MainActivity.class);
                                        startActivity(in);
                                        finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(reg,e.getMessage(),BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void sendVer(final String email, final String password) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        layout.setTransition(R.id.tra4);
                        layout.transitionToEnd();
                        lin.setClickable(false);
                        Button back = findViewById(R.id.back_email);
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                layout.transitionToStart();
                                lin.setClickable(true);
                            }
                        });
                        Snackbar.make(pas,"Письмо отправленно",BaseTransientBottomBar.LENGTH_SHORT).show();
                        email_good.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseAuth.getInstance().signOut();
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    ver_good = FirebaseAuth.getInstance().getCurrentUser().isEmailVerified();
                                                    if(ver_good) {
                                                        Snackbar.make(reg, "Адрес потверждён", BaseTransientBottomBar.LENGTH_SHORT).show();
                                                        new FireBaseConnect().setBdUser("no","no",email,password,false,email.substring(0,email.indexOf("@")),lin);
                                                        Intent in = new Intent(StartActivity.this,MainActivity.class);
                                                        startActivity(in);
                                                        finish();
                                                    }else
                                                        Snackbar.make(reg,"Адрес не потверждён",BaseTransientBottomBar.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
                      }
                    }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if(!exit){
            exit = true;
            Snackbar.make(enter,"Нажмите ещё раз что-бы выйти",BaseTransientBottomBar.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            },2000);
        }else{
            finish();
        }
    }
}