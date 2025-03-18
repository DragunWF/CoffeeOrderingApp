package com.example.application;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.application.data.CartItem;
import com.example.application.helpers.SessionData;
import com.example.application.helpers.Utils;

public class CheckoutView extends AppCompatActivity {
    public static final String TOTAL_PRICE = "totalPrice";
    public static final String IS_BUY_NOW = "isBuyNowView";

    private ImageView returnImageBtn;
    private TextView orderNumberText, totalCoffeeNumberText, totalPriceText;
    private Button confirmBtn;

    private boolean isBuyNowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        try {
            isBuyNowView = getIntent().getBooleanExtra(IS_BUY_NOW, false);

            bindElements();
            setData();
            setButtons();
        }
        catch (Exception err){
            err.printStackTrace();
        }
    }
    private void bindElements() {
        orderNumberText = findViewById(R.id.orderNumberText);
        totalCoffeeNumberText = findViewById(R.id.totalCoffeeNumberText);
        totalPriceText = findViewById(R.id.totalPriceText);
        returnImageBtn = findViewById(R.id.returnImageBtn);
        confirmBtn = findViewById(R.id.confirmBtn);
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        orderNumberText.setText(String.valueOf((int) Math.floor(Math.random() * 99999)));
        totalCoffeeNumberText.setText(String.valueOf(SessionData.getCart().size()));

        double totalPrice;
        if (isBuyNowView) {
            totalPrice = getIntent().getDoubleExtra(CheckoutView.TOTAL_PRICE, 0);
        } else {
            totalPrice = 0;
            for (CartItem item : SessionData.getCart()) {
                totalPrice += item.getTotalPrice();
            }
        }

        totalPriceText.setText(totalPrice + " PHP");
    }

    private void setButtons(){
        returnImageBtn.setOnClickListener(v ->{
            finish();
        });
        confirmBtn.setOnClickListener(v ->{
            Utils.longToast("Your order has been successfully confirmed", CheckoutView.this);
            finish();
        });
    }
}