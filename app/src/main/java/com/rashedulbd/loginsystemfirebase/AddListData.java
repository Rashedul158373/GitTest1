package com.rashedulbd.loginsystemfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class AddListData extends AppCompatActivity {

    private EditText fullName,fatherName,motherName,nidNumber,address;
    private Button addFire;
    private TextView display;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_data);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        findViewById();

        addFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_fullName = fullName.getText().toString();
                String user_fatherName = fatherName.getText().toString();
                String user_motherName = motherName.getText().toString();
                String user_nidnumber = nidNumber.getText().toString().trim();
                String user_address = address.getText().toString();

                if (!TextUtils.isEmpty(user_fullName)
                        && !TextUtils.isEmpty(user_fatherName)
                        && !TextUtils.isEmpty(user_motherName)
                        && !TextUtils.isEmpty(user_address)){

                    if (!TextUtils.isEmpty(user_nidnumber) && (user_nidnumber.length() == 10) ||(user_nidnumber.length() == 17)){

                        /*AddData sendData = new AddData(user_fullName, user_fatherName, user_motherName, user_nidnumber, user_address);
                        databaseReference.push().setValue(sendData);

                        Toast.makeText(AddListData.this, "Add Successfull", Toast.LENGTH_LONG).show();

                        fullName.setText("");
                        fatherName.setText("");
                        motherName.setText("");
                        nidNumber.setText("");
                        address.setText("");*/

                        String dateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                        AddData setData = new AddData(user_fullName, user_fatherName, user_motherName, user_nidnumber, user_address);
                        databaseReference.child(dateTime).setValue(setData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(AddListData.this, "Add Successfull", Toast.LENGTH_LONG).show();

                                    fullName.setText("");
                                    fatherName.setText("");
                                    motherName.setText("");
                                    nidNumber.setText("");
                                    address.setText("");

                                    //gfjhbjkyug
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddListData.this, "Add Failed", Toast.LENGTH_LONG).show();

                            }
                        });


                    }else {
                        nidNumber.setError("inter number 10 or 17");

                    }
                }
                else {
                    Toast.makeText(AddListData.this, "Error found", Toast.LENGTH_SHORT).show();



                }

            }
        });
    }

    private void findViewById() {
        fullName = findViewById(R.id.full_name_id);
        fatherName = findViewById(R.id.father_name_id);
        motherName = findViewById(R.id.mother_name_id);
        nidNumber = findViewById(R.id.nid_id);
        address = findViewById(R.id.address_id);

        addFire = findViewById(R.id.submit_button_id);
        display = findViewById(R.id.display_id);
    }


    @Override
    protected void onStart() {
        super.onStart();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = "";
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                    AddData addData = itemSnapshot.getValue(AddData.class);
                    String oName = addData.getFull_Name();
                    String fName  =addData.getFather_Name();
                    String mName = addData.getMother_Name();
                    String nNumber = addData.getNid_Number();
                    String aAddress = addData.getAddress_loc();

                    data = data +"Name: "+oName + "\nF.Name: "+fName+"\nM.Name: "
                            +mName+"\nNID no: "+nNumber+"\nAddress: "+aAddress+"\n\n";
                }
                display.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddListData.this, "Error Found to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
