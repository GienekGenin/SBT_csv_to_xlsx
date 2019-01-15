package com.quadstingray.javafx.sample;

import java.util.ArrayList;

class ProductModel {
    // KODTOWARU
    ArrayList<String> refDes = new ArrayList<>();
    String uwagi, value, producent, patternName, vendo;
    Integer count = 0;
    float ulamek = 0;
    float goldpins = 0;

    int getCount() {
        return count;
    }

    void setCount(int count) {
        this.count = count;
    }

    ProductModel(String uwagi,String value,String producent, String patternName, String vendo, float ulamek){
        this.uwagi = uwagi;
        this.value = value;
        this.producent = producent;
        this.patternName = patternName;
        this.ulamek = ulamek;
        this.vendo = vendo;
    }
}
