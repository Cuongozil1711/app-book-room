package com.example.finalandroid.activity.authen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.finalandroid.Common.Common;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaceBookAuthenActivity extends LoginActivity {
    private CallbackManager callbackManager;
    private ProgressDialogCustom progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Intent intent = new Intent();
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        context = this;
        progressDialog = new ProgressDialogCustom(this);
        progressDialog.show();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile", "user_friends"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("6666");
                        progressDialog.hide();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("6666");
                        progressDialog.hide();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mUser = mAuth.getCurrentUser();
                        if (authResult.getAdditionalUserInfo().isNewUser()) {
                        }
                        progressDialog.hide();
                        success();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.hide();
                        Toast.makeText(FaceBookAuthenActivity.this, "Authen Facebook went wrong", Toast.LENGTH_SHORT).show();
                        error();
                        finish();
                    }
                });
    }

    private void success() {
        User user = new User();
        user.setAccessToken(null);
        user.setUuId(mUser.getUid());
        user.setName(mUser.getDisplayName());
        user.setImage(mUser.getPhotoUrl().toString());
        user.setPhone(mUser.getPhoneNumber());
        user.setEmail(mUser.getEmail());
        user.setTypeLogin(Common.TYPE_LOGIN.TYPE_FACE);

        ApiService.apiService.loginFaceOrGoogle(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null){
                    SqliteHelper db = new SqliteHelper(context);
                    List<User> listUser = db.getAllUser();
                    for(User us: listUser){
                        db.delete(us.getId());
                    }
                    User user1 = response.body();
                    db.addItem(user1);

                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    error();
                    Toast.makeText(FaceBookAuthenActivity.this, "Không thể đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                error();
                Toast.makeText(FaceBookAuthenActivity.this, "Không thể đăng nhập", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void error() {
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
