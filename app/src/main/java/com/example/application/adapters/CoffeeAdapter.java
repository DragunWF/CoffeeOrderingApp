package com.example.application.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.ViewActivity;
import com.example.application.data.Coffee;
import com.example.application.helpers.DatabaseHelper;

import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.ViewHolder> {

    private List<Coffee> localDataSet;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText, categoryText, priceText;
        private final ImageView coffeeImageBtn;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            // textView = (TextView) view.findViewById(R.id.textView);
            nameText = view.findViewById(R.id.nameText);
            categoryText = view.findViewById(R.id.categoryText);
            priceText = view.findViewById(R.id.priceText);
            coffeeImageBtn = view.findViewById(R.id.coffeeImageBtn);
        }

        public TextView getNameText() {
            return nameText;
        }

        public TextView getCategoryText() {
            return categoryText;
        }

        public TextView getPriceText() {
            return priceText;
        }

        public ImageView getCoffeeImageBtn() {
            return coffeeImageBtn;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public CoffeeAdapter(List<Coffee> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_coffee_recycler, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        // viewHolder.getTextView().setText(localDataSet[position]);
        Coffee coffee = localDataSet.get(position);
        viewHolder.getNameText().setText(coffee.getName());
        viewHolder.getCategoryText().setText(coffee.getCategory());
        viewHolder.getPriceText().setText(coffee.getPrice() + " PHP");
        viewHolder.getCoffeeImageBtn().setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewActivity.class);
            intent.putExtra(ViewActivity.VIEWED_COFFEE_ID, coffee.getId());
            context.startActivity(intent);
        });
    }

    public void updateDataSet() {
        List<Coffee> coffeeList = DatabaseHelper.getCoffeeBank().getAll();
        updateDataSet(coffeeList);
    }

    public void updateDataSet(List<Coffee> updatedCoffees) {
        localDataSet.clear();
        for (Coffee coffee : updatedCoffees) {
            localDataSet.add(coffee);
        }
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
