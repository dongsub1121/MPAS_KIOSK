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

    private List<CartChild> db;
    private Activity activity;
    private static MenuItemViewModel menuItemViewModel;
    public CartRecyclerViewAdapter(List<CartChild> items) {
        Log.e("CartRecyclerViewAdapter","CartRecyclerViewAdapter");
        this.db = items;

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
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartChild cartChild = db.get(position);

        int count = cartChild.getCount();
        int price = cartChild.getPrice();
        String tag = cartChild.getTag();

        Log.e("CartRecyclerViewAdapter", String.format("postion : %d count: %d  price:  %d tag : %s",position,count,price,tag));

        holder.tag.setText(tag);
        holder.price.setText(String.format("%d 원",price));
        holder.quantity.setText(String.valueOf(cartChild.getCount()));

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartChild.setCount(cartChild.getCount()+1);
                Log.e("item.count", String.valueOf(cartChild.getCount()));
                menuItemViewModel.updateCart(position, cartChild);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartChild.setCount(cartChild.getCount()-1);
                Log.e("item.count", String.valueOf(cartChild.getCount()));
                menuItemViewModel.updateCart(position, cartChild);

                if(cartChild.getCount() < 1) {
                    menuItemViewModel.removeCartItem(position, cartChild);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return db.size();
    }

    public void setCart(List<CartChild> itemList) {
        this.db = itemList;
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
