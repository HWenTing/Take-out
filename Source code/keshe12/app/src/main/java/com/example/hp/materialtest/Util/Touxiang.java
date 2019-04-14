package com.example.hp.materialtest.Util;

import com.example.hp.materialtest.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by HP on 2018/9/25.
 */

public class Touxiang {
    private static List<Integer> headList= Arrays.asList(new Integer[]{
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6,
            R.drawable.p7,
            R.drawable.p8,
            R.drawable.p10,

    });
    private static List<Integer> shopList= Arrays.asList(new Integer[]{
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3,
            R.drawable.s4,
            R.drawable.s5,
            R.drawable.s6,
            R.drawable.s7,
            R.drawable.s8,
            R.drawable.s9,
            R.drawable.s10,

    });
    private static List<Integer> foodList= Arrays.asList(new Integer[]{
            R.drawable.f1,
            R.drawable.f2,
            R.drawable.f3,
            R.drawable.f4,
            R.drawable.f5,
            R.drawable.f6,
            R.drawable.f7,
            R.drawable.f8,
            R.drawable.f9,
            R.drawable.f10,
            R.drawable.f11,
            R.drawable.f12,
            R.drawable.f13,
            R.drawable.f14,
            R.drawable.f15,
            R.drawable.f16,
            R.drawable.f17,
            R.drawable.f18,
            R.drawable.f19,
            R.drawable.f20,
            R.drawable.f21,
            R.drawable.f22,
            R.drawable.f23,
            R.drawable.f24,
            R.drawable.f25,
            R.drawable.f26,
            R.drawable.f28,
            R.drawable.f29,
            R.drawable.f30,
    });
    public static int getTouxiang(){
        int pos = (int)(Math.random()*(headList.size()));
        return headList.get(pos);
    }
    public static int getShopImage(){
        int pos = (int)(Math.random()*(shopList.size()));
        return shopList.get(pos);
    }
    public static int getFoodImage(){
        int pos = (int)(Math.random()*(foodList.size()));
        return foodList.get(pos);
    }
}
