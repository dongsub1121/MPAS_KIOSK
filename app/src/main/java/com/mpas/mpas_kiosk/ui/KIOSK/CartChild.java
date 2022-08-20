package com.mpas.mpas_kiosk.ui.KIOSK;

public class CartChild {

    Integer itemImage;
    String tag;
    Integer count = 0;
    Integer price = 0;

    public CartChild(int drawable, String tag, Integer price) {
       this.itemImage = drawable;
       this.tag = tag;
       this.price = price;

    }

    public Integer getItemImage() {
        return itemImage;
    }

    public void setItemImage(Integer itemImage) {
        this.itemImage = itemImage;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
