package com.example.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userDetails extends AppCompatActivity {

    TextView nm,pw,cn;
    Button b1,b2,b3;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        final String s1=getIntent().getStringExtra("number");

        nm = findViewById(R.id.name);
        pw = findViewById(R.id.password);
        cn = findViewById(R.id.num);

        b1 = findViewById(R.id.done);
        b2 = findViewById(R.id.update);
        b3 = findViewById(R.id.delete);

        /*final String number = cn.getText().toString();
        final String pas = pw.getText().toString();

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Customer").child(number).exists()) {
                    //User usersData = dataSnapshot.child("Customer").child(number).getValue(User.class);

                    //if (usersData.getPhone().equals(number)) {
                        //if (usersData.getPassword().equals(pas)) {
                            number.setText(dataSnapshot.child("name").getValue().toString());
                            pw.setText(dataSnapshot.child("password").getValue().toString());
                            cn.setText(dataSnapshot.child("phone").getValue().toString());
                        //}
                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/



        DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("Customer").child(s1);
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
                Intent i = new Intent(getApplicationContext(),Home.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),updateProfile.class);
                i.putExtra("number",s1);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dlRef = FirebaseDatabase.getInstance().getReference().child("Customer");
                dlRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(s1)){
                            ref =FirebaseDatabase.getInstance().getReference().child("Customer").child(s1);
                            ref.removeValue();
                            Toast.makeText(getApplicationContext(),"Deleted successfully!",Toast.LENGTH_LONG).show();


                            Intent i = new Intent(getApplicationContext(),login.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"No source to delete!",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

}



