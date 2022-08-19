package com.mpas.mpas_kiosk.ui.KIOSK;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    private  static final  String BASE_URL = "http://61.33.183.227:3000/";
    private static final String _uid = "MzQ4NTUwOTk5EUGB";
    private static String uid = "";
    private static final String _nid = "jtnet;1078155843;1814119990";
    private static final String cid = "M101";
    private static String pnm = "김밥 외";
    private static String ptz = "1004";
    private static String cnt = "";
    private static final String ptw = "913";
    private static final String ptx = "91";
    private static final String ptr = "0";
    private static final String pty = "0";
    private static final String pta = "0";
    private static String prev = "[{menu:김밥,cnt:2,price:4000}]";

    private static final String cor = "jtnet";
    private static final String biz = "1078155843";
    private static final String mid = "1814119990";


    private static ApiRepository instance;

    public ApiRepository() {
    }

    public static ApiRepository getInstance() {
        if ( instance == null){
            instance = new ApiRepository();
        }
        return instance;
    }

    public void setUid(String uid) {
        ApiRepository.uid = uid;
    }

    public RetrofitAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RetrofitAPI.class);

    public Single<RetroItem> singleGet(String uid) {
        return api.singleGet(_uid,uid);
    }

    public Call<Object> retroGet(String uid) {
        return api.retroGet(_uid,uid);
    }

    public Single<Object> singleNew(Cart cart) {
/*        pnm = cart.getTitle();
        ptz = String.valueOf(cart.getSumPrice());
        cnt = String.valueOf(cart.getCount());
        prev = String.format("[menu:%s,cnt:%s,price:%s]",pnm, cnt, ptz);*/
        return api.singleNew(_uid,_nid, cid,pnm,ptz,ptw,ptx,ptr,pty,pta,prev);
    }

    public Call<Object> retroNew() {
        return api.retroNew(_uid,_nid,cid,pnm,ptz,ptw,ptx,ptr,pty,pta,prev);
    }
}
