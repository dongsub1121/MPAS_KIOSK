package com.mpas.mpas_kiosk.ui.KIOSK;

import android.util.Log;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartChild> items ;
    private int sumPrice;
    private int count;
    private JSONArray jsonArray;
    private String title;

    public Cart() {
        items = new ArrayList<>();
        sumPrice = 0;
        title = null;
        jsonArray = new JSONArray();
    }

    public List<CartChild> getItems() {
        return items;
    }

    public void setItems(List<CartChild> items) {
        this.items = items;

        setSumPrice();
        setTitle();
        setJsonArray();
        setCount();
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setCount() {
        count = items.size();
    }
    public int getCount() {
        return count;
    }
    public void setSumPrice() {
        int siz = items.size();
        int totPrice = 0;
        if (siz >0) {
            for (CartChild item : items) {
                Log.e("setSumPrice", String.valueOf(item.getPrice()*item.getCount()));
                totPrice +=  item.getPrice()*item.getCount();
            }
        }
        sumPrice = totPrice;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    //TODO
    public void setJsonArray() {

    }

    public void setTitle() {
        int siz = items.size();
        if (siz ==  1) {
            title = items.get(0).getTag();
        } else if (siz >= 2) {
            title =  String.format("%s 외 %d 건", items.get(0).getTag(), siz-1);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setEditTitle(RetroItem editTitle) {
        this.title += title+ editTitle;
    }
}
