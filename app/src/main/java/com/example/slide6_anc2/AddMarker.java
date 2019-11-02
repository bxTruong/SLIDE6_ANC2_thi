package com.example.slide6_anc2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddMarker extends AppCompatActivity {
    private EditText edtName;
    private EditText edtLongitude;
    private EditText edtLatitude;
    private RecyclerView rcMarker;
    private Button btnThem;
    private Button btnSua;
    private List<Map> mapList;
    private MapAdapter mapAdapter;

    public String name;
    public double latitute;
    public double longitute;
    private Dao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);


        rcMarker = findViewById(R.id.rcMarker);


        dao = new Dao(this);

        mapList = dao.getAllMap();
        mapAdapter = new MapAdapter(this, mapList);
        rcMarker.setAdapter(mapAdapter);

        LinearLayoutManager vertical = new LinearLayoutManager(this);
        rcMarker.setLayoutManager(vertical);

    }

    public void newMarker(View view) {
        name = edtName.getText().toString().trim();
        latitute = Double.parseDouble(edtLatitude.getText().toString().trim());
        longitute = Double.parseDouble(edtLongitude.getText().toString().trim());

        Map map = new Map(name, longitute, latitute);

        long insert = dao.inserMap(map);
        if (insert > 0) {
            mapList.add(map);
            mapAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Thêm Thành Công ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Thêm Thất Bại ", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateMarker(View view) {
        for (int i = 0; i < mapList.size(); i++) {
            Map map = mapList.get(i);
            Log.e("NAME",name);
            if (map.getName().equalsIgnoreCase(name)) {
                name = edtName.getText().toString().trim();
                latitute = Double.parseDouble(edtLatitude.getText().toString().trim());
                longitute = Double.parseDouble(edtLongitude.getText().toString().trim());
                map.setName(name);
                map.setLongitute(longitute);
                map.setLatitute(latitute);
                int update = dao.updateBook(map);
                if (update > 0) {
                    mapList.set(i, map);
                    mapAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Sửa Thành Công ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Sửa Thất Bại ", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}

