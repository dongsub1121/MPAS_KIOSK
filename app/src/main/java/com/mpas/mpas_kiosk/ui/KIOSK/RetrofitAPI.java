package com.mpas.mpas_kiosk.ui.KIOSK;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @FormUrlEncoded
    @POST("jtnet/pos/trade/info")
    Single<RetroItem> singleGet(
            @Field("_uid") String uid,
            @Field("uid") String mid
    );

    @FormUrlEncoded
    @POST("jtnet/pos/trade/info")
    Call<Object> retroGet(
            @Field("_uid") String uid,
            @Field("uid") String mid
    );

    @FormUrlEncoded
    @POST("jtnet/pos/trade/new")
    Single<Object> singleNew(
            @Field("_uid") String _uid,
            @Field("_nid") String _nid,
            @Field("cid") String cid,
            @Field("pnm") String pnm,
            @Field("ptz") String ptz,
            @Field("ptw") String ptw,
            @Field("ptx") String ptx,
            @Field("ptr") String ptr,
            @Field("pty") String pty,
            @Field("pta") String pta,
            @Field("prev") String prev
    );

    @FormUrlEncoded
    @POST("jtnet/pos/trade/new")
    Call<Object> retroNew(
            @Field("_uid") String _uid,
            @Field("_nid") String _nid,
            @Field("cid") String cid,
            @Field("pnm") String pnm,
            @Field("ptz") String ptz,
            @Field("ptw") String ptw,
            @Field("ptx") String ptx,
            @Field("ptr") String ptr,
            @Field("pty") String pty,
            @Field("pta") String pta,
            @Field("prev") String prev
    );
}
