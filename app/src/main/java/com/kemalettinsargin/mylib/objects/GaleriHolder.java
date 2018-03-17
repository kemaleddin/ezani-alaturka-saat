package com.kemalettinsargin.mylib.objects;

import java.util.ArrayList;

/**
 * Written by "كمال الدّين صارغين"  on 23.03.2017.
 * و من الله توفیق
 */

public class GaleriHolder {
    private int galeriId,pos;
    private ArrayList<String> fotograflar;


    public GaleriHolder(int galeriId, ArrayList<String> fotograflar,int pos) {
        this.galeriId = galeriId;
        this.fotograflar = fotograflar;
        this.pos=pos;
    }

    public GaleriHolder() {

    }

    public int getGaleriId() {
        return galeriId;
    }

    public void setGaleriId(int galeriId) {
        this.galeriId = galeriId;
    }

    public ArrayList<String> getFotograflar() {
        return fotograflar;
    }

    public void setFotograflar(ArrayList<String> fotograflar) {
        this.fotograflar = fotograflar;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
