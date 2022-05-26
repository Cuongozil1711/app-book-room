package com.example.finalandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.activity.authen.LoginActivity;
import com.example.finalandroid.activity.room.BookRoom;
import com.example.finalandroid.R;
import com.example.finalandroid.model.User;
import com.example.finalandroid.adapter.RecyleViewHistoryAdapter;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.model.HistoryBookRoom;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHistory extends Fragment implements RecyleViewHistoryAdapter.ItemListener{

    private User user;
    private SqliteHelper sqliteHelper;
    private List<HistoryBookRoom> bookRoomList;
    private Context context;
    private TextView mHistory, btnOrder;

    private RecyclerView recyclerView;
    private RecyleViewHistoryAdapter adapter;
    private RecyleViewHistoryAdapter.ItemListener itemListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmet_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sqliteHelper = new SqliteHelper(view.getContext());
        user = sqliteHelper.getUser();
        context = getActivity();
        mHistory = view.findViewById(R.id.mHistory);
        recyclerView = view.findViewById(R.id.recycleView);
        btnOrder = view.findViewById(R.id.btnOrder);
        itemListener = this;
        if(user == null){
            btnOrder.setVisibility(View.VISIBLE);
            btnOrder.setText("Đăng nhập ngay để nhận nhiều ưu đãi hấp dẫn");
        }
        if(user != null)
        getListHistoryBookRoom();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user == null){
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void getListHistoryBookRoom(){
        ProgressDialogCustom progressDialogCustom = new ProgressDialogCustom(context);
        progressDialogCustom.show();
        ApiService.apiService.getHistoryBook(Integer.valueOf(user.getId()), "0", user.getAccessToken()).enqueue(new Callback<List<HistoryBookRoom>>() {
            @Override
            public void onResponse(Call<List<HistoryBookRoom>> call, Response<List<HistoryBookRoom>> response) {
                progressDialogCustom.hide();
                bookRoomList = response.body();
                if(bookRoomList != null){
                    mHistory.setText("Lịch sử đặt phòng");
                    btnOrder.setVisibility(View.GONE);
                }
                else{
                    mHistory.setText("Bạn chưa có lịch sử nào");
                    btnOrder.setVisibility(View.VISIBLE);
                }

                adapter = new RecyleViewHistoryAdapter(context);
                adapter.setmList(bookRoomList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                adapter.setItemListener(itemListener);
            }

            @Override
            public void onFailure(Call<List<HistoryBookRoom>> call, Throwable t) {
                progressDialogCustom.hide();
                Toast.makeText(getActivity(), "Call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        HistoryBookRoom historyBookRoom = adapter.getItemPos(position);
        Intent intent = new Intent(context, BookRoom.class);
        intent.putExtra("room", historyBookRoom.getRoom());
        startActivity(intent);

    }
}