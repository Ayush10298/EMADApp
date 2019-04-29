package com.example.emadapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputname, inputphone, inputcpassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressDialog loadingBar;
    String RID;
    private FirebaseAuth auth;
    public static final String TAG = "Trial App";
    static int count=1;
    String password, cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.btnlogin);
        btnSignUp = (Button) findViewById(R.id.getstarted);
        inputname = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputcpassword=(EditText)findViewById(R.id.cpassword);
        inputphone=(EditText)findViewById(R.id.number);


        loadingBar = new ProgressDialog(this);

        //password=inputPassword.get;
        //cpassword=inputcpassword.getText().toString();
        // btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        //btnResetPassword.setOnClickListener(new View.OnClickListener() {
        //@Override
//            public void onClick(View v) {
//                startActivity(new Intent(signup.this, ResetPasswordActivity.class));
//            }
//        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //inputcpassword.getText().toString();

                //Log.d(TAG, "onClick: +++++++++++ COnfirm Password "+cpass.toString());
                Log.d(TAG, "onClick: ++++++++++++password "+password);
                Log.d(TAG, "onClick: ++++++++++++Cpassword "+ cpassword);
                if(inputPassword.getText().toString().equals(inputcpassword.getText().toString())) {
                    Log.d(TAG, "onClick: ++++++++++++ Inside IF loop");
                    String name = inputname.getText().toString().trim();
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    String phone = inputphone.getText().toString().trim();

                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (phone.length() != 10) {
                        Toast.makeText(getApplicationContext(), "Phone Number incorrect, enter only 10 digits!", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    Log.d(TAG, "CreateAccount: ++++++++++++before validate");
                    CreateAccount(name, email, phone, password);

                }

                else{
                    Toast.makeText(RegisterActivity.this, "Password and Confirm Password is not matching. Kindly check once more", Toast.LENGTH_LONG).show();
                    inputcpassword.setText("");
                }
                    //loadingBar.setVisibility(View.VISIBLE);
                    //create user
            }
        });

    }

    private void CreateAccount(final String name, final String email,final String phone, final String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            StoreInDatabase(name,email,phone,password);
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void StoreInDatabase(final String name,final String email, final String phone, final String password)
    {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "ValidatephoneNumber: +++++++++++++++++++after validate "+count);
        if(count<10) {
            RID = "R0000" + count;
        }
        else if(count >= 10){
            RID="R000"+count;
        }

        Log.d(TAG, "StoreInDatabase: ++++++++++ RID======="+RID);
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Log.d(TAG, "onDataChange: ++++++++before if");
                if (!(dataSnapshot.child("Users").child(RID).exists()))
                {
                    Log.d(TAG, "onDataChange: +++++before if and hashmap");
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", name);
                    userdataMap.put("email", email);
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);

                    Log.d(TAG, "onDataChange: +++++++after hashmap");
                    RootRef.child("Users").child(RID).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    Log.d(TAG, "onComplete: +++++++++oncomplete");
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        inputEmail.setText("");
                                        inputname.setText("");
                                        inputPassword.setText("");
                                        inputphone.setText("");
                                        count++;
                                        //Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        //startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Kindly, register again", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    //Toast.makeText(RegisterActivity.this, "Please try again using another Phone Number.", Toast.LENGTH_LONG).show();
                    inputphone.setText("");
                    //Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    //startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingBar.dismiss();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RegisterActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
