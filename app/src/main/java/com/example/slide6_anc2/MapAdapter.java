package com.example.slide6_anc2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MapAdapter extends RecyclerView.Adapter<MapHolder> {
    private Context context;
    private List<Map> mapList;
    private AlertDialog alertDialog;
    private Dao dao;

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

        holder.tvName.setText("Tên: "+name);
        holder.tvLatitute.setText("Kinh Độ: "+latitute);
        holder.tvLongitute.setText("Vĩ Độ: "+longitute);

        dao = new Dao(context);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("name", map.getName());
                intent.putExtra("latitute", map.getLatitute());
                intent.putExtra("longitute", map.getLongitute());
                context.startActivity(intent);
            }
        });

        holder.btnSua1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialog = LayoutInflater.from(context).inflate(R.layout.themdialog, null);
                builder.setTitle("Sửa Địa Điểm");
                builder.setView(dialog);

                final EditText edtName;
                final EditText edtLongitude;
                final EditText edtLatitude;
                Button btnThem;
                Button btnSua;

                edtName = dialog.findViewById(R.id.edtName);
                edtLongitude = dialog.findViewById(R.id.edtLongitude);
                edtLatitude = dialog.findViewById(R.id.edtLatitude);
                btnThem = dialog.findViewById(R.id.btnThem);
                btnSua = dialog.findViewById(R.id.btnSua);

                edtName.setText(name);
                edtLatitude.setText(latitute + "");
                edtLongitude.setText(longitute + "");

                btnThem.setEnabled(false);
                btnSua.setEnabled(true);

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = edtName.getText().toString().trim();
                        latitute = Double.parseDouble(edtLatitude.getText().toString().trim());
                        longitute = Double.parseDouble(edtLongitude.getText().toString().trim());
                        map.setName(name);
                        map.setLongitute(longitute);
                        map.setLatitute(latitute);
                        int update = dao.updateBook(map);
                        if (update > 0) {
                            mapList.set(position, map);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Sửa Thành Công ", Toast.LENGTH_SHORT).show();
                            alertDialog.cancel();
                        } else {
                            Toast.makeText(context, "Sửa Thất Bại ", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

                builder.create();
                alertDialog = builder.show();
            }
        });

        holder.btnXoa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Dữ Liệu Này");

                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Xóa Thát Bại", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        long delete = dao.deleteBill(map.getId());

                        if (delete > 0) {
                            mapList.remove(map);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa Thành Công ", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Xóa Thất Bại ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
