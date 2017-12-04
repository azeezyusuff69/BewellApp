package com.example.breezil.firebaseloginregistration;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
     private EditText emailTxt;
     private EditText passWd;
     private Button regBtn;
     private TextView signIn;
     private ProgressDialog progDia;
     private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Intent mainArea = new Intent(MainActivity.this,MainUserArea.class);
            startActivity(mainArea);
        }
        progDia = new ProgressDialog(this);
        emailTxt = (EditText) findViewById(R.id.eMailText);
        passWd = (EditText) findViewById(R.id.ePassword);
        signIn = (TextView) findViewById(R.id.signIn);
        regBtn = (Button) findViewById(R.id.RegisterBtn);

        regBtn.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==regBtn){
            registerUser();
        }
        if(v==signIn) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void registerUser() {
        String email = emailTxt.getText().toString().trim();
        String password = passWd.getText().toString().trim();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter Email and Password",Toast.LENGTH_LONG).show();
            return;
        }else {
            progDia.setMessage("Signing Up");
            progDia.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progDia.dismiss();
                            if(task.isSuccessful()){
                                finish();
                                Intent userAreaIntent = new Intent(MainActivity.this,MainUserArea.class);
                                startActivity(userAreaIntent);
                            }else {
                                Toast.makeText(MainActivity.this,"Please Try Again",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }
}
