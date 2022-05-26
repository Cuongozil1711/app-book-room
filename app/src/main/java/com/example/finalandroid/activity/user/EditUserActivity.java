package com.example.finalandroid.activity.user;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalandroid.R;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ConfigGetData;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.custom.RealPathUtil;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.model.User;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity{
    private static final int MY_REQUEST_CODE = 100;
    private TextView idPhone;
    private EditText idName, idEmail, eEdit;
    private RadioButton radio_nam, radio_nu;
    private ImageView profile_image;
    private Button btUpdate;
    private boolean checkChangeImage = false;
    private ProgressDialogCustom progressDialogCustom;
    private MultipartBody.Part multipartBody;
    private Uri uriData;
    private Context context;
    private SqliteHelper sq;
    private User user;
    private ImageButton btBack;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {

                        // compare the resultCode with the
                        // SELECT_PICTURE constant
                        checkChangeImage = true;
                        // Get the url of the image from data
                        Intent data = result.getData();
                        if (data == null) return;
                        Uri uri = data.getData();
                        uriData = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            profile_image.setImageBitmap(bitmap);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        idPhone = findViewById(R.id.idPhone);
        idName = findViewById(R.id.idName);
        idEmail = findViewById(R.id.idEmail);
        eEdit = findViewById(R.id.eEdit);
        radio_nam = findViewById(R.id.radio_nam);
        radio_nu = findViewById(R.id.radio_nu);
        profile_image = findViewById(R.id.profile_image);
        btUpdate = findViewById(R.id.btUpdate);
        btBack = findViewById(R.id.btBack);
        sq = new SqliteHelper(this);
        context = this;
        progressDialogCustom = new ProgressDialogCustom(this);
        progressDialogCustom.show();
        setData();
        eEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = java.util.Calendar.getInstance();
                int year = c.get(java.util.Calendar.YEAR);
                int month = c.get(java.util.Calendar.MONTH);
                int day = c.get(java.util.Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(EditUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date = "";
                        if (m > 8) {
                            date = d + "/" + (m + 1) + "/" + y;
                        } else {
                            date = d + "/0" + (m + 1) + "/" + y;
                        }
                        eEdit.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermision();
            }
        });


        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setName(idName.getText().toString().trim());
                user.setEmail(idEmail.getText().toString().trim());
                if(radio_nam.isChecked())
                user.setGener("Nam");
                else user.setGener("Nữ");
                user.setAge(eEdit.getText().toString());
                if (user != null) {
                    progressDialogCustom.show();
                    if(uriData != null){
//                        String user
                        String repath = RealPathUtil.getRealPath(context, uriData);
                        File file = new File(repath);
                        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("*/*"), file);
                        multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestBodyImage);
                    }
                    ApiService.apiService.updateUser(multipartBody, Integer.valueOf(user.getId()), user.getPhone(), user.getEmail(), user.getAge(), user.getGener(), user.getAccessToken()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            progressDialogCustom.hide();
                            User user1 = response.body();
                            if(user1 != null){
                                sq.update(user1);
                                Toast.makeText(getApplicationContext(), "Cập nhật thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            progressDialogCustom.hide();
                            Toast.makeText(getApplicationContext(), "Call api error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    void setData(){
        user = sq.getUser();
        if (user != null) {
            idPhone.setText(user.getPhone());
            idName.setText(user.getName());
            idEmail.setText(user.getEmail());
            eEdit.setText(user.getAge());
            if(user.getGener() == null)  radio_nam.setSelected(true);
            else{
                if(user.getGener().equals("Nam")){
                    radio_nam.setSelected(true);
                }
                else{
                    radio_nu.setSelected(true);
                }
            }

            if (user.getImage() != null) {
                new ConfigGetData(profile_image)
                        .execute(user.getImage());
            }
        }
        progressDialogCustom.hide();
    }

    private void onClickRequestPermision() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permision = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permision, MY_REQUEST_CODE);
        }
    }

    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(i, "Select Picture"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!checkChangeImage)
        setData();
    }
}