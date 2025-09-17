package com.nsq.lab03;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    ExtendedFloatingActionButton add_city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        String[] cities = {"Karachi", "Lahore", "Islamabad", "Pindi", "Faisalabad", "peshawar", "Multan", "Abottabad", "Edmonton","Bahawalpur", "Kashmir", "KPK", "Balochistan"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            edit_city(position);});

        add_city = findViewById(R.id.add_city);
        add_city.setOnClickListener(v -> city_input());
    }

    private void city_input() {
        EditText new_city = new EditText(this);

        new AlertDialog.Builder(this)
                .setTitle("add city")
                .setView(new_city)
                .setPositiveButton("add", (dialog, which) -> {
                    String added_city = new_city.getText().toString();
                    if (!added_city.isEmpty()) {
                        dataList.add(added_city);
                        cityAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "city needs a name", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("nah", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void edit_city(int position) {
        String city_name = dataList.get(position);
        EditText edited_city_name = new EditText(this);
        edited_city_name.setText(city_name);

        new AlertDialog.Builder(this)
            .setTitle("edit city")
            .setView(edited_city_name)
            .setPositiveButton("edit", (dialog, which) -> {
                String new_city_name = edited_city_name.getText().toString();
                if (!new_city_name.isEmpty()) {
                    dataList.set(position, new_city_name);
                    cityAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "empty name, deleted city", Toast.LENGTH_SHORT).show();
                    dataList.remove(position);
                    cityAdapter.notifyDataSetChanged();
                }
            })
            .setNeutralButton("delete", (dialog, which) -> {
                dataList.remove(position);
                cityAdapter.notifyDataSetChanged();
                Toast.makeText(this, "deleted city", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
            .show();
    }

//    private void add_new(String city) {
//        dataList.add(city);
//        cityAdapter.notifyDataSetChanged();
//    }
}
