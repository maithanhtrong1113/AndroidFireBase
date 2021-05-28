package com.example.android_firebase;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAction extends AppCompatActivity {
    EditText editEmail;
    EditText editPassword;
    Button btnLogin;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener( LoginAction.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if(password.length() <6){
                                editPassword.setError("Password nhập sai");
                            }else{
                                Toast.makeText( LoginAction.this,"Sai Email hoặc Password", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Intent intent = new Intent( LoginAction.this,MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("email",email);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}