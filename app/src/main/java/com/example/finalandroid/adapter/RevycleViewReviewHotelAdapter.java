package com.example.finalandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.R;
import com.example.finalandroid.model.HistoryBookRoom;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.ReviewHotel;

import java.util.ArrayList;
import java.util.List;

public class RevycleViewReviewHotelAdapter extends RecyclerView.Adapter<RevycleViewReviewHotelAdapter.ReviewHotelHolder>{
    private List<ReviewHotel> mList;

    public RevycleViewReviewHotelAdapter() {
        mList = new ArrayList<>();
    }

    public void setmList(List<ReviewHotel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public ReviewHotel getItemPos(int position){
        return mList.get(position);
    }

    @NonNull
    @Override
    public ReviewHotelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new RevycleViewReviewHotelAdapter.ReviewHotelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHotelHolder holder, int position) {
        ReviewHotel reviewHotel = mList.get(position);
        holder.dayReview.setText(reviewHotel.getDayReview());
        holder.nameReview.setText(reviewHotel.getNameReview());
        holder.reviewStar.setText(reviewHotel.getReviewStar());
        holder.idReview.setText(reviewHotel.getIdReview());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ReviewHotelHolder extends RecyclerView.ViewHolder{

        private TextView nameReview, dayReview, reviewStar, idReview;

        public ReviewHotelHolder(@NonNull View view) {
            super(view);
            nameReview = view.findViewById(R.id.nameReview);
            dayReview = view.findViewById(R.id.dayReview);
            reviewStar = view.findViewById(R.id.reviewStar);
            idReview = view.findViewById(R.id.idReview);
        }

    }
}
