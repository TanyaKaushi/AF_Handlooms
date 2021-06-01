package com.example.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class updateProfile extends AppCompatActivity {

    EditText nm,pw,cn;
    Button b1;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        final String s2=getIntent().getStringExtra("number");

        nm = findViewById(R.id.name);
        pw = findViewById(R.id.password);
        cn = findViewById(R.id.num);

        b1 = findViewById(R.id.update);


        DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().getRef().child("Customer").child(s2);
        upRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    nm.setText(dataSnapshot.child("name").getValue().toString());
                    pw.setText(dataSnapshot.child("password").getValue().toString());
                    cn.setText(dataSnapshot.child("phone").getValue().toString());
                }
                else {
                    Toast.makeText(getApplicationContext(),"No source to display",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nm.getText().toString();
                String password = pw.getText().toString();
                String number = cn.getText().toString();

                ref = FirebaseDatabase.getInstance().getReference().child("Customer");

                HashMap hashMap = new HashMap();

                hashMap.put("name",name);
                hashMap.put("password",password);
                hashMap.put("phone",number);

                ref.child(s2).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){

                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}