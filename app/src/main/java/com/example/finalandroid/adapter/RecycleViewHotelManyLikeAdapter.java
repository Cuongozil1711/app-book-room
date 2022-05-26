package com.example.finalandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.R;
import com.example.finalandroid.custom.ConfigGetData;
import com.example.finalandroid.model.Hotel;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewHotelManyLikeAdapter extends RecyclerView.Adapter<RecycleViewHotelManyLikeAdapter.HotelViewHolder>{
    private List<Hotel> mList;
    private RecycleViewHotelManyLikeAdapter.ItemListener itemListener;

    public RecycleViewHotelManyLikeAdapter() {
        mList = new ArrayList<>();
    }

    public void setItemListener(RecycleViewHotelManyLikeAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setmList(List<Hotel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public Hotel getItemPos(int position){
        return mList.get(position);
    }

    @NonNull
    @Override
    public RecycleViewHotelManyLikeAdapter.HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new RecycleViewHotelManyLikeAdapter.HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHotelManyLikeAdapter.HotelViewHolder holder, int position) {
        Hotel hotel = mList.get(position);
        new ConfigGetData(holder.img)
                .execute(hotel.getImage());
//        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(150,140);
//        holder.img.setLayoutParams(parms);
        holder.tvtitle.setText(hotel.getName());
        holder.tvPrice.setText("150.000đ");
        holder.tvStar.setText(hotel.getStar());
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        else return 0;
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView img;
        private TextView tvPrice, tvtitle, tvStar;

        public HotelViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.tvImage);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvtitle = view.findViewById(R.id.tvTitle);
            tvStar = view.findViewById(R.id.idStar);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onItemClickHotelLike(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onItemClickHotelLike(View view, int position);
    }
}
