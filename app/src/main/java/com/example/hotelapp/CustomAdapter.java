package com.example.hotelapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {
    Context context;
    ArrayList id_reservation,id_kunde,id_zimmer,gesamt;
    ArrayList  datein,dateout;


     CustomAdapter(Context context, ArrayList<Integer> id_reservation,ArrayList<Integer> id_kunde,ArrayList<Integer> id_zimmer, ArrayList<String> datein,ArrayList<String> dateout,ArrayList<Integer> gesamt) {
        this.context=context;
        this.id_reservation=id_reservation;
        this.id_kunde=id_kunde;
        this.id_zimmer=id_zimmer;
        this.datein=datein;
        this.dateout=dateout;
        this.gesamt=gesamt;
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
         try {
             holder.id_Reservation.setText(String.valueOf(id_reservation.get(position)));
             holder.id_kunde.setText(String.valueOf(id_kunde.get(position)));
             holder.id_zimmer.setText(String.valueOf(id_zimmer.get(position)));
             holder.datein.setText(String.valueOf(datein.get(position)));
             holder.dateout.setText(String.valueOf(dateout.get(position)));
             holder.gesamt.setText("Price :"+String.valueOf(gesamt.get(position)));
         }catch (NullPointerException e){
             Toast.makeText(context, " Null ", Toast.LENGTH_SHORT).show();
         }





    }

    @Override
    public int getItemCount() {
        return id_reservation.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

         TextView id_Reservation,id_kunde,id_zimmer,datein,dateout,gesamt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_Reservation = itemView.findViewById(R.id.id_reservation);
            id_kunde = itemView.findViewById(R.id.vorname);
            id_zimmer = itemView.findViewById(R.id.nachname);
            datein = itemView.findViewById(R.id.datein);
            dateout = itemView.findViewById(R.id.dateout);
            gesamt = itemView.findViewById(R.id.gesamt);

        }
    }
}
