package com.example.developer.logindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    RelativeLayout myLayout;
    AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        registerButton = (Button) findViewById(R.id.registerBtn);

        registerButton.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        myLayout = (RelativeLayout) findViewById(R.id.myLayout);
        animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(1150);
        animationDrawable.start();

    }

    @Override
    public void onClick(View view) {
        if(view == registerButton) {
            registerUser();
        }
    }


    public void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(email.isEmpty()) {
            return;
        }

        if(password.isEmpty()) {
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    // Call WelcomeActivity here

                    Intent welcome = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(welcome);

                    Toast.makeText(MainActivity.this, "Registered!", Toast.LENGTH_SHORT);
                    Log.d("Firebase", "Registered!");
                } else {
                    // If login has failed
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT);
                }
            }
        });

        progressDialog.hide();
    }
}
