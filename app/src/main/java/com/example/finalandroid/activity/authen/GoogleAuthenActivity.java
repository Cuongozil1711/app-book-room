package com.example.finalandroid.activity.authen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalandroid.Common.Common;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleAuthenActivity extends LoginActivity {
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialogCustom progressDialog;
    private Intent intent = new Intent();
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialogCustom(this);
        progressDialog.show();
        context = this;

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // configre google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("114941331279-tauo0as0l8cvads1r5sgndhokupmkdhd.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.revokeAccess();

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
                finish();
            }
        }
    }

    private void firebaseAuthWithGoogleAccount(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
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
                        Toast.makeText(GoogleAuthenActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        error();
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
        user.setTypeLogin(Common.TYPE_LOGIN.TYPE_GOOGLE);

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
                    Toast.makeText(GoogleAuthenActivity.this, "Không thể đăng nhập", Toast.LENGTH_SHORT).show();
                    error();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(GoogleAuthenActivity.this, "Không thể đăng nhập", Toast.LENGTH_SHORT).show();
                error();
            }
        });


    }

    private void error() {
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
