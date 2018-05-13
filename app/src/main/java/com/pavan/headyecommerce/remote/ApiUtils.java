package com.pavan.headyecommerce.remote;

public class ApiUtils {

    private static final String BASE_URL = "https://stark-spire-93433.herokuapp.com/";
    public static String SLIDER_IMAGE_URL = "file:///android_asset/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }

}