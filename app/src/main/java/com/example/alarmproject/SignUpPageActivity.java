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

public class SignUpPageActivity extends AppCompatActivity {
    EditText username, password, user;
    Button button_signup;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    public final static String A_KEY = "A_KEY";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_sub);

        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        user = findViewById(R.id.register_nickname);
        button_signup = findViewById(R.id.button_signup);

        fAuth = FirebaseAuth.getInstance();

        //check if the user had logged in before
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                String email = username.getText().toString().trim();
                String password_1 = password.getText().toString().trim();
                String user1 = user.getText().toString().trim();

                //check null
                if (TextUtils.isEmpty(email)){
                    username.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password_1)){
                    password.setError("Password is Required");
                    return;
                }

                if (password_1.length() < 6){
                    password.setError("Password must be >= 6 Characters");
                    return;
                }


                //register the user in firebase
                fAuth.createUserWithEmailAndPassword(email, user1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //add to firebase database
                            FirebaseUser mfirebaseuser = fAuth.getCurrentUser();
                            String userid = mfirebaseuser.getUid();
                            int score = 0;
                            UserHelper helperClass = new UserHelper(email, password_1, user1, score);
                            reference.child(user1).setValue(helperClass);
                            Toast.makeText(SignUpPageActivity.this, "User Created", Toast.LENGTH_LONG).show();
                            Intent toAccount = new Intent(getApplicationContext(), MyAccountAcitivity.class);
                            toAccount.putExtra(A_KEY, user1);
                            startActivity(toAccount);
                        }else{
                            Toast.makeText(SignUpPageActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });


    }
}
