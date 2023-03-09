package com.flag.cinema.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.flag.cinema.R;

import java.util.ArrayList;
import java.util.List;



public class NumberedAdapter extends RecyclerView.Adapter<NumberedAdapter.ViewHolder> {
    private final List<String> labels;
    public static List<String> seats;
    public static List<String> seatindex =new ArrayList<>();
    public static List<String> bookedSeats = new ArrayList<>();
    public NumberedAdapter(int count) {        labels = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            labels.add(String.valueOf(i));
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        for(int i=0;i<bookedSeats.size();i++)
        {
            if(position==(Integer.parseInt(bookedSeats.get(i).trim()))){
                holder.textView.setBackgroundResource(R.drawable.seat_booked);
            }
        }

        String row_name="";
        if(position<=5)
        {
            row_name ="A";
        }
        else if(position>5 && position<=11)
        {
            row_name ="B";
        }
        else if(position>11 && position<=17)
        {
            row_name ="C";
        }
        else if(position>17 && position<=23)
        {
            row_name ="D";
        }
        else if(position>23 && position<=29)
        {
            row_name ="E";
        }
        else if(position>29 && position<=35)
        {
            row_name ="F";
        }
        else if(position>35 && position<=41)
        {
            row_name ="G";
        }
        else if(position>41 && position<=47)
        {
            row_name ="H";
        }
        else if(position>47 && position<=53)
        {
            row_name ="I";
        }

        final String label  = row_name+""+ (Integer.parseInt(labels.get(position))+1);
        holder.textView.setText("");
        final String indexval = labels.get(position);

        //handling item click event
        holder.textView.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;

            @Override
            public void onClick(View v) {
                if(bookedSeats.contains(indexval.trim())){
                    holder.textView.setBackgroundResource(R.drawable.seat_booked);
                    Toast.makeText(holder.textView.getContext(), "Пожалуйста, выберите другое место", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(stateChanged) {
                        // reset background to default;
                        holder.textView.setBackgroundResource(R.drawable.seat_normal);
                        seats.remove(label.trim());
                        seatindex.remove(indexval.trim());
                    } else {
                        holder.textView.setBackgroundResource(R.drawable.seat_selected);
                        seats.add(label.trim());
                        seatindex.add(indexval.trim());
                    }
                    stateChanged = !stateChanged;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return labels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }


    }
}