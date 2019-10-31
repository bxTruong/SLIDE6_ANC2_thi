package com.example.slide6_anc2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MapAdapter extends RecyclerView.Adapter<MapHolder> {
    private Context context;
    private List<Map> mapList;

    public static String name;
    public static double latitute;
    public static double longitute;
    public static int id;

    public MapAdapter() {
    }

    public MapAdapter(Context context, List<Map> mapList) {
        this.context = context;
        this.mapList = mapList;
    }


    @NonNull
    @Override
    public MapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.map, parent, false);
        MapHolder map = new MapHolder(view);
        return map;
    }

    @Override
    public void onBindViewHolder(@NonNull MapHolder holder, final int position) {

        final Map map = mapList.get(position);
        name = map.getName();
        latitute = map.getLatitute();
        longitute = map.getLongitute();

        holder.tvName.setText(name);
        holder.tvLatitute.setText(latitute + "");
        holder.tvLongitute.setText(longitute + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=position;
                Log.e("POSTION2", id + "");
                name = map.getName();
                latitute = map.getLatitute();
                longitute = map.getLongitute();
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }
}
