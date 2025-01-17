package com.example.listycity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    EditText cityToAdd;
    String cityToDel = "";

    View lastSelected;

    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

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

        String[] cities = {"Edmonton", "Paris", "London", "Calgary", "New York", "Dubai", "Bangkok", "Beijing", "Sydney", "Seattle"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Lab participation
        Button addButton = findViewById(R.id.add_city_button);
        Button deleteButton = findViewById(R.id.delete_city_button);
        Button confirmCity = findViewById(R.id.confirm_button);
        TextView cityInputLine = findViewById(R.id.input_text_city);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityInputLine.setVisibility(View.VISIBLE);
                confirmCity.setVisibility(View.VISIBLE);
                view.setVisibility(View.INVISIBLE);
                deleteButton.setVisibility(View.INVISIBLE);
            }
        });

        confirmCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityToAdd = findViewById(R.id.input_text_city);
                String newCity = cityToAdd.getText().toString();
                if (!(newCity.isEmpty())) {
                    dataList.add(newCity);
                    cityAdapter.notifyDataSetChanged();
                }
                cityInputLine.setVisibility(View.INVISIBLE);
                view.setVisibility(View.INVISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.VISIBLE);
            }
        });


        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (cityToDel.equals(cityAdapter.getItem(i))) {
                    cityToDel = ""; // unselects
                    lastSelected = null;
                    view.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    cityToDel = cityAdapter.getItem(i);  // selects
                    if (lastSelected != null) {
                        lastSelected.setBackgroundColor(Color.TRANSPARENT);
                    }
                    lastSelected = view;
                    view.setBackgroundColor(getResources().getColor(R.color.selected_content,null));
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(cityToDel.isEmpty())) {
                    lastSelected.setBackgroundColor(Color.TRANSPARENT);
                    dataList.remove(cityToDel);
                    cityAdapter.remove(cityToDel);
                    cityAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}