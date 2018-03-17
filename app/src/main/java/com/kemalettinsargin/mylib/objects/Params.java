package com.kemalettinsargin.mylib.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Written by "كمال الدّين صارغين"  on 6.10.2015.
 *و من الله توفیق
 *
 */
public class Params {
    private StringBuilder mBuilder = new StringBuilder();
    private Map<String, String> mPairs = new HashMap<>();

    public void add(NameValuePair nv) {
        mPairs.put(nv.getName(), nv.getValue());
    }
    public void add(String name,String value) {
        mPairs.put(name, value);
    }

    @Override
    public String toString() {
        if(mBuilder.length()==0) mBuilder.append("&");
        Set<String> keys = mPairs.keySet();
        boolean start = false;
        for (String key : keys) {
            if (start) {
                mBuilder.append("&");
            }
            String value = mPairs.get(key);
            mBuilder.append(key);
            mBuilder.append("=");
            mBuilder.append(value);
            start = true;
        }

        return mBuilder.toString();
    }

    public Map<String, String > getPairs() {
        return mPairs;
    }

    public boolean paramsHas(String key) {
        return mPairs.containsKey(key);
    }


    public String get(String key) {
        return mPairs.get(key);
    }
}
