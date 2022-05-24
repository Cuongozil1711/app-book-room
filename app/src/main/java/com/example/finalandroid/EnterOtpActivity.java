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

import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.dto.UserCodeDto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterOtpActivity extends AppCompatActivity {
    private TextView phone;
    private EditText otpEdit;
    private Button btOtp;
    private UserCodeDto userCodeDto;
    private User user;
    private SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        phone = findViewById(R.id.phone);
        otpEdit = findViewById(R.id.otpCode);
        btOtp  = findViewById(R.id.btnLogin);
        sqliteHelper = new SqliteHelper(this);
        loadDataIntent();

        btOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSendOtp(otpEdit.getText().toString().trim());
            }
        });
    }

    private void onClickSendOtp(String otp) {
        if(otpEdit.getText().toString().length() > 0){
            ApiService.apiService.checkSmsUser(otpEdit.getText().toString(),userCodeDto.getUiId()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    if(user == null){
                        Toast.makeText(getApplicationContext(), "Otp không chính xác", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        sqliteHelper.addItem(user);
                        Intent i = new Intent(EnterOtpActivity.this, MainActivity.class);
                        i.putExtra("user", user);
                        startActivity(i);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

    private void loadDataIntent(){
        userCodeDto = (UserCodeDto) getIntent().getSerializableExtra("userCodeDto");
    }
}