package com.rashedulbd.loginsystemfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registation extends AppCompatActivity {

    private TextView userDOB;
    private EditText userFirstName, userLastName, userMobileNum, userEmail, userPresentAdd, userPermanentAdd, iD, pass;
    private Button RegistationBtn;
    private RadioGroup ganderRG;
    private ScrollView scrollView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        findViewById();
        firebaseAuth = FirebaseAuth.getInstance();

        final String email = userEmail.getText().toString();
        final String password = pass.getText().toString();



        RegistationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registation.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Registation.this, "Account creat success",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(Registation.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }

    public void findViewById(){

        userDOB = findViewById(R.id.dateOfBirthTV);
        userFirstName = findViewById(R.id.firstNameET);
        userLastName = findViewById(R.id.lastNameET);
        userMobileNum = findViewById(R.id.mobileET);
        userEmail = findViewById(R.id.emailET);
        userPresentAdd = findViewById(R.id.presentAddressET);
        userPermanentAdd = findViewById(R.id.permanentET);
        ganderRG = findViewById(R.id.ganderRG);
        RegistationBtn = findViewById(R.id.registerBtn);
        scrollView = findViewById(R.id.scrollView);
        iD = findViewById(R.id.iDET);
        pass = findViewById(R.id.passwordET );

    }
}
