package com.wz.network.converter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public class BitmapConverter implements Converter<ResponseBody, Bitmap> {
    @Override
    public Bitmap convert(ResponseBody responseBody) throws IOException {
        if (responseBody != null) {
            InputStream is = responseBody.byteStream();
            if (is != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }
        }
        return null;
    }
}
