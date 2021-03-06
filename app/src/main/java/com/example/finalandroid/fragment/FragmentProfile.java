package com.example.finalandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalandroid.activity.authen.RegisterActivity;
import com.example.finalandroid.activity.hotel.UserHotelLove;
import com.example.finalandroid.activity.user.EditUserActivity;
import com.example.finalandroid.activity.authen.LoginActivity;
import com.example.finalandroid.R;
import com.example.finalandroid.activity.review.ReviewHotelAcitivity;
import com.example.finalandroid.model.User;
import com.example.finalandroid.dal.SqliteHelper;

public class FragmentProfile extends Fragment{

    private User user;
    private SqliteHelper sqliteHelper;
    private TextView txName, txMail, txPhone, reviewHotel, btLogin, btnRegister, hotelLove;
    private ImageButton editUser;
    private Button idSignOut;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sqliteHelper = new SqliteHelper(view.getContext());
        user = sqliteHelper.getUser();
        txName = view.findViewById(R.id.idName);
        txPhone = view.findViewById(R.id.idPhone);
        txMail = view.findViewById(R.id.idEmail);
        //idSignOut = view.findViewById(R.id.idSignOut);
        editUser = view.findViewById(R.id.btEdit);
        reviewHotel = view.findViewById(R.id.reviewHotel);
        hotelLove = view.findViewById(R.id.hotelLove);
        reviewHotel = view.findViewById(R.id.reviewHotel);
        btLogin = view.findViewById(R.id.btLogin);
        btnRegister = view.findViewById(R.id.btnRegister);
        LinearLayout pageUser = (LinearLayout)  view.findViewById(R.id.pageUser);
        LinearLayout userLogin = (LinearLayout)  view.findViewById(R.id.userLogin);
        LinearLayout noUserLogin = (LinearLayout)  view.findViewById(R.id.noUserLogin);
        LinearLayout btSignOut = (LinearLayout)  view.findViewById(R.id.btSignOut);
        if(user != null){
            txName.setText(user.getName());
            txMail.setText(user.getEmail());
            txPhone.setText(user.getPhone());
            userLogin.setVisibility(View.VISIBLE);
            noUserLogin.setVisibility(View.GONE);
        }
        else{
            pageUser.setVisibility(View.GONE);
            userLogin.setVisibility(View.GONE);
            noUserLogin.setVisibility(View.VISIBLE);
            btSignOut.setVisibility(View.GONE);
        }

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplication() , LoginActivity.class);
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplication() , RegisterActivity.class);
                startActivity(i);
            }
        });



        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplication() , EditUserActivity.class);
                startActivity(i);
            }
        });

        reviewHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewHotelOf(view);
            }
        });

        hotelLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplication() , UserHotelLove.class);
                startActivity(i);
            }
        });

        LinearLayout sigoutBt = (LinearLayout) view.findViewById(R.id.signOut);
        sigoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    public void reviewHotelOf(View view){
        Intent i = new Intent(getActivity().getApplication() , ReviewHotelAcitivity.class);
        startActivity(i);
    }

    public void signOut(){
        sqliteHelper.delete(user.getId());
        Intent i = new Intent(getActivity().getApplication() , LoginActivity.class);
        startActivity(i);
    }
}
