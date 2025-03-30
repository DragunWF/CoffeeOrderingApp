package com.example.application;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.application.data.CartItem;
import com.example.application.helpers.DatabaseHelper;
import com.example.application.helpers.Utils;
import com.example.application.services.CartService;

import java.util.List;

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
        List<CartItem> cartItemList = DatabaseHelper.getCartBank().getAll();

        orderNumberText.setText(String.valueOf((int) Math.floor(Math.random() * 99999)));
        totalCoffeeNumberText.setText(String.valueOf(cartItemList.size()));

        double totalPrice;
        if (isBuyNowView) {
            totalPrice = getIntent().getDoubleExtra(CheckoutView.TOTAL_PRICE, 0);
        } else {
            totalPrice = 0;
            for (CartItem item : cartItemList) {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutView.this);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Utils.longToast("Your order has been successfully confirmed", CheckoutView.this);
                    CartService.clear();
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancels the dialog.
                }
            });
            builder.setTitle("Confirmation Dialog");
            builder.setMessage("Are you sure you want to confirm this order? This will clear your current cart!");

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}