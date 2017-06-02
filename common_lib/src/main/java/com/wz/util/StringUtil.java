package com.wz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

import okhttp3.Response;

public class StringUtil {

    private static final String TAG = StringUtil.class.getSimpleName();

    public static String responseToString(Response response) {

        return responseToString(response, "utf-8");
    }

    public static String responseToString(Response response, String charset) {

        if (response != null) {
            return responseToString(response.body().byteStream(), charset);
        }
        return null;
    }

    public static String responseToString(InputStream is, String charset) {

        if (is != null) {
            BufferedReader bfr = null;
            InputStreamReader isr = null;
            StringBuilder sBuilder = new StringBuilder();
            try {
                try {
                    is = new GZIPInputStream(is);
                } catch (ZipException e) {
                    WLog.e(TAG, e.getMessage(), e);
                } catch (IOException e) {
                    WLog.e(TAG, e.getMessage(), e);
                }
                isr = new InputStreamReader(is, charset);
                bfr = new BufferedReader(isr);
                String line = null;
                while ((line = bfr.readLine()) != null) {
                    sBuilder.append(line);
                    sBuilder.append("\n");
                }
                return sBuilder.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    if (bfr != null) {
                        bfr.close();
                    }
                    if (isr != null) {
                        isr.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
