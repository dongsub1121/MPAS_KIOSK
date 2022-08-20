package com.mpas.mpas_kiosk.ui.KIOSK;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    private  static final java.lang.String BASE_URL = "http://61.33.183.227:3000/";
    private static final java.lang.String _uid = "MzQ4NTUwOTk5EUGB";
    private static java.lang.String uid = "";
    private static final java.lang.String _nid = "jtnet;1078155843;1814119990";
    private static final java.lang.String cid = "M101";
    private static java.lang.String pnm = "김밥 외";
    private static java.lang.String ptz = "1004";
    private static java.lang.String cnt = "";
    private static final java.lang.String ptw = "913";
    private static final java.lang.String ptx = "91";
    private static final java.lang.String ptr = "0";
    private static final java.lang.String pty = "0";
    private static final java.lang.String pta = "0";
    private static java.lang.String prev = "[{menu:김밥,cnt:2,price:4000}]";

    private static final java.lang.String cor = "jtnet";
    private static final java.lang.String biz = "1078155843";
    private static final java.lang.String mid = "1814119990";


    private static ApiRepository instance;

    public ApiRepository() {
    }

    public static ApiRepository getInstance() {
        if ( instance == null){
            instance = new ApiRepository();
        }
        return instance;
    }

    public void setUid(java.lang.String uid) {
        ApiRepository.uid = uid;
    }

    public RetrofitAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RetrofitAPI.class);

    public Single<RetroItem> singleGet(java.lang.String uid) {
        return api.singleGet(_uid,uid);
    }

    public Call<java.lang.Object> retroGet(java.lang.String uid) {
        return api.retroGet(_uid,uid);
    }

    public Single<java.lang.Object> singleNew(Cart cart) {
        pnm = cart.getTitle();
        ptz = java.lang.String.valueOf(cart.getSumPrice());
        cnt = java.lang.String.valueOf(cart.getCount());
        prev = java.lang.String.format("[menu:%s,cnt:%s,price:%s]",pnm, cnt, ptz);
        return api.singleNew(_uid,_nid, cid,pnm,ptz,ptw,ptx,ptr,pty,pta,prev);
    }

    public Call<java.lang.Object> retroNew() {
        return api.retroNew(_uid,_nid,cid,pnm,ptz,ptw,ptx,ptr,pty,pta,prev);
    }

    public Observable<Object> retroItemObservable(String uid) {
        Log.e("getInfo", "_uid:"+_uid+"  uid:"+uid);
        return api.observerGet(_uid,uid);
    }

}
