package com.rashedulbd.loginsystemfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MobileVerificationSend extends AppCompatActivity {
    private EditText inputNumberEt;
    private CheckBox confirmCb;
    private Button verify;
    private boolean flag = false;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification_send);

        inputNumberEt = findViewById(R.id.inputNumberET);
        confirmCb = findViewById(R.id.confirmCb);
        verify = findViewById(R.id.verifySendBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile = inputNumberEt.getText().toString().trim();
                Intent intent = new Intent(MobileVerificationSend.this, VerificationReceived.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });

        confirmCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                String mobile = inputNumberEt.getText().toString().trim();
                int length = inputNumberEt.length();
                if (b){
                    flag = true;
                    verify.setEnabled(!mobile.isEmpty() && length == 11 && flag == true);
                }else {
                    verify.setEnabled(false);
                    flag = false;
                }
                flag = false;
            }
        });

        inputNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String mobile = inputNumberEt.getText().toString().trim();
                int length = inputNumberEt.length();
                if (confirmCb.isChecked()){
                    confirmCb.setChecked(false);
                }
                verify.setEnabled(!mobile.isEmpty() && length == 11 && flag == true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null){
            SendUserToMainmenu();
        }
    }

    private void SendUserToMainmenu() {

        Intent intent = new Intent(MobileVerificationSend.this,Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
