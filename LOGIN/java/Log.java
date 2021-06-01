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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Log extends AppCompatActivity {

    EditText phone, password;
    TextView admin, nAdmin;
    Button button;
    String dbName = "Customer";
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);



        phone = findViewById(R.id.phone);
        password = findViewById(R.id.pas);
        admin = findViewById(R.id.admin);
        nAdmin = findViewById(R.id.notAdmin);

        button = findViewById(R.id.signIn);

        loadingBar = new ProgressDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginUser();
            }
        });

        /*ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "login");
                startActivity(intent);
            }
        });*/

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                button.setText("Login Admin");
                admin.setVisibility(View.INVISIBLE);
                nAdmin.setVisibility(View.VISIBLE);
                dbName = "Admin";
            }
        });

        nAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                button.setText("Login");
                admin.setVisibility(View.VISIBLE);
                nAdmin.setVisibility(View.INVISIBLE);
                dbName = "Customer";
            }
        });
    }



    private void LoginUser()
    {
        String ph = phone.getText().toString();
        String pas = password.getText().toString();

        if (TextUtils.isEmpty(ph))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pas))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(ph, pas);
        }
    }



    private void AllowAccessToAccount(final String phone, final String password)
    {
        /*if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }*/


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        final String s1=getIntent().getStringExtra("number");


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(dbName).child(phone).exists())
                {
                    User usersData = dataSnapshot.child(dbName).child(phone).getValue(User.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            if (dbName.equals("Admin"))
                            {
                                Toast.makeText(Log.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(Log.this, FinalOrders.class);
                                startActivity(intent);
                            }
                            else if (dbName.equals("Customer"))
                            {
                                Toast.makeText(Log.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(Log.this, Home.class);
                                intent.putExtra("number",s1);
                                //Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(Log.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(Log.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}



