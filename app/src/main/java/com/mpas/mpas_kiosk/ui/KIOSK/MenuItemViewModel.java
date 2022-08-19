package com.mpas.mpas_kiosk.ui.KIOSK;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MenuItemViewModel extends AndroidViewModel {

    private final ApiRepository repo = ApiRepository.getInstance();
    private final CompositeDisposable cd = new CompositeDisposable();


    public MenuItemViewModel(@NonNull Application application) {
        super(application);
    }

    public List<CartChild> cartItems = new ArrayList<>();
    public Cart cart = new Cart();

    public MutableLiveData<List<CartChild>> cartList = new MutableLiveData<>();
    public MutableLiveData<Integer> sum = new MutableLiveData<>();
    public MutableLiveData<Bitmap> qrData = new MutableLiveData<>();

    public MutableLiveData<Bitmap> getQrData() {
        return qrData;
    }

    public MutableLiveData<List<CartChild>> getICart() {
        return cartList;
    }

    public MutableLiveData<Integer> getSum() {
        return sum;
    }


    public void initCartItem() { cart = new Cart(); cartItems = new ArrayList<>();}

    public void addCart(CartChild cartChild) {

         cartChild.setCount(1);
        cartItems.add(cartChild);
        for (CartChild item : cartItems) {
            Log.e("cartItems",item.getTag());
        }
        Log.e("cartItems",cartItems.toString());
        cartList.setValue(cartItems);
        cartList.postValue(cartItems);

        cart.setItems(cartItems);
        sum.postValue(cart.getSumPrice());
    }

    public void updateCart(int index , CartChild cartChild) {
        cartItems.set(index, cartChild);
        cartList.postValue(cartItems);
        cart.setItems(cartItems);
        sum.postValue(cart.getSumPrice());
    }

    public void setCartItem (int index, CartChild cartChild) {
        Log.e("cartItems", String.valueOf(cartItems.get(index)));
        cartItems.set(index, cartChild);
        Log.e("cartItems", cartChild.getTag());
        Log.e("cartItems", String.valueOf(cartItems.get(index)));

    }

    public void removeCartItem (int index, CartChild cartChild) {
        cartItems.remove(index);
        cartList.postValue(cartItems);
        cart.setItems(cartItems);
        sum.postValue(cart.getSumPrice());
    }

    public void newItem() {
        Log.e("newItem", "newItem");
        cd.add(repo.singleNew(cart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();

                        String s = new Gson().toJson(o);
                        String js = s.substring(1, s.length() - 1);

                        RetroItem item = gson.fromJson(js, RetroItem.class);
                        Log.e("onSuccess", item.cbf);

                        Bitmap qrBitmap = null;
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                        try {
                            BitMatrix qr_bitMatrix = multiFormatWriter.encode(Objects.requireNonNull(item.cbf), BarcodeFormat.QR_CODE, 500, 500);
                            BarcodeEncoder qr_barcodeEncoder = new BarcodeEncoder();
                            qrBitmap = qr_barcodeEncoder.createBitmap(qr_bitMatrix);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        qrData.postValue(qrBitmap);
                        repo.setUid(item.cbf);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }
}