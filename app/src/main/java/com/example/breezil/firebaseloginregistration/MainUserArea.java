package com.example.breezil.firebaseloginregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainUserArea extends AppCompatActivity implements View.OnClickListener {
    private TextView getEmail;
    private Button buttonLogout,saveButton;
    private FirebaseAuth mAuth;
    private EditText fullname,Address;
    private DatabaseReference mDataref;
    private Button sleepBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_area);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(MainUserArea.this,LoginActivity.class));
        }
        mDataref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        fullname = (EditText) findViewById(R.id.fullName);
        Address = (EditText) findViewById(R.id.address);
        saveButton = (Button) findViewById(R.id.saveBtn);
        getEmail = (TextView) findViewById(R.id.emailget);
        getEmail.setText("Welcome "+user.getEmail());
        buttonLogout = (Button) findViewById(R.id.LogoutBtn);
        buttonLogout.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        sleepBtn.setOnClickListener(this);
    }

    private void saveInfo(){
        String name = fullname.getText().toString().trim();
        String address = Address.getText().toString().trim();

        UserInfo userInfo = new UserInfo(name,address);
        FirebaseUser user = mAuth.getCurrentUser();
        mDataref.child(user.getUid()).setValue(userInfo);
        Toast.makeText(this,"Information saved...",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogout){
            mAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(v==saveButton){
            saveInfo();
        }

        if (v == sleepBtn){

            startActivity(new Intent(this, HomePageActivity.class));
        }


    }

}
