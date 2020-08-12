package com.securegate;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;



public class LoginActivity extends AppCompatActivity {

    private EditText loginfield1, loginfield2;
    private TextView loginforgotpassword;
    private Button loginbutton, tologin, tosignup;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth =FirebaseAuth.getInstance();
        loginfield1 = findViewById(R.id.loginfield1);
        loginfield2 = findViewById(R.id.loginfield2);
        loginbutton = findViewById(R.id.loginbutton);
        loginforgotpassword = findViewById(R.id.loginforgotpassword);

        tologin =  findViewById(R.id.tologin);
        tosignup =  findViewById(R.id.tosignup);


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        loginforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toforgotpassword = new Intent(LoginActivity.this,Forgotpassword.class);
                startActivity(toforgotpassword);
            }
        });

        tosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tosignup =  new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(tosignup);
            }
        });
    }
        private void loginUserAccount(){

            String email, password;
            email = loginfield1.getText().toString();
            password = loginfield2.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please verify!", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){
            startActivity(new Intent(LoginActivity.this, FingerprintSignin.class));
            finish();
        }
    }
}