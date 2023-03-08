package com.flag.cinema;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerBookAdapter extends RecyclerView.Adapter<RecyclerBookAdapter.MyViewHolder>{

    private List<Bookings> bookList;
    private ClickListener1<Bookings> clickListener1;
    Context context;

    RecyclerBookAdapter(List<Bookings> bookList){
        this.bookList = bookList;
    }
    @Override
    public RecyclerBookAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_adapter_layout,parent,false);
        return new RecyclerBookAdapter.MyViewHolder(view);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Bookings book = bookList.get(position);
        holder.title.setText(book.getTitle());
        holder.seats.setText(book.getDate());
        holder.screendetails.setText(book.getSeats());


      holder.image.setImageResource(R.drawable.ticket2077);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener1.onItemClick(book);
            }
        });


    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void setOnItemClickListener(ClickListener1<Bookings> bookClickListener) {
        this.clickListener1 = bookClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView screendetails;
        private TextView seats;
        private TextView title;
        private ImageView image;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            screendetails = itemView.findViewById(R.id.screendetails);
            seats = itemView.findViewById(R.id.seats);
            image = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.carView);
        }
    }
}
interface ClickListener1<T> {
    void onItemClick(T data);
}