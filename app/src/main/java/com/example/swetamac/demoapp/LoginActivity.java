package com.example.swetamac.demoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText LoginEmailEditText;
    private EditText LoginPasswordEditText;
    private Button Loginbtn;
    private TextView forgotPasswordTxt;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView signupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //If user is already signed in*************************
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), mainScreenActivity.class));

        }

        LoginEmailEditText = (EditText) findViewById(R.id.loginEmailTxt);
        LoginPasswordEditText = (EditText) findViewById(R.id.loginPasswordTxt);
        Loginbtn = (Button) findViewById(R.id.loginBtn);
        signupTextView = (TextView) findViewById(R.id.signUptextView);
        forgotPasswordTxt = (TextView) findViewById(R.id.loginForgotTextview);
        progressDialog = new ProgressDialog(this);

        Loginbtn.setOnClickListener(this);
        forgotPasswordTxt.setOnClickListener(this);
        signupTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == Loginbtn){
            loginUser();
        }

        if(v == forgotPasswordTxt){
            sendPasswordResetEmail();
        }

        if(v == signupTextView){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    private  void loginUser(){
        String email = LoginEmailEditText.getText().toString().trim();
        String password = LoginPasswordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please fillup both the textfields!",Toast.LENGTH_SHORT).show();
            return;

        }
        progressDialog.setMessage("Please Wait...Logging In");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                    Toast.makeText(LoginActivity.this,"You have successfully logged in.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), mainScreenActivity.class));

                }
            }
        });



    }

    private void sendPasswordResetEmail(){
        firebaseAuth = FirebaseAuth.getInstance();
        String emailAddress = LoginEmailEditText.getText().toString().trim();

        firebaseAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressDialog.setMessage("Sending Password Reset E-mail....");
                    Toast.makeText(LoginActivity.this,"Reset E-mail is successfuly sent.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
