package com.example.swetamac.demoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextpassword;
    private Button registerBtn;
    private TextView mainScreentextView;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog( this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextpassword = (EditText) findViewById(R.id.editTextPassword);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        mainScreentextView = (TextView) findViewById(R.id.maintextView);

        registerBtn.setOnClickListener(this);
        mainScreentextView.setOnClickListener(this);
    }

    public void startRegister(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please fillup both the textfields!",Toast.LENGTH_LONG).show();
            return;

        }
        progressDialog.setMessage("Registering User....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"The user is succcessfully registered!!",Toast.LENGTH_SHORT).show();


                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Verification e-mail has been sent.Please Verify your e-mail!!",Toast.LENGTH_LONG).show();
                               //String url = firebaseAuth.getCurrentUser().getEmail();
                                String url = "http://www.outlook.com";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                                finish();

                                if(user.isEmailVerified()){
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                }

                               //startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                                progressDialog.dismiss();

//                                if(task.isComplete()){
//                                    if (firebaseAuth.getCurrentUser().isEmailVerified() == true){
//                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                                        }
//
//                                        //startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                                }

//
                            }
                        }
                    });

                }else{
                    Toast.makeText(MainActivity.this,"Error..Failed to register!!",Toast.LENGTH_LONG).show();

                }

            }
        });


    }


    @Override
    public void onClick(View v) {

        if(v == registerBtn){
            startRegister();
        }

        if(v == mainScreentextView){
            //Screen transition method to Login Screen.

            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }

    }
}
