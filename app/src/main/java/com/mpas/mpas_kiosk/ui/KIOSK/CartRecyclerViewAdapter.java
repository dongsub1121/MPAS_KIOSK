package com.mpas.mpas_kiosk.ui.KIOSK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mpas_kiosk.R;

import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>{

    private final List<MenuItem> db;
    private Activity activity;
    private MenuItemViewModel menuItemViewModel;
    public CartRecyclerViewAdapter(List<MenuItem> items) {
        this.db = items;
        Log.e("CartRecyclerViewAdapter","CartRecyclerViewAdapter");
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_rows,parent,false);
        activity = (Activity) parent.getContext();

        menuItemViewModel =
                new ViewModelProvider((ViewModelStoreOwner) activity).get(MenuItemViewModel.class);

        return new CartViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        MenuItem menuItem = db.get(position);

        int count = menuItem.getCount();
        int price = menuItem.getPrice();
        String tag = menuItem.getTag();

        Log.e("CartRecyclerViewAdapter", String.format("postion : %d count: %d  price:  %d tag : %s",position,count,price,tag));

        holder.tag.setText(tag);
        holder.price.setText(String.format("%d 원",price));
        holder.quantity.setText(menuItem.getCount());
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItem.setCount(count+1);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItem.setCount(count-1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return db.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        TextView tag, price;
        EditText quantity;
        Button plus,minus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            tag = itemView.findViewById(R.id.cart_item_tag);
            price = itemView.findViewById(R.id.cart_item_price);
            quantity = itemView.findViewById(R.id.edit_quantity);
            plus = itemView.findViewById(R.id.bt_plus);
            minus = itemView.findViewById(R.id.bt_minus);
        }
    }
}
