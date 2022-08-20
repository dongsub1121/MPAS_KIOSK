package com.mpas.mpas_kiosk.ui.KIOSK;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackClass {
    @SerializedName("data")
    private List<RetroItem> packModels;

    public List<RetroItem> getPackModels() {
        return packModels;
    }
}
