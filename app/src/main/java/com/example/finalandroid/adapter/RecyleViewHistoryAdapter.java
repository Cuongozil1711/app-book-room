package com.example.finalandroid.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.R;
import com.example.finalandroid.activity.review.ReviewHotelAcitivity;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ConfigGetData;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.model.HistoryBookRoom;
import com.example.finalandroid.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyleViewHistoryAdapter extends RecyclerView.Adapter<RecyleViewHistoryAdapter.HistoryViewHolder>{
    private List<HistoryBookRoom> mList;
    private RecyleViewHistoryAdapter.ItemListener itemListener;
    private Context context;

    public RecyleViewHistoryAdapter(Context context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    public void setItemListener(RecyleViewHistoryAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setmList(List<HistoryBookRoom> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public HistoryBookRoom getItemPos(int position){
        return mList.get(position);
    }

    @NonNull
    @Override
    public RecyleViewHistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new RecyleViewHistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyleViewHistoryAdapter.HistoryViewHolder holder, int position) {
        HistoryBookRoom historyBookRoom = mList.get(position);
        new ConfigGetData(holder.tvImage)
                .execute(historyBookRoom.getHotel().getImage());
        holder.idNameHotel.setText(historyBookRoom.getHotel().getName());
        holder.tvDis.setText(historyBookRoom.getRoom().getDescribe());
        holder.tvPrice.setText(historyBookRoom.getRoom().getPrice());
        holder.dateTime.setText("Nhập phòng: " + historyBookRoom.getUserRoom().getDateFrom());
        holder.dateTimeTo.setText("Trả phòng: " + historyBookRoom.getUserRoom().getDateTo());

        SqliteHelper sql = new SqliteHelper(context);
        User us = sql.getUser();
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            ProgressDialogCustom progressDialogCustom1 = new ProgressDialogCustom(context);
            @Override
            public void onClick(View view) {
                progressDialogCustom1.show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("ZoZoy thông báo");
                builder.setMessage("Bạn chỉ được hủy phòng sau 1 ngày đặt phòng");
                builder.setIcon(R.drawable.ic_baseline_done_24);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.deleteBookRoom(us.getAccessToken(), historyBookRoom.getUserRoom().getIdUserRoom()).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if(response.body() != null){
                                    progressDialogCustom1.hide();
                                    mList.remove(historyBookRoom);
                                    notifyDataSetChanged();
                                }
                                else{
                                    progressDialogCustom1.hide();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                progressDialogCustom1.hide();
                            }
                        });
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mList != null)
        return mList.size();
        return 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView tvImage;
        private TextView idNameHotel, tvDis, dateTime, dateTimeTo, tvPrice;
        private TextView btDelete;

        public HistoryViewHolder(@NonNull View view) {
            super(view);
            tvImage = view.findViewById(R.id.tvImage);
            idNameHotel = view.findViewById(R.id.idNameHotel);
            tvDis = view.findViewById(R.id.tvDis);
            dateTime = view.findViewById(R.id.dateTime);
            dateTimeTo = view.findViewById(R.id.dateTimeTo);
            tvPrice = view.findViewById(R.id.tvPrice);
            btDelete = view.findViewById(R.id.btDelete);
//            btnRegister.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    HistoryBookRoom historyBookRoom = getItemPos(getAdapterPosition());
//                    Intent intent = new Intent(context, BookRoom.class);
//                    intent.putExtra("room", historyBookRoom.getRoom());
//                    startActivity(intent);
//                }
//            });
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onItemClick(View view, int position);
    }

}
