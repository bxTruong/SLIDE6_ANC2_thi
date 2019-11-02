package com.example.slide6_anc2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog alertDialog;
    private EditText edtName;
    private EditText edtLongitude;
    private EditText edtLatitude;
    private Button btnThem;
    private Button btnSua;
    private List<Map> mapList;
    private TextView tvSoluong;

    public String name;
    public double latitute;
    public double longitute;
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSoluong=findViewById(R.id.tvSoLuong);
        dao =new Dao(this);
        mapList=dao.getAllMap();

    }

    public void them(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialog = getLayoutInflater().from(this).inflate(R.layout.themdialog, null);
        builder.setTitle("Thêm Địa Điểm");
        builder.setView(dialog);

        edtName = dialog.findViewById(R.id.edtName);
        edtLongitude = dialog.findViewById(R.id.edtLongitude);
        edtLatitude = dialog.findViewById(R.id.edtLatitude);
        btnThem = dialog.findViewById(R.id.btnThem);
        btnSua = dialog.findViewById(R.id.btnSua);

        dao = new Dao(this);

        mapList = dao.getAllMap();

        btnThem.setEnabled(true);
        btnSua.setEnabled(false);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edtName.getText().toString().trim();
                latitute = Double.parseDouble(edtLatitude.getText().toString().trim());
                longitute = Double.parseDouble(edtLongitude.getText().toString().trim());

                Map map = new Map(name, longitute, latitute);

                long insert = dao.inserMap(map);
                if (insert > 0) {
                    mapList.add(map);
                    Intent intent=new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Thêm Thành Công ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Thêm Thất Bại ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.create();
        alertDialog = builder.show();
    }

    public void danhsach(View view) {
        Intent intent = new Intent(this, AddMarker.class);
        startActivity(intent);
    }


}
