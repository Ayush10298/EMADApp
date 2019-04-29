package com.example.emadapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login;
    Button btnnewuser;
    private EditText email;
    private EditText password;
    private CheckBox saveLoginCheckBox;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login =(Button)findViewById(R.id.login);
        login.setOnClickListener(this);

        email=(EditText)findViewById(R.id.emaillogin);
        btnnewuser = (Button) findViewById(R.id.btnnewuser);
        password=(EditText)findViewById(R.id.passwordlogin);

        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            email.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        progressDialog=new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();

        btnnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));

            }
        });
    }

    private void userLogin()
    {
        String putEmail = email.getText().toString().trim();
        String putPassword = password.getText().toString().trim();
        if(TextUtils.isEmpty(putEmail))
        {
            Toast.makeText(this,"Plaese enter the email",Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(putPassword))
        {
            Toast.makeText(this,"Please enter the password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(putEmail,putPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            finish();
                            startActivity(new Intent(getApplicationContext(),NaviActivity.class));

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"login Failed please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

//        if(view == signin){
//            finish();
//            startActivity(new Intent(this,MainActivity.class));
//        }

        if(view == login)
        {
            String putEmail = email.getText().toString();
            String putPassword = password.getText().toString();

            if (saveLoginCheckBox.isChecked()) {
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("username", putEmail);
                loginPrefsEditor.putString("password", putPassword);
                loginPrefsEditor.commit();
            } else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
            }
            userLogin();

        }
    }


    public void resetpass(View v)
    {
        startActivity(new Intent(MainActivity.this,ForgetPasswordActivity.class));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
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
