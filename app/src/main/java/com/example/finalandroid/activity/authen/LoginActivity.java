package com.example.finalandroid.activity.authen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.finalandroid.MainActivity;
import com.example.finalandroid.R;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.dto.UserCodeDto;
import com.example.finalandroid.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText phone;
    private Button btLogin;
    private TextView btRegister, btIgnore;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private String phoneEdit = "";
    private User user;
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
        btIgnore = findViewById(R.id.btIgnore);
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

        checkToken();

        btnLoginFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialogCustom.show();
                Intent intent = new Intent(LoginActivity.this, FaceBookAuthenActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FACEBOOK);
            }
        });

        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialogCustom.show();
                Intent intent = new Intent(LoginActivity.this, GoogleAuthenActivity.class);
                startActivityForResult(intent, REQUEST_CODE_GOOGLE);
            }
        });

        btIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkToken() {
        user = sql.getUser();
        if(user != null){
            ApiService.apiService.checkToken(user.getAccessToken()).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.body() != null){
                        int status = response.body();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void login(){
        phoneEdit = phone.getText().toString();
        progressDialogCustom.show();
        if(TextUtils.isEmpty(phoneEdit)){
            progressDialogCustom.hide();
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


    public void register(View v){
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GOOGLE && resultCode == RESULT_OK) {
            progressDialogCustom.hide();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        } else if (requestCode == REQUEST_CODE_FACEBOOK && resultCode == RESULT_OK) {
            progressDialogCustom.hide();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        } else {
            progressDialogCustom.hide();
            //Toast.makeText(this, "Không thể đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }
}
