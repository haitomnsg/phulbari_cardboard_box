package com.haitomns.phulbari.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haitomns.phulbari.Flower;
import com.haitomns.phulbari.FlowerAdapter;
import com.haitomns.phulbari.MyFlowers;
import com.haitomns.phulbari.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<Flower> flowerList = new ArrayList<>();
    private List<Flower> filteredFlowerList = new ArrayList<>();
    private FlowerAdapter flowerAdapter;
    private DatabaseReference sensorDataReference;
    ProgressBar moistureProgressBar;
    ImageButton myflowerButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerViewFlowers;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        flowerAdapter = new FlowerAdapter(filteredFlowerList);
        recyclerView.setAdapter(flowerAdapter);

        // Load flowers data
        loadFlowerData();

        // Set up search box functionality
        setupSearchBox();

        moistureProgressBar = binding.moistureProgressBar;

        //Firebase Setup
        // Initialize Firebase reference for soil moisture
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://phulbari-haitomns-default-rtdb.asia-southeast1.firebasedatabase.app/");
        sensorDataReference = firebaseDatabase.getReference("sensorData");
        sensorDataReference.keepSynced(true);

        // Fetch and display soil moisture data
        TextView moistureLevelTextView = binding.moistureLevel; // Reference to the TextView
        fetchSensorData(moistureLevelTextView);

        myflowerButton = binding.flower;
        myflowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(getContext(), MyFlowers.class);
                 startActivity(intent);
            }
        });

        return root;
    }

    private void loadFlowerData() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(getContext().getDatabasePath("flowers.db").getPath(), null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = null;

        try {
            cursor = db.query("flowers_data", new String[]{"Flower"}, null, null, null, null, null);

            int columnIndex = cursor.getColumnIndex("Flower");
            if (columnIndex == -1) {
                throw new IllegalStateException("Column 'Flower' not found in the database.");
            }

            if (cursor.moveToFirst()) {
                do {
                    String flowerName = cursor.getString(columnIndex);
                    String imageName = flowerName + ".jpg";
                    flowerList.add(new Flower(flowerName, imageName));
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        // Initially show all flowers
        filteredFlowerList.addAll(flowerList);
        flowerAdapter.notifyDataSetChanged();
    }

    private void setupSearchBox() {
        EditText searchBox = binding.searchBox;
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterFlowerList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void fetchSensorData(TextView sensorTextView) {
        sensorDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot lastRecord = null;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        lastRecord = snapshot; // Keep updating to get the last record
                    }

                    if (lastRecord != null) {
                        int soilMoisture = lastRecord.child("soilMoisture").getValue(Integer.class);

                        // Convert soil moisture to percentage
                        int moisturePercentage = (int) ((soilMoisture / 1023.0) * 100);

                        // Update the TextView with percentage value
                        sensorTextView.setText(moisturePercentage + "%");

                        moistureProgressBar.setProgress(moisturePercentage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                sensorTextView.setText("Failed to load data: " + databaseError.getMessage());
            }
        });
    }


    private void filterFlowerList(String query) {
        // Clear the list before adding new results
        filteredFlowerList.clear();

        if (query.isEmpty()) {
            // Add all flowers when search query is empty
            filteredFlowerList.clear();
            filteredFlowerList.addAll(flowerList);
        } else {
            filteredFlowerList.clear();
            // Filter flowers based on the search query
            for (Flower flower : flowerList) {
                if (flower.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredFlowerList.add(flower);
                }
            }
        }

        // Notify adapter about data changes
        flowerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}