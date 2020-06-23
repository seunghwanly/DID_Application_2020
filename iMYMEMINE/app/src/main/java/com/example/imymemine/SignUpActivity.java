package com.example.imymemine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.imymemine.Identify.IdentifyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private Button mButton;
    private EditText mEmailAddress, mPassword, mName;

    FirebaseAuth mFirebaseAuth;

    private static String TAG = "EmailActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEmailAddress = (EditText) findViewById(R.id.editMail);
        mName = (EditText) findViewById(R.id.editName);
        mPassword = (EditText) findViewById(R.id.verifyNumber);
        mButton = (Button) findViewById(R.id.signup_submit);

        //firebase
        mFirebaseAuth = FirebaseAuth.getInstance();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = mEmailAddress.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                mFirebaseAuth.createUserWithEmailAndPassword(emailAddress, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("users")
                                            .child(mFirebaseAuth.getCurrentUser().getUid())
                                            .child("Name")
                                            .setValue(mName.getText().toString());
                                    Toast.makeText(getApplicationContext(), "가입이 완료되었습니다!",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
                System.out.println("send email button clicked!");
            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}