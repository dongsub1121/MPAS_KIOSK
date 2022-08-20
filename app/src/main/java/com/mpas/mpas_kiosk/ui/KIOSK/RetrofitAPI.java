package com.mpas.mpas_kiosk.ui.KIOSK;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @FormUrlEncoded
    @POST("jtnet/pos/trade/info")
    Single<RetroItem> singleGet(
            @Field("_uid") java.lang.String uid,
            @Field("uid") java.lang.String mid
    );

    @FormUrlEncoded
    @POST("jtnet/pos/trade/info")
    Call<java.lang.Object> retroGet(
            @Field("_uid") java.lang.String uid,
            @Field("uid") java.lang.String mid
    );

    @FormUrlEncoded
    @POST("jtnet/pos/trade/new")
    Single<java.lang.Object> singleNew(
            @Field("_uid") java.lang.String _uid,
            @Field("_nid") java.lang.String _nid,
            @Field("cid") java.lang.String cid,
            @Field("pnm") java.lang.String pnm,
            @Field("ptz") java.lang.String ptz,
            @Field("ptw") java.lang.String ptw,
            @Field("ptx") java.lang.String ptx,
            @Field("ptr") java.lang.String ptr,
            @Field("pty") java.lang.String pty,
            @Field("pta") java.lang.String pta,
            @Field("prev") java.lang.String prev
    );

    @FormUrlEncoded
    @POST("jtnet/pos/trade/new")
    Call<java.lang.Object> retroNew(
            @Field("_uid") java.lang.String _uid,
            @Field("_nid") java.lang.String _nid,
            @Field("cid") java.lang.String cid,
            @Field("pnm") java.lang.String pnm,
            @Field("ptz") java.lang.String ptz,
            @Field("ptw") java.lang.String ptw,
            @Field("ptx") java.lang.String ptx,
            @Field("ptr") java.lang.String ptr,
            @Field("pty") java.lang.String pty,
            @Field("pta") java.lang.String pta,
            @Field("prev") java.lang.String prev
    );

    @FormUrlEncoded
    @POST("jtnet/pos/trade/info")
    Observable<Object> observerGet (
            @Field("_uid") java.lang.String _uid,
            @Field("uid") java.lang.String uid);
}
