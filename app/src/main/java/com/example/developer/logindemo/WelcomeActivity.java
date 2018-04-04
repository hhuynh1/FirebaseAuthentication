package com.example.developer.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database; // URL
    DatabaseReference reference;

    EditText messageEditText;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Initialize instance variables
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        send = (Button) findViewById(R.id.sendButton);

        send.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("ITEC3870");


    }

    @Override
    public void onClick(View view) {
        if(view == send) {
            writeToDatabase();
            messageEditText.setText("");
        }
    }

    public void writeToDatabase() {

        String userInput = messageEditText.getText().toString().trim();

        reference.setValue(userInput);
    }
}
