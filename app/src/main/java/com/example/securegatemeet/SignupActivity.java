package com.example.securegatemeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName, edtEmail, edtPassword, edtWing, edtFlat, edtSociety, edtPhone;
    private Button btnSignIn, btnSignup, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //getActionBar().setTitle("Register Here");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        edtFirstName = (EditText)findViewById(R.id.fnameEditText);
        edtLastName = (EditText)findViewById(R.id.lnameEditText);
        edtEmail = (EditText) findViewById(R.id.emailEditText);
        edtPassword = (EditText)findViewById(R.id.passwordEditText);
        edtWing = (EditText)findViewById(R.id.wingEditText);
        edtFlat = (EditText)findViewById(R.id.flatEditText);
        edtSociety = (EditText)findViewById(R.id.societyEditText);
        edtPhone = (EditText)findViewById(R.id.phoneEditText);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignup = (Button) findViewById(R.id.sign_up_button);
        btnResetPassword = (Button)findViewById(R.id.btn_reset_password);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fname = edtFirstName.getText().toString();
                final String lname = edtLastName.getText().toString();
                final String email = edtEmail.getText().toString();
                final String password = edtPassword.getText().toString();
                final String wing = edtWing.getText().toString();
                final String flat = edtFlat.getText().toString();
                final String society = edtSociety.getText().toString();
                final String phone = edtPhone.getText().toString();

                if (TextUtils.isEmpty(fname)) {
                    Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lname)) {
                    Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(wing)) {
                    Toast.makeText(getApplicationContext(), "Enter your Wing", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(flat)) {
                    Toast.makeText(getApplicationContext(), "Enter your Flat No.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(society)) {
                    Toast.makeText(getApplicationContext(), "Enter your Society", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter your Phone no.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Please Enter valid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //Create User
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "CreateUserWithEmail:OnComplete" +task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {

                                    String uid =auth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = reference.child(uid);

                                    current_user_db.child("firstName").setValue(fname);
                                    current_user_db.child("lastName").setValue(lname);
                                    current_user_db.child("email").setValue(email);
                                    current_user_db.child("password").setValue(password);
                                    current_user_db.child("wing").setValue(wing);
                                    current_user_db.child("flatNo").setValue(flat);
                                    current_user_db.child("society").setValue(society);
                                    current_user_db.child("phone").setValue(phone);

                                    Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this, DashBoardActivity.class));
                                    finish();

                                } else {

                                    Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
