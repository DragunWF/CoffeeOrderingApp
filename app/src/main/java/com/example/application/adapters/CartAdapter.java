package com.example.application.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.data.CartItem;
import com.example.application.data.Coffee;
import com.example.application.helpers.DatabaseHelper;
import com.example.application.services.CartService;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> localDataSet;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText, priceText, quantityText;
        private final ImageView addBtn, subtractBtn;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            nameText = view.findViewById(R.id.nameText);
            priceText = view.findViewById(R.id.priceText);
            quantityText = view.findViewById(R.id.quantityText);

            addBtn = view.findViewById(R.id.addBtn);
            subtractBtn = view.findViewById(R.id.subtractBtn);
        }

        public TextView getNameText() {
            return nameText;
        }

        public TextView getPriceText() {
            return priceText;
        }

        public TextView getQuantityText() {
            return quantityText;
        }

        public ImageView getAddBtn() {
            return addBtn;
        }

        public ImageView getSubtractBtn() {
            return subtractBtn;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public CartAdapter(List<CartItem> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_cart_recycler, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        // viewHolder.getTextView().setText(localDataSet[position]);
        CartItem cartItem = localDataSet.get(position);
        Coffee coffee = cartItem.getCoffee();

        viewHolder.getNameText().setText(coffee.getName());
        viewHolder.getPriceText().setText(coffee.getPrice() + " PHP");
        viewHolder.getQuantityText().setText(String.valueOf(cartItem.getQuantity()));
        viewHolder.getAddBtn().setOnClickListener(v -> {
            cartItem.addQuantity();
            CartService.update(cartItem);
            updateDataSet();
        });
        viewHolder.getSubtractBtn().setOnClickListener(v -> {
            cartItem.subtractQuantity();
            if (cartItem.getQuantity() <= 0) {
                CartService.remove(cartItem.getId());
            } else {
                CartService.update(cartItem);
            }
            updateDataSet();
        });
    }

    public void updateDataSet() {
        List<CartItem> itemList = DatabaseHelper.getCartBank().getAll();
        updateDataSet(itemList);
    }

    public void updateDataSet(List<CartItem> updatedCart) {
        localDataSet.clear();
        for (CartItem item : updatedCart) {
            localDataSet.add(item);
        }
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
