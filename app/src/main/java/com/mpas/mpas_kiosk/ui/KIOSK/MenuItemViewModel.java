package com.mpas.mpas_kiosk.ui.KIOSK;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MenuItemViewModel extends ViewModel {

    private final List<MenuItem> cartItems = new ArrayList<>();

    private final MutableLiveData<List<MenuItem>> cart = new MutableLiveData<>();

    public MutableLiveData<List<MenuItem>> getICart() {
        return cart;
    }

    public MenuItemViewModel() {

    }

    public void addCart(MenuItem menuItem) {
        cartItems.add(menuItem);
        for (MenuItem item : cartItems) {
            Log.e("cartItems",item.getTag());
        }
        Log.e("cartItems",cartItems.toString());
        cart.setValue(cartItems);
    }

}