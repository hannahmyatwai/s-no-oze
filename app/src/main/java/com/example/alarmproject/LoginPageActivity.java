package com.example.alarmproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class LoginPageActivity extends AppCompatActivity {
    EditText username, password;
    Button button_login;
    FirebaseAuth fAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_sub);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();

        button_login = findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString().trim();
                String password_1 = password.getText().toString().trim();

                //check null
                if (TextUtils.isEmpty(email)) {
                    username.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password_1)) {
                    password.setError("Password is Required");
                    return;
                }
                if (password_1.length() < 6) {
                    password.setError("Password must be >= 6 Characters");
                    return;
                }

                //authenticate the user
                fAuth.signInWithEmailAndPassword(email, password_1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mFirebaseDatabase = FirebaseDatabase.getInstance();
                            myRef = mFirebaseDatabase.getReference();
                            FirebaseUser user = fAuth.getCurrentUser();
                            userID = user.getUid();


                            Toast.makeText(LoginPageActivity.this, "You've successfully logged into your account", Toast.LENGTH_LONG).show();
                            Intent toMyAccount = new Intent(getApplicationContext(), MyAccountAcitivity.class);
                            startActivity(toMyAccount);

                        } else {
                            Toast.makeText(LoginPageActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();


                        }
                    }
                });

            }


        });
    }
}
