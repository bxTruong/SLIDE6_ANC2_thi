package com.example.slide6_anc2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MapHolder extends RecyclerView.ViewHolder {
    public TextView tvName;
    public TextView tvLatitute;
    public TextView tvLongitute;


    public MapHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvLatitute = itemView.findViewById(R.id.tvLatitute);
        tvLongitute = itemView.findViewById(R.id.tvLongitute);
    }
}
