package com.mpas.mpas_kiosk.ui.KIOSK;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mpas_kiosk.R;
import com.mpas.mpas_kiosk.databinding.FragmentTransformBinding;

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
    private MenuItemViewModel menuItemViewModel;

    Dialog dialog01;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuItemViewModel =
                new ViewModelProvider(requireActivity()).get(MenuItemViewModel.class);

        binding = FragmentTransformBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dialog01 = new Dialog(requireActivity());
        dialog01.setContentView(R.layout.qr_dialog);

        RecyclerView recyclerView = binding.recyclerviewTransform;
        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(),4));
        MenuRecyclerViewAdapter adapter = new MenuRecyclerViewAdapter(setItem());
        recyclerView.setAdapter(adapter);

        RecyclerView cart = binding.cartItemRecyclerview;
        cart.setLayoutManager(new LinearLayoutManager(requireActivity()));


        binding.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItemViewModel.newItem();
            }
        });

        menuItemViewModel.getICart().observe(requireActivity(),items ->{
            cart.setAdapter(new CartRecyclerViewAdapter(items));
            Log.e("getChildCount", String.valueOf(cart.getChildCount()));
            binding.scrollview1.scrollBy(0,200);

            Log.e("observe",items.toString());

        });

        menuItemViewModel.getSum().observe(requireActivity(),price -> {
            binding.tvSum.setText(String.format("%d 원",price));
        });

        menuItemViewModel.getQrData().observe(requireActivity(),qr ->{

            LayoutInflater layoutInflater = dialog01.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.qr_dialog,null);
            ImageView imageView1 = view.findViewById(R.id.image_qr);
            imageView1.setImageBitmap(qr);

            dialog01.setContentView(view);
            dialog01.show();



        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<CartChild> setItem() {

        List<CartChild> items = new LinkedList<>();

        items.add( new CartChild(R.drawable.menu1,"나이트로 바닐라 크림",6500));
        items.add( new CartChild(R.drawable.menu_2,"콜드 브루 플로트",6000));
        items.add( new CartChild(R.drawable.menu_3,"돌체 콜드브루",5300));
        items.add( new CartChild(R.drawable.menu_4,"바닐라 크림 콜드브루",5000));
        items.add( new CartChild(R.drawable.menu_5,"부드러운 생크림 카스텔라",9000));
        items.add( new CartChild(R.drawable.menu_6,"베리 수플레 치즈 케이크",13000));
        items.add( new CartChild(R.drawable.menu_7,"리치 가나슈 케이크",15000));
        items.add( new CartChild(R.drawable.menu_8,"초콜릿 생크림 케이크",13000));

        return items;
    }
}