package com.example.finalandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.BookRoom;
import com.example.finalandroid.R;
import com.example.finalandroid.custom.ConfigGetData;
import com.example.finalandroid.model.HistoryBookRoom;
import com.example.finalandroid.model.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        holder.dateTimeTo.setText("Trả phòng" + historyBookRoom.getUserRoom().getDateTo());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView tvImage;
        private TextView idNameHotel, tvDis, dateTime, dateTimeTo, tvPrice;
        private Button btnRegister;

        public HistoryViewHolder(@NonNull View view) {
            super(view);
            tvImage = view.findViewById(R.id.tvImage);
            idNameHotel = view.findViewById(R.id.idNameHotel);
            tvDis = view.findViewById(R.id.tvDis);
            dateTime = view.findViewById(R.id.dateTime);
            dateTimeTo = view.findViewById(R.id.dateTimeTo);
            tvPrice = view.findViewById(R.id.tvPrice);
            btnRegister = view.findViewById(R.id.btnRegister);
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
