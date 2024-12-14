package com.haitomns.phulbari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyFlowers extends AppCompatActivity {

    private RecyclerView flowersRecyclerView;
    private MyFlowerAdapter flowerAdapter;
    private List<MyFlowerModel> flowerList;
    public ImageButton allFlowersButton;
    public ImageButton waterPlantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_flowers);

        flowersRecyclerView = findViewById(R.id.recycler_view);
        flowersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        allFlowersButton = findViewById(R.id.all_flowers);
        allFlowersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        waterPlantsButton = findViewById(R.id.water_plants);
        waterPlantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyFlowers.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        });

        flowerList = new ArrayList<>();

        loadFlowerData();
    }

    private void loadFlowerData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyGarden", Context.MODE_PRIVATE);
        String flowerNames = sharedPreferences.getString("flowers", "");

        if (flowerNames != null && !flowerNames.isEmpty()) {
            String[] flowerArray = flowerNames.split(",");

            SQLiteDatabase db = SQLiteDatabase.openDatabase(getDatabasePath("flowers.db").getPath(), null, SQLiteDatabase.OPEN_READONLY);

            for (String flowerName : flowerArray) {
                Cursor cursor = db.query("flowers_data", null, "Flower = ?", new String[]{flowerName}, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    String waterRequirement = cursor.getString(cursor.getColumnIndex("Water Requirement"));
                    String sunlightRequirement = cursor.getString(cursor.getColumnIndex("Sunlight Requirement"));
                    flowerList.add(new MyFlowerModel(flowerName, waterRequirement, sunlightRequirement));
                    cursor.close();
                }
            }
            db.close();

            flowerAdapter = new MyFlowerAdapter(this, flowerList);
            flowersRecyclerView.setAdapter(flowerAdapter);
        } else {
            Toast.makeText(this, "No flowers found in your garden", Toast.LENGTH_SHORT).show();
        }
    }
}