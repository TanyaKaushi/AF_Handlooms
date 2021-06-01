package com.example.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView text, text2;
    Button btn,bt,bt2,bt3;
    Animation ani,animtwo;
    ImageView imgani;
    ImageButton bt8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final String s2=getIntent().getStringExtra("number");

        ani = AnimationUtils.loadAnimation(this,R.anim.ani);
        animtwo = AnimationUtils.loadAnimation(this,R.anim.animtwo);


        btn = findViewById(R.id.cat);
        bt = findViewById(R.id.offer);
        bt2 = findViewById(R.id.noti);
        bt3 = findViewById(R.id.rOrder);
        bt8 = findViewById(R.id.profile);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Category.class);
                startActivity(i);
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),FirstPage.class);
                startActivity(i);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ImagesActivity.class);
                startActivity(i);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Return_order.class);
                startActivity(i);
            }
        });

        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),userDetails.class);
                i.putExtra("number",s2);
                startActivity(i);
            }
        });


        //animation
        //btn.startAnimation(ani);
        //bt.startAnimation(animtwo);
        //bt2.startAnimation(animtwo);
        //bt3.startAnimation(animtwo);

    }
}