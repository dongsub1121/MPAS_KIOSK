package com.mpas.mpas_kiosk.ui.KIOSK;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentsData {
    @SerializedName("issuerCode") @Expose
    private String issuerCode;
    @SerializedName("issuerName") @Expose
    private String issuerName;
    @SerializedName("purchaseCode") @Expose
    private String purchaseCode;
    @SerializedName("purchaseName") @Expose
    private String purchaseName;

    public String getIssuerCode() {
        return issuerCode;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public String getPurchaseName() {
        return purchaseName;
    }
}
