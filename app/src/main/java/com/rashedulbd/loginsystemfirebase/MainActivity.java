package com.rashedulbd.loginsystemfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText UserNameET, UserPasswordET;
    private Button LoginBtn, RegisterBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar LoginPB;
    String userName, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserNameET = findViewById(R.id.userIdET);
        UserPasswordET = findViewById(R.id.userPasswordET);
        LoginBtn = findViewById(R.id.loginBtn);
        RegisterBtn = findViewById(R.id.registationBtn);
        LoginPB = findViewById(R.id.loginPB);
        //Realtime Permission
        firebaseAuth = FirebaseAuth.getInstance();




        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginPB.setVisibility(View.VISIBLE);
                userName = UserNameET.getText().toString();
                userPassword = UserPasswordET.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(userName, userPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            LoginBtn.setVisibility(View.GONE);
                            finish();
                        }
                        else {

                            Toast.makeText(MainActivity.this, "User name or Password invalid", Toast.LENGTH_SHORT).show();
                            LoginBtn.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword)){
//
//                    firebaseAuth.createUserWithEmailAndPassword(userName,userPassword)
//                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()){
//                                        Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//                                    }else {
//                                        Toast.makeText(MainActivity.this, "Sorry Registration Not Complete", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }else {
//                    UserNameET.setError("Enter User");
//                    UserPasswordET.setError("Enter Password");
//                    Toast.makeText(MainActivity.this, "Error occurs", Toast.LENGTH_SHORT).show();
//                }


                Intent intent= new Intent(MainActivity.this, MobileVerificationSend.class);
                startActivity(intent);
            }
        });
    }
    protected void onStart() {
        super.onStart();

        FirebaseUser user =  firebaseAuth.getCurrentUser();
        if (user != null){
            sendMainMenu();
            finish();
        }
    }

    private void sendMainMenu() {
        Intent intent = new Intent(MainActivity.this,Home.class);
        startActivity(intent);
        finish();
    }
}
