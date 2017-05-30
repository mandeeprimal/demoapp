package com.example.swetamac.demoapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FormActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView formtextView;
    private Button logoutBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        formtextView = (TextView) findViewById(R.id.FormtextView);
        logoutBtn = (Button) findViewById(R.id.FormBtn);
        logoutBtn.setOnClickListener(this);


        FirebaseUser user = firebaseAuth.getCurrentUser();
        formtextView.setText("Welcome "+user.getEmail()+ " User" );

    }

    @Override
    public void onClick(View v) {
        if(v == logoutBtn){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    }
}
