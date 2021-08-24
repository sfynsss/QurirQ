package com.mss.quriq.Response;

import com.mss.quriq.Api.RetrofitClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class Utils {

    public static ApiError convertError(ResponseBody responseBody) {
        Converter<ResponseBody, ApiError> converter = RetrofitClient
                .getRetrofit()
                .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError apiError = null;

        try {
            apiError = converter.convert(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apiError;
    }
}
