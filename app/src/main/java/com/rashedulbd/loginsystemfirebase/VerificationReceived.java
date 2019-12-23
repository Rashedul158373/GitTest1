package com.rashedulbd.loginsystemfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerificationReceived extends AppCompatActivity {

    private TextView timeTV;
    private EditText verifyCodeET;
    private Button verifyBtn, reSendBtn;
    ProgressBar progressBar;

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private static final String KEY_MILLIS_LEFT = "KEYmILLISlEFT";
    private CountDownTimer countDownTimer;
    private long timeLeftMillis;
    String codeSend, phoneNumber;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_received);

        firebaseAuth = FirebaseAuth.getInstance();

        timeTV = findViewById(R.id.timerTV);
        verifyCodeET = findViewById(R.id.inputCodeEt);
        verifyBtn = findViewById(R.id.verifyBtn);
        reSendBtn = findViewById(R.id.resendBtn);
        progressBar = findViewById(R.id.progressBar);

        final Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("mobile");

        timeLeftMillis = COUNTDOWN_IN_MILLIS;
        startCountdown();
        sendVerifycode();

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = verifyCodeET.getText().toString().trim();
                if (!code.isEmpty() && code.length() == 6){
                    verifySinginCode();

                }
                else {
                    Toast.makeText(VerificationReceived.this, "Verification code dosen't match", Toast.LENGTH_SHORT).show();

                }

//                Intent intent = new Intent(VerificationReceived.this, Home.class);
//                startActivity(intent);
            }
        });

        reSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });





    }

    private void verifySinginCode() {
        progressBar.setVisibility(View.VISIBLE);
        String code = verifyCodeET.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSend,code);
        signInWithCredential(credential);

    }

    private void signInWithCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    sendMainMenu();
                    finish();
                }else {
                    progressBar.setVisibility(View.GONE);
                    if (task.getException() instanceof FirebaseAuthInvalidUserException){
                        Toast.makeText(VerificationReceived.this,
                                "Invalid Verification Code", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void sendMainMenu() {
        Intent intent = new Intent(VerificationReceived.this,Home.class);
        startActivity(intent);
        finish();
    }


    private void startCountdown() {

        countDownTimer = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long lon) {
                timeLeftMillis = lon;
                updateCountdown();


            }

            @Override
            public void onFinish() {
                timeLeftMillis = 0;
                updateCountdown();
                reSendBtn.setVisibility(View.VISIBLE);


            }
        }.start();
    }

    private void updateCountdown() {
        int min = (int)(timeLeftMillis / 1000) / 60;
        int sec = (int)(timeLeftMillis / 1000) % 60;
        String timeFor = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        timeTV.setText(timeFor);
    }

    private void sendVerifycode() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+88"+
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);

    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSend = s;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user =  firebaseAuth.getCurrentUser();
        if (user != null){
            sendMainMenu();
            finish();
        }
    }
}
