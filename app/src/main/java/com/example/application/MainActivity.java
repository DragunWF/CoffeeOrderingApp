package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.adapters.CoffeeAdapter;
import com.example.application.data.Coffee;
import com.example.application.helpers.DatabaseHelper;
import com.example.application.helpers.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView coffeeRecycler;
    private CoffeeAdapter coffeeAdapter;
    private RecyclerView.LayoutManager coffeeLayoutManager;

    private ImageView cartImageBtn;
    private SearchView searchBar;
    private Spinner categorySpinner;

    @Override
    protected void onResume() {
        super.onResume();
        coffeeAdapter.updateDataSet();
    }

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

        try {
            DatabaseHelper.initialize(this);
            DatabaseHelper.clear();
            DatabaseHelper.addDummyData();

            bindElements();
            setButtons();
            setRecycler();
            setSpinner();
            setSearch();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        coffeeRecycler = findViewById(R.id.coffeeRecycler);
        searchBar = findViewById(R.id.searchBar);
        categorySpinner = findViewById(R.id.categorySpinner);
        cartImageBtn = findViewById(R.id.cartImageBtn);
    }

    private void setButtons() {
        cartImageBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartView.class));
        });
    }

    private void setRecycler() {
        coffeeRecycler.setHasFixedSize(false);

        coffeeAdapter = new CoffeeAdapter(DatabaseHelper.getCoffeeBank().getAll(), this);
        coffeeRecycler.setAdapter(coffeeAdapter);

        coffeeLayoutManager = new LinearLayoutManager(this);
        coffeeRecycler.setLayoutManager(coffeeLayoutManager);
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.array_categories,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = adapter.getItem(position).toString();

                if (selectedItem.equals("Any")) {
                    coffeeAdapter.updateDataSet();
                    return;
                }

                List<Coffee> results = new ArrayList<>();
                for (Coffee coffee : DatabaseHelper.getCoffeeBank().getAll()) {
                    if (coffee.getCategory().equals(selectedItem)) {
                        results.add(coffee);
                    }
                }

                coffeeAdapter.updateDataSet(results);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSearch() {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                update(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                update(newText);
                return false;
            }

            public void update(String query) {
                List<Coffee> data = DatabaseHelper.getCoffeeBank().getAll();
                List<Coffee> results = new ArrayList<>();

                query = query.toLowerCase();
                for (Coffee coffee : data) {
                    String name = coffee.getName().toLowerCase();
                    if (name.contains(query)) {
                        results.add(coffee);
                    }
                }

                coffeeAdapter.updateDataSet(results);
            }
        });
    }
}