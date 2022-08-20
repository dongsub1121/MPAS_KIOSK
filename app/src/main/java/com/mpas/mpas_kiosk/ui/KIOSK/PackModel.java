package com.mpas.mpas_kiosk.ui.KIOSK;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackModel {
    @SerializedName("app_tm") @Expose
    private String app_tm;
    @SerializedName("app_no") @Expose
    private String app_no;
    @SerializedName("authTime") @Expose
    private String authTime;
    @SerializedName("tran_un") @Expose
    private String tran_un;
    @SerializedName("price") @Expose
    private int price;
    @SerializedName("res_cd") @Expose
    private String res_cd;
    @SerializedName("res_mg1") @Expose
    private String res_mg1;
    @SerializedName("clientData") @Expose
    private PaymentsData clientData;


    public String getApp_tm() {
        return app_tm;
    }

    public String getApp_no() {
        return app_no;
    }

    public String getAuthTime() {
        return authTime;
    }

    public String getTran_un() {
        return tran_un;
    }

    public int getPrice() {
        return price;
    }

    public String getRes_cd() {
        return res_cd;
    }

    public String getRes_mg1() {
        return res_mg1;
    }

    public PaymentsData getClientData() {
        return clientData;
    }
}
