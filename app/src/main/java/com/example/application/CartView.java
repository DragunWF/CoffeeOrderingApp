package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.adapters.CartAdapter;
import com.example.application.helpers.DatabaseHelper;
import com.example.application.helpers.Utils;

public class CartView extends AppCompatActivity {
    private RecyclerView cartRecycler;
    private CartAdapter cartAdapter;
    private RecyclerView.LayoutManager cartLayoutManager;

    private ImageView backImageBtn;
    private Button checkouBtn;

    @Override
    protected void onResume() {
        super.onResume();
        cartAdapter.updateDataSet();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);
            DatabaseHelper.addDummyData();

            bindElements();
            setButtons();
            setRecycler();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        cartRecycler = findViewById(R.id.cartRecycler);
        backImageBtn = findViewById(R.id.backImageBtn);
        checkouBtn = findViewById(R.id.checkoutBtn);
    }

    private void setButtons() {
        backImageBtn.setOnClickListener(v -> {
            finish();
        });
        checkouBtn.setOnClickListener(v -> {
            startActivity(new Intent(CartView.this, CheckoutView.class));
        });
    }

    private void setRecycler() {
        cartRecycler.setHasFixedSize(false);

        cartAdapter = new CartAdapter(DatabaseHelper.getCartBank().getAll(), this);
        cartRecycler.setAdapter(cartAdapter);

        cartLayoutManager = new LinearLayoutManager(this);
        cartRecycler.setLayoutManager(cartLayoutManager);
    }
}