package com.flag.cinema.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.flag.cinema.models.Movie;
import com.flag.cinema.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private List<Movie> movieList;
    private ClickListener<Movie> clickListener;
    Context context;

    public RecyclerViewAdapter(List<Movie> movieList){
        this.movieList = movieList;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout,parent,false);
        return new MyViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {

        final Movie movie = movieList.get(position);
        holder.title.setText(movie.getName());
        holder.genre.setText(movie.getType());
        holder.rating.setText(movie.getRating()+" | "+movie.getLength());
        holder.image.setImageResource(movie.getImgUrl());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(movie);
            }
        });


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setOnItemClickListener(ClickListener<Movie> movieClickListener) {
        this.clickListener = movieClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView rating;
        private TextView genre;
        private ImageView image;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            genre = itemView.findViewById(R.id.genre);
            image = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.carView);
        }
    }
}
