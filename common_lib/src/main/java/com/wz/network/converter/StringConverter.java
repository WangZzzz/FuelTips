package com.wz.network.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class StringConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody responseBody) throws IOException {
        if (responseBody != null) {
            return new String(responseBody.bytes(), "utf-8");
        }
        return null;
    }
}
