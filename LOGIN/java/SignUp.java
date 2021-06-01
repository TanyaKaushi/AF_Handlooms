package com.example.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText name,password,number;
    Button btn;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.uName);
        password = findViewById(R.id.pas);
        number = findViewById(R.id.num);

        loadingBar = new ProgressDialog(this);

        btn = findViewById(R.id.signIn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String nm = name.getText().toString();
        String pas = password.getText().toString();
        String num = number.getText().toString();

        if (TextUtils.isEmpty(nm)){
            Toast.makeText(getApplicationContext(),"Please enter your name :",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pas)){
            Toast.makeText(getApplicationContext(),"Please enter a password :",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(num)){
            Toast.makeText(getApplicationContext(),"Please enter your contact number :",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatePhone(nm,pas,num);
        }
    }

    private void validatePhone(final String nm, final String pas, final String num) {

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Customer").child(num).exists())){
                    HashMap<String, Object> cusData = new HashMap<>();
                    cusData.put("phone", num);
                    cusData.put("password", pas);
                    cusData.put("name", nm);

                    ref.child("Customer").child(num).updateChildren(cusData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Your Account has been created!",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent i = new Intent(getApplicationContext(),login.class);
                                        i.putExtra("number",num);
                                        startActivity(i);

                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(),"Try Again!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(getApplicationContext(),"This contact number is already exits!",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    Intent i = new Intent(getApplicationContext(),login.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}