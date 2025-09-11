package com.example.listycity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addBtn;
    Button deleteBtn;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        addBtn   = findViewById(R.id.add_city_btn);
        deleteBtn= findViewById(R.id.delete_city_btn);

        String []cities = {"Edmonton","Vancouver","Moscow","Sydney","Berlin","Vienna","Tokyo","Beijing","Osaka","New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Toast.makeText(this, "Selected: " + dataList.get(position), Toast.LENGTH_SHORT).show();
        });

        addBtn.setOnClickListener(v -> showAddCityDialog());

        deleteBtn.setOnClickListener(v -> deleteSelectedCity());
    }

    private void showAddCityDialog() {
        final EditText input = new EditText(this);
        input.setHint("City name");
        input.setSingleLine();
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});

        new AlertDialog.Builder(this)
                .setTitle("Add City")
                .setView(input)
                .setPositiveButton("CONFIRM", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dataList.add(name);
                    cityAdapter.notifyDataSetChanged();
                    selectedPosition = -1;
                })
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteSelectedCity() {
        if (selectedPosition < 0 || selectedPosition >= dataList.size()) {
            Toast.makeText(this, "Tap a city first, then press DELETE CITY", Toast.LENGTH_SHORT).show();
            return;
        }
        String removed = dataList.remove(selectedPosition);
        cityAdapter.notifyDataSetChanged();
        selectedPosition = -1;
        Toast.makeText(this, "Removed: " + removed, Toast.LENGTH_SHORT).show();
    }
}