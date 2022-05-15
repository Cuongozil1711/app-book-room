package com.example.finalandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText phone, name;
    private Button btRegister;
    private ImageButton btBack;
    private FirebaseAuth auth;
    private ProgressDialogCustom progressDialogCustom;
    private Context context;
    private User userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_register);
        auth = FirebaseAuth.getInstance();
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        btRegister = findViewById(R.id.btnRegister);
        btBack = findViewById(R.id.btBack);

        context = this;
        progressDialogCustom = new ProgressDialogCustom(this);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });

    }

    public void register(){
        String phoneEdit, passEdit;
        phoneEdit = phone.getText().toString();
        passEdit = name.getText().toString();
        if(TextUtils.isEmpty(phoneEdit)){
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passEdit)){
            Toast.makeText(this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
            return;
        }

        callApiRegister(phoneEdit, passEdit);



//        auth.createUserWithEmailAndPassword(phoneEdit, passEdit).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
//                    startActivity(i);
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }

    public void callApiRegister(String phone, String name) {
        progressDialogCustom.show();
        ApiService.apiService.registerUser(phone, name).enqueue(new Callback<User>() {
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
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
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
                Toast.makeText(getApplicationContext(), "Call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(View view){
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
