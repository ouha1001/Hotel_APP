package com.example.hotelapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    Activity activity;
    ArrayList<Integer> id_reservation, id_kunde, id_zimmer, gesamt;
    ArrayList<String> datein, dateout;


    CustomAdapter(Activity activity, Context context, ArrayList<Integer> id_reservation, ArrayList<Integer> id_kunde, ArrayList<Integer> id_zimmer, ArrayList<String> datein, ArrayList<String> dateout, ArrayList<Integer> gesamt) {
        this.activity = activity;
        this.context = context;
        this.id_reservation = id_reservation;
        this.id_kunde = id_kunde;
        this.id_zimmer = id_zimmer;
        this.datein = datein;
        this.dateout = dateout;
        this.gesamt = gesamt;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.fragment,parent,false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.id_Reservation.setText(String.valueOf(id_reservation.get(position)));
        holder.id_kunde.setText(String.valueOf(id_kunde.get(position)));
        holder.id_zimmer.setText(String.valueOf(id_zimmer.get(position)));
        holder.datein.setText(String.valueOf(datein.get(position)));
        holder.dateout.setText(String.valueOf(dateout.get(position)));
        holder.gesamt.setText(String.format("Price :%s", gesamt.get(position)));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_UpdateReservation.class);
                intent.putExtra("Id_Reservation", String.valueOf(id_reservation.get(position)));
                intent.putExtra("Id_Kunde", String.valueOf(id_kunde.get(position)));
                intent.putExtra("Id_Zimmer", String.valueOf(id_zimmer.get(position)));
                intent.putExtra("check_In", String.valueOf(datein.get(position)));
                intent.putExtra("check_Out", String.valueOf(dateout.get(position)));
                intent.putExtra("gesamt", String.valueOf(gesamt.get(position)));
                activity.startActivityForResult(intent, 1);
                 }
             });
    }

    @Override
    public int getItemCount() {
        return id_reservation.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_Reservation, id_kunde, id_zimmer, datein, dateout, gesamt;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_Reservation = itemView.findViewById(R.id.id_reservation);
            id_kunde = itemView.findViewById(R.id.vorname);
            id_zimmer = itemView.findViewById(R.id.nachname);
            datein = itemView.findViewById(R.id.datein);
            dateout = itemView.findViewById(R.id.dateout);
            gesamt = itemView.findViewById(R.id.gesamt);
            linearLayout=itemView.findViewById(R.id.linearlayout);

        }
    }
}
