package com.app.alg;

/**
 * @author:wuqi
 * @date:2020/3/18
 * @description:com.app.alg
 * @version:1.0
 */
public class GenHao {

    public static void main(String[] args) {
        System.out.println(getDouble(2));
    }

    public static double getDouble(int n){
        double maxIndex = n;
        double minIndex = 0;
        while (true){
            double half = (maxIndex+minIndex)/2;
            if (maxIndex - minIndex < 0.000000000000001){
                return half;
            }
            if(half * half > n){
                maxIndex = half;
            }else{
                minIndex = half;
            }
        }
    }
}
