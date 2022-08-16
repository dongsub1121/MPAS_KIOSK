package com.mpas.mpas_kiosk.ui.KIOSK;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mpas_kiosk.R;
import com.mpas.mpas_kiosk.databinding.FragmentTransformBinding;
import com.mpas.mpas_kiosk.databinding.ItemTransformBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
public class MenuItemFragment extends Fragment {

    private FragmentTransformBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MenuItemViewModel menuItemViewModel =
                new ViewModelProvider(this).get(MenuItemViewModel.class);

        binding = FragmentTransformBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerviewTransform;
        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(),4));
        //ListAdapter<String, TransformViewHolder> adapter = new TransformAdapter();
        MenuRecyclerViewAdapter adapter = new MenuRecyclerViewAdapter(setItem());
        recyclerView.setAdapter(adapter);
        //menuItemViewModel.getTexts().observe(getViewLifecycleOwner(), adapter::submitList);

        RecyclerView cart = binding.cartItemRecyclerview;
        cart.setLayoutManager(new LinearLayoutManager(requireActivity()));

        menuItemViewModel.getICart().observe(getViewLifecycleOwner(),itemList ->{
            CartRecyclerViewAdapter cartRecyclerViewAdapter = new CartRecyclerViewAdapter(itemList);
            cart.setAdapter(cartRecyclerViewAdapter);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<MenuItem> setItem() {

        List<MenuItem> items = new LinkedList<>();

        items.add( new MenuItem(R.drawable.menu1,"나이트로 바닐라 크림",6500));
        items.add( new MenuItem(R.drawable.menu_2,"콜드 브루 플로트",6000));
        items.add( new MenuItem(R.drawable.menu_3,"돌체 콜드브루",5300));
        items.add( new MenuItem(R.drawable.menu_4,"바닐라 크림 콜드브루",5000));
        items.add( new MenuItem(R.drawable.menu_5,"부드러운 생크림 카스텔라",9000));
        items.add( new MenuItem(R.drawable.menu_6,"베리 수플레 치즈 케이크",13000));
        items.add( new MenuItem(R.drawable.menu_7,"리치 가나슈 케이크",15000));
        items.add( new MenuItem(R.drawable.menu_8,"초콜릿 생크림 케이크",13000));

        return items;
    }

    private static class TransformAdapter extends ListAdapter<String, TransformViewHolder> {

        private final List<Integer> drawables = Arrays.asList(
                R.drawable.avatar_1,
                R.drawable.avatar_2,
                R.drawable.avatar_3,
                R.drawable.avatar_4,
                R.drawable.avatar_5,
                R.drawable.avatar_6,
                R.drawable.avatar_7,
                R.drawable.avatar_8,
                R.drawable.avatar_9,
                R.drawable.avatar_10,
                R.drawable.avatar_11,
                R.drawable.avatar_12,
                R.drawable.avatar_13,
                R.drawable.avatar_14,
                R.drawable.avatar_15,
                R.drawable.avatar_16);

        protected TransformAdapter() {
            super(new DiffUtil.ItemCallback<String>() {
                @Override
                public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                    return oldItem.equals(newItem);
                }
            });
        }

        @NonNull
        @Override
        public TransformViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemTransformBinding binding = ItemTransformBinding.inflate(LayoutInflater.from(parent.getContext()));
            return new TransformViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TransformViewHolder holder, int position) {
            holder.textView.setText(getItem(position));
            holder.imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(holder.imageView.getResources(),
                            drawables.get(position),
                            null));
        }
    }

    private static class TransformViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public TransformViewHolder(ItemTransformBinding binding) {
            super(binding.getRoot());
            imageView = binding.imageViewItemTransform;
            textView = binding.textViewItemTransform;
        }
    }
}