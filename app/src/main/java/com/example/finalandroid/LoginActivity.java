package com.example.finalandroid;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.finalandroid.activity.authen.FaceBookAuthenActivity;
import com.example.finalandroid.activity.authen.GoogleAuthenActivity;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.dto.UserCodeDto;
import com.example.finalandroid.model.ReviewHotel;
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

import java.util.List;
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
    private ImageView btnLoginFace, btnLoginGoogle;
    private static final String TAG = LoginActivity.class.getName();
    private final static int REQUEST_CODE_GOOGLE = 10000;
    private final static int REQUEST_CODE_FACEBOOK = 10001;
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
        btnLoginFace = findViewById(R.id.btnLoginFace);
        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);
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

        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorChecked));

        btnLoginFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FaceBookAuthenActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FACEBOOK);
            }
        });

        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GoogleAuthenActivity.class);
                startActivityForResult(intent, REQUEST_CODE_GOOGLE);
            }
        });
    }

    private void login(){
        phoneEdit = phone.getText().toString();
        progressDialogCustom.show();
        if(TextUtils.isEmpty(phoneEdit)){
            Toast.makeText(this, "Vui lòng nhập phone", Toast.LENGTH_SHORT).show();
            return;
        }
        loginToServer(phoneEdit);
    }

    public void loginToServer(String phone){
        progressDialogCustom.show();
        ApiService.apiService.login(phone).enqueue(new Callback<UserCodeDto>() {
            @Override
            public void onResponse(Call<UserCodeDto> call, Response<UserCodeDto> response) {
                progressDialogCustom.hide();
                    UserCodeDto userCodeDto = response.body();
                    SqliteHelper db = new SqliteHelper(context);
                    List<User> listUser = db.getAllUser();
                    for(User us: listUser){
                        db.delete(us.getId());
                    }

                    if(userCodeDto != null){
                        Intent i = new Intent(LoginActivity.this, EnterOtpActivity.class);
                        i.putExtra("userCodeDto", userCodeDto);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                    }
            }
            @Override
            public void onFailure(Call<UserCodeDto> call, Throwable t) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GOOGLE && resultCode == RESULT_OK) {
            finish();
        } else if (requestCode == REQUEST_CODE_FACEBOOK && resultCode == RESULT_OK) {
            finish();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
