package com.example.hotelapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    Activity activity;


    ArrayList<Integer> id_reservation, id_kunde, id_zimmer, gesamt;
    ArrayList<String> datein, dateout, vorname, nachname;
    Database db;
    Map<Integer, String> Kunde;


    public void storeAllClients() {
        Cursor c = db.readAllData();
        if (c.getCount() == 0) {
            Toast.makeText(context, "Keinen Kunden", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()) {
                Kunde.put(Integer.parseInt(c.getString(0)), c.getString(1) + " " + c.getString(2));
            }
        }

    }

    CustomAdapter(Activity activity,Context context, ArrayList<Integer> id_reservation, ArrayList<Integer> id_kunde, ArrayList<Integer> id_zimmer, ArrayList<String> datein, ArrayList<String> dateout, ArrayList<Integer> gesamt, ArrayList<String> vorname, ArrayList<String> nachname) {

        this.context = context;
        this.id_reservation = id_reservation;
        this.id_kunde = id_kunde;
        this.id_zimmer = id_zimmer;
        this.datein = datein;
        this.dateout = dateout;
        this.gesamt = gesamt;

        this.vorname = vorname;
        this.nachname = nachname;
        this.activity=activity;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        db = new Database(context);

        View view = layoutInflater.inflate(R.layout.fragment, parent, false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.id_Reservation.setText(String.valueOf(id_reservation.get(position)));
        holder.id_kunde.setText(vorname.get(position) + "  " + nachname.get(position));
        holder.id_zimmer.setText(String.valueOf(id_zimmer.get(position)));
        holder.datein.setText(String.valueOf(datein.get(position)));
        holder.dateout.setText(String.valueOf(dateout.get(position)));
        holder.gesamt.setText("Price :" + String.valueOf(gesamt.get(position)));


        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder aleBuilder;
            @Override
            public void onClick(View v) {
                String pos = String.valueOf(id_reservation.get(position));
                Toast.makeText(context, "" + String.valueOf(id_reservation.get(position)), Toast.LENGTH_SHORT).show();

                if (pos.isEmpty()) {
                    Toast.makeText(context, "Item Empty" + pos, Toast.LENGTH_SHORT).show();

                } else {
                    activity=new Activity();
                        aleBuilder=new AlertDialog.Builder(context);
                        aleBuilder.setTitle(" Item : "+id_reservation.get(position));
                        aleBuilder.setMessage("Are you sure ?");
                        aleBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.DeleteReservation(pos);
                                Toast.makeText(context, "deleted item " + String.valueOf(id_reservation.get(position)), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context ,CustomAdapter.MyViewHolder.class);
                                activity.startActivityForResult(intent,1);
                                activity.finish();

                            }
                        });
                        aleBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        aleBuilder.show();
                }
            }
        });

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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_Reservation, id_kunde, id_zimmer, datein, dateout, gesamt, btndelete;
        LinearLayout linearLayout;
        RecycleViewInterface recycleViewInterface;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_Reservation = itemView.findViewById(R.id.id_reservation);
            id_kunde = itemView.findViewById(R.id.vorname);
            id_zimmer = itemView.findViewById(R.id.nachname);
            datein = itemView.findViewById(R.id.datein);
            dateout = itemView.findViewById(R.id.dateout);
            gesamt = itemView.findViewById(R.id.gesamt);
            btndelete = itemView.findViewById(R.id.deletebtn);
            linearLayout = itemView.findViewById(R.id.linearlayout);
            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recycleViewInterface.Oussama(pos);
                            Toast.makeText(context, "NO " + pos, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

        }
    }
}
