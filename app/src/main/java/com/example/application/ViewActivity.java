package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.application.data.Coffee;
import com.example.application.helpers.DatabaseHelper;
import com.example.application.helpers.Utils;
import com.example.application.services.CartService;

public class ViewActivity extends AppCompatActivity {
    public static final String VIEWED_COFFEE_ID = "viewedCoffeeId";

    private TextView nameText, categoryText, descriptionText, priceText, quantityText;
    private ImageView addQuantityImageBtn, minusQuantityImageBtn, returnImageBtn;
    private Button buyBtn, addToCartBtn;

    private int currentQuantity = 1;
    private Coffee viewedCoffee = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            int viewedCoffeeId = getIntent().getIntExtra(VIEWED_COFFEE_ID, -1);
            viewedCoffee = DatabaseHelper.getCoffeeBank().get(viewedCoffeeId);
            System.out.println(viewedCoffeeId + " " + viewedCoffee.getId());

            bindElements();
            setData();
            setButtons();
        }
        catch(Exception err){
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements(){
        nameText = findViewById(R.id.nameText);
        categoryText = findViewById(R.id.categoryText);
        descriptionText = findViewById(R.id.descriptionText);
        priceText = findViewById(R.id.priceText);
        quantityText = findViewById(R.id.quantityText);

        addQuantityImageBtn = findViewById(R.id.addImageBtn);
        minusQuantityImageBtn = findViewById(R.id.minusImageBtn);
        buyBtn = findViewById(R.id.buyBtn);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        returnImageBtn = findViewById(R.id.returnImageBtn);
    }

    private void setData() {
        nameText.setText(viewedCoffee.getName());
        categoryText.setText(viewedCoffee.getCategory());
        descriptionText.setText(viewedCoffee.getDescription());
        priceText.setText(viewedCoffee.getPrice() + " PHP");
        quantityText.setText(String.valueOf(currentQuantity));
    }

    private void setButtons(){
        addQuantityImageBtn.setOnClickListener(v ->{
            currentQuantity++;
            quantityText.setText(String.valueOf(currentQuantity));
        });
        minusQuantityImageBtn.setOnClickListener(v ->{
            currentQuantity--;
            if (currentQuantity <= 0) {
                currentQuantity = 1;
            }
            quantityText.setText(String.valueOf(currentQuantity));
        });
        buyBtn.setOnClickListener(v ->{
            Intent intent = new Intent(ViewActivity.this, CheckoutView.class);
            intent.putExtra(CheckoutView.IS_BUY_NOW, true);
            intent.putExtra(CheckoutView.TOTAL_PRICE, viewedCoffee.getPrice() * currentQuantity);
            startActivity(intent);
        });
        addToCartBtn.setOnClickListener(v ->{
            if (!CartService.isCoffeeInCart(viewedCoffee.getId())) {
                CartService.add(viewedCoffee, currentQuantity);
                Utils.longToast("Item has been added to cart!", ViewActivity.this);
                finish();
            } else {
                Utils.longToast("Item is already in cart!", ViewActivity.this);
            }
        });
        returnImageBtn.setOnClickListener(v ->{
            finish();
        });
    }
}
