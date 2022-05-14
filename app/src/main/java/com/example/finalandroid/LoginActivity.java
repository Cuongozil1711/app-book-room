package com.example.finalandroid;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText phone;
    private Button btLogin;
    private TextView btRegister;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private User userLogin;
    private String phoneEdit = "";
    private Context context;
    private ProgressDialogCustom progressDialogCustom;
    private SqliteHelper sql;
    private static final String TAG = LoginActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        auth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        phone = findViewById(R.id.phone);
        btLogin = findViewById(R.id.btnLogin);
        btRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        context = this;
        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());

        progressDialogCustom = new ProgressDialogCustom(this);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        sql = new SqliteHelper(this);
    }

    private void login(){
        phoneEdit = phone.getText().toString();
        progressDialogCustom.show();
//        if(TextUtils.isEmpty(phoneEdit)){
//            Toast.makeText(this, "Vui lòng nhập phone", Toast.LENGTH_SHORT).show();
//            return;
//        }
        //phoneEdit = "+84 974886013";
//        if(TextUtils.isEmpty(passEdit)){
//            Toast.makeText(this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        auth.signInWithEmailAndPassword(emailEdit, passEdit).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                progressBar.setVisibility(View.GONE);
//                if(task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(i);
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//

//        SMS
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(auth)
//                        .setPhoneNumber(phoneEdit)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                            @Override
//                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                progressBar.setVisibility(View.GONE);
//                                signInWithPhoneAuthCredential(phoneAuthCredential);
//                            }
//
//                            @Override
//                            public void onVerificationFailed(@NonNull FirebaseException e) {
//                                progressBar.setVisibility(View.GONE);
//                                Toast.makeText(getApplicationContext(), "The verification failed", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                            @Override
//                            public void onCodeSent(@NonNull String verfyID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                super.onCodeSent(verfyID, forceResendingToken);
//                                progressBar.setVisibility(View.GONE);
//                                goToEnterActivity(phoneEdit, verfyID);
//                            }
//                        })          // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
        loginToServer(phoneEdit);
    }

    public void loginToServer(String phone){
        progressDialogCustom.show();
        ApiService.apiService.login(phone).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressDialogCustom.hide();
                userLogin = response.body();
                SqliteHelper db = new SqliteHelper(context);

                User user = db.getUser();
                if(user != null)
                db.delete(user.getId());

                db.addItem(userLogin);
                if(userLogin != null){
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("user", userLogin);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialogCustom.hide();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToEnterActivity(String phoneNumber, String verfyID){
        Intent i = new Intent(LoginActivity.this, EnterOtpActivity.class);
        i.putExtra("phone_number", phoneNumber);
        i.putExtra("verfy_id", verfyID);
        startActivity(i);
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
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
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


    public void register(View v){
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if(userLogin != null) return;
        super.onBackPressed();
    }
}
