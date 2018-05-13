package com.pavan.headyecommerce.helper;

import java.util.Random;

public class CommonUtilities {


    public static int getRandomNumber(int max)
    {
        Random rand = new Random();
        return rand.nextInt(max) + 1;
    }

    public static String taxCalculator(float amt, float rt)
    {
        float totaltax;
        totaltax=(amt/100)*rt;
        return String.valueOf(amt+totaltax);

    }

}
