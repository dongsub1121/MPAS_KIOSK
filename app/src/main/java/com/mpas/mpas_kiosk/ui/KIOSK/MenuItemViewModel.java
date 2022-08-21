package com.mpas.mpas_kiosk.ui.KIOSK;

import android.annotation.SuppressLint;
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
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.core.Maybe;
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
    public MutableLiveData<Boolean> result = new MutableLiveData<>();
    public MutableLiveData<String> uid = new MutableLiveData<>();
    public MutableLiveData<Boolean> paymentsDone = new MutableLiveData<>();
    public MutableLiveData<Integer> dialogTimer = new MutableLiveData<>();

    public MutableLiveData<Integer> getTimer() {
        return dialogTimer;
    }

    public MutableLiveData<Boolean> getPaymentsDone() {
        return paymentsDone;
    }

    public MutableLiveData<String> getUid() {
        return uid;
    }

    public MutableLiveData<Boolean> getResult() {
        return result;
    }

    public MutableLiveData<Bitmap> getQrData() {
        return qrData;
    }

    public MutableLiveData<List<CartChild>> getICart() {
        return cartList;
    }

    public MutableLiveData<Integer> getSum() {
        return sum;
    }


    public void initCartItem() {
        cartItems.removeAll(cartItems);
        cartList.postValue(cartItems);
        cart.setItems(cartItems);
        Log.e("cartitem", String.valueOf(cartItems.size()));
    }//cart = new Cart(); cartItems = new ArrayList<>();}

    public void addCart(CartChild cartChild) {

        cartChild.setCount(1);
        cartItems.add(cartChild);
        for (CartChild item : cartItems) {
            Log.e("cartItems", item.getTag());
        }
        Log.e("cartItems", cartItems.toString());
        cartList.setValue(cartItems);
        cartList.postValue(cartItems);

        cart.setItems(cartItems);
        sum.postValue(cart.getSumPrice());
    }

    public void updateCart(int index, CartChild cartChild) {
        cartItems.set(index, cartChild);
        cartList.postValue(cartItems);
        cart.setItems(cartItems);
        sum.postValue(cart.getSumPrice());
    }

    public void setCartItem(int index, CartChild cartChild) {
        Log.e("cartItems", java.lang.String.valueOf(cartItems.get(index)));
        cartItems.set(index, cartChild);
        Log.e("cartItems", cartChild.getTag());
        Log.e("cartItems", java.lang.String.valueOf(cartItems.get(index)));

    }

    public void removeCartItem(int index, CartChild cartChild) {
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
                .subscribeWith(new DisposableSingleObserver<java.lang.Object>() {
                    @Override
                    public void onSuccess(java.lang.Object o) {
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();

                        java.lang.String s = new Gson().toJson(o);
                        java.lang.String js = s.substring(1, s.length() - 1);

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
                        uid.setValue(item.uid);
                        Log.e("uid", getUid().toString());
                        repo.setUid(item.uid);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }

    @SuppressLint("CheckResult")
    public void getInfo() {

        boolean iu;

        Gson gson = new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .create();

        Observable<RetroItem> itemObservable = repo.retroItemObservable(getUid().getValue())
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Object, ObservableSource<? extends RetroItem>>() {
                    @Override
                    public Observable<RetroItem> apply(Object o) throws Exception {
                        String s = gson.toJson(o);
                        Log.e("apply", s);
                        String js = s.substring(1, s.length() - 1);
                        RetroItem item = gson.fromJson(js, RetroItem.class);
                        Log.e("Observable_hit", item.hit);

                        return Observable.just(item);
                    }
                })
                .repeat(30)
                .filter(retroItem -> retroItem.hit.equals("20.0"));


        itemObservable.subscribeWith(new DisposableObserver<RetroItem>() {
            @Override
            public void onNext(RetroItem retroItem) {
                if (retroItem.hit.equals("20.0")) {
                    String s = retroItem.pack;
                    String js = s.substring(1, s.length() - 1);
                    //Log.e("retroItem.pack", js );
                    PackModel pack = gson.fromJson(js, PackModel.class);

                    Log.e("subscribe", String.valueOf(pack.getClientData().getIssuerName()));

                    result.postValue(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        });

    }

    @SuppressLint("CheckResult")
    public void getItem() {
        boolean iu;

        Gson gson = new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .create();

        Observable<RetroItem> itemObservable = repo.retroItemObservable(getUid().getValue())
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Object, ObservableSource<? extends RetroItem>>() {
                    @Override
                    public Observable<RetroItem> apply(Object o) throws Exception {
                        String s = gson.toJson(o);
                        String js = s.substring(1, s.length() - 1);
                        RetroItem item = gson.fromJson(js, RetroItem.class);

                        return Observable.just(item);
                    }
                })
                .repeat(15)
                .filter(retroItem -> retroItem.hit.equals("20.0"));

        itemObservable.subscribeWith(new DisposableObserver<RetroItem>() {
            @Override
            public void onNext(RetroItem retroItem) {
                if (retroItem.hit.equals("20.0")) {
                    String s = retroItem.pack;
                    String js = s.substring(1, s.length() - 1);
                    //Log.e("retroItem.pack", js );
                    PackModel pack = gson.fromJson(js, PackModel.class);

                    Log.e("subscribe", String.valueOf(pack.getClientData().getIssuerName()));
                    result.postValue(true);

                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        });

    }
}

