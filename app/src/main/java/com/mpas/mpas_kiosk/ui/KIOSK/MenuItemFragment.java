package com.mpas.mpas_kiosk.ui.KIOSK;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mpas_kiosk.R;
import com.mpas.mpas_kiosk.databinding.FragmentTransformBinding;

import java.util.LinkedList;
import java.util.List;

public class MenuItemFragment extends Fragment {

    private FragmentTransformBinding binding;
    private MenuItemViewModel menuItemViewModel;
    private LayoutInflater dLayoutInflater;
    private Dialog dialog01;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuItemViewModel =
                new ViewModelProvider(requireActivity()).get(MenuItemViewModel.class);

        binding = FragmentTransformBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dialog01 = new Dialog(requireActivity());
        dialog01.setContentView(R.layout.qr_dialog);
        dLayoutInflater = dialog01.getLayoutInflater();
        View view = dLayoutInflater.inflate(R.layout.qr_dialog,null);
        ImageView image_qr = view.findViewById(R.id.image_qr);
        TextView timer = view.findViewById(R.id.tv_timer);
        TextView guide = view.findViewById(R.id.guide);

        RecyclerView recyclerView = binding.recyclerviewTransform;
        MenuRecyclerViewAdapter adapter = new MenuRecyclerViewAdapter(setItem());
        recyclerView.setAdapter(adapter);

        RecyclerView cart = binding.cartItemRecyclerview;


        binding.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItemViewModel.newItem();
            }
        });

        menuItemViewModel.getICart().observe(requireActivity(),items ->{
            CartRecyclerViewAdapter cartRecyclerViewAdapter = (new CartRecyclerViewAdapter(items));
            cart.setAdapter(cartRecyclerViewAdapter);

            Log.e("getChildCount", String.valueOf(cartRecyclerViewAdapter.getItemCount()));
            binding.cartItemRecyclerview.scrollToPosition(cartRecyclerViewAdapter.getItemCount()-1);

        });

        menuItemViewModel.getSum().observe(requireActivity(),price -> {
            binding.tvSum.setText(String.format("%d ???",price));
        });

        menuItemViewModel.getQrData().observe(requireActivity(),qr ->{

            guide.setText("1. ?????? ??? ???????????? App??? ????????? ?????????" +'\n'+'\n'+"2. QR????????? ???????????? ?????? QR??? ?????? ?????????");
            image_qr.setImageBitmap(qr);
/*            LayoutInflater layoutInflater = dialog01.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.qr_dialog,null);
            ImageView imageView1 = view.findViewById(R.id.image_qr);
            imageView1.setImageBitmap(qr);
            TextView textView = view.findViewById(R.id.guide);
            textView.setText("1. ?????? ??? ???????????? App??? ????????? ?????????\\n\\n2. QR????????? ???????????? ?????? QR??? ?????? ?????????");
            */
            dialog01.setContentView(view);
            dialog01.show();
        });

        menuItemViewModel.getUid().observe(requireActivity(),re -> {
            //menuItemViewModel.getInfo();
            menuItemViewModel.getItem();;
        });

        menuItemViewModel.getResult().observe(requireActivity(),r->{

            if(dialog01.isShowing()) {
                Log.e("getResult","Done!!");
                guide.setText("????????? ?????? ???????????????."+'\n'+'\n'+"????????? ????????? ???????????????.");
                image_qr.setImageBitmap(null);
                dialog01.setContentView(view);
                //dialog01.show();
                menuItemViewModel.initCartItem();
                binding.tvSum.setText("0 ???");
            }

        });

        menuItemViewModel.getTimer().observe(requireActivity(),t->{
            menuItemViewModel.initCartItem();
            binding.tvSum.setText("0 ???");
            dialog01.dismiss();
        });

        menuItemViewModel.getErrorMessage().observe(requireActivity(),msg->{
            Toast.makeText(requireActivity(), "msg", Toast.LENGTH_SHORT).show();
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

        items.add( new CartChild(R.drawable.menu1,"???????????? ????????? ??????",6500));
        items.add( new CartChild(R.drawable.menu_2,"?????? ?????? ?????????",6000));
        items.add( new CartChild(R.drawable.menu_3,"?????? ????????????",5300));
        items.add( new CartChild(R.drawable.menu_4,"????????? ?????? ????????????",5000));
        items.add( new CartChild(R.drawable.menu_5,"??????????????? ????????????",9000));
        items.add( new CartChild(R.drawable.menu_6,"?????? ????????? ?????? ?????????",13000));
        items.add( new CartChild(R.drawable.menu_7,"?????? ????????? ?????????",15000));
        items.add( new CartChild(R.drawable.menu_8,"????????? ????????? ?????????",13000));

        return items;
    }
}