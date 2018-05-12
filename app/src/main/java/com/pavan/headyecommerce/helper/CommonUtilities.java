package com.pavan.headyecommerce.helper;

import java.util.Random;

public class CommonUtilities {

    public static int getRandomNumber(int max)
    {
        Random rand = new Random();
        int  n = rand.nextInt(max) + 1;
        return n;
    }

    public static String taxCalculator(float amt, float rt)
    {
        float totaltax=0;
        totaltax=(amt/100)*rt;
        return String.valueOf(amt+totaltax);

    }

}
