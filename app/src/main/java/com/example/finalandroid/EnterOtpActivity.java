package com.example.finalandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class EnterOtpActivity extends AppCompatActivity {
    private TextView phone;
    private EditText otpEdit;
    private Button btOtp;
    private String phoneNumber, verfyId;
    private FirebaseAuth auth;
    private static final String TAG = EnterOtpActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        auth = FirebaseAuth.getInstance();
        phone = findViewById(R.id.phone);
        otpEdit = findViewById(R.id.otpCode);
        btOtp  = findViewById(R.id.btnLogin);
        loadDataIntent();

        btOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSendOtp(otpEdit.getText().toString().trim());
            }
        });
    }

    private void onClickSendOtp(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verfyId, otp);
    }

    private void loadDataIntent(){
        phoneNumber = getIntent().getStringExtra("phone_number");
        phone.setText(phoneNumber);
        verfyId = getIntent().getStringExtra("verfy_id");
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Intent i = new Intent(EnterOtpActivity.this, MainActivity.class);
                            startActivity(i);
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(), "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}