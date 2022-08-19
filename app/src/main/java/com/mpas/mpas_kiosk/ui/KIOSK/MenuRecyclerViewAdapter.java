package com.mpas.mpas_kiosk.ui.KIOSK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.mpas.mpas_kiosk.R;
import java.util.List;

public class MenuRecyclerViewAdapter  extends RecyclerView.Adapter<MenuRecyclerViewAdapter.MenuViewHolder>{

    private final List<CartChild> db;
    private Activity activity;
    private MenuItemViewModel menuItemViewModel;
    public MenuRecyclerViewAdapter(List<CartChild> items) {
        this.db = items;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        activity = (Activity) parent.getContext();

        menuItemViewModel =
                new ViewModelProvider((ViewModelStoreOwner) activity).get(MenuItemViewModel.class);

        return new MenuViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        CartChild cartChild = db.get(position);

        int count = cartChild.getCount();
        int price = cartChild.getPrice();
        int draw = cartChild.getItemImage();
        String tag = cartChild.getTag();

        Log.e("onBindViewHolder", String.format("postion : %d count: %d  price:  %d tag : %s",position,count,price,tag));

        holder.imageView.setImageResource(draw);
        holder.tag.setText(tag);
        holder.price.setText(String.format("%d 원",price));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItemViewModel.addCart(cartChild);
            }
        });


    }

    @Override
    public int getItemCount() {
        return db.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tag;
        TextView price;
        Button add;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemImage);
            tag = itemView.findViewById(R.id.itemTag);
            price = itemView.findViewById(R.id.itemPrice);
            add = itemView.findViewById(R.id.add);
        }
    }
}
