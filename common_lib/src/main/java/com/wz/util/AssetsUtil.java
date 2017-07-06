package com.wz.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetsUtil {
    public static void copyFileFromAssets(Context context, String oldPath, String newPath) {
        if (context == null || TextUtils.isEmpty(oldPath) || TextUtils.isEmpty(newPath)) {
            throw new RuntimeException("context is null or oldPath is null or newPath is null!");
        }
        try {
            String[] fileNames = context.getAssets().list(oldPath);
            if (fileNames.length > 0) {
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFileFromAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }

                fos.flush();
                fos.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(Context context, String filePath) {
        if (context == null || TextUtils.isEmpty(filePath)) {
            throw new RuntimeException("context is null or filePath is null!");
        }
        try {
            String[] fileNames = context.getAssets().list(filePath);
            if (fileNames.length > 0) {
                throw new RuntimeException(filePath + " is not a file!");
            }
            InputStream is = context.getAssets().open(filePath);
            return ConvertUtil.inputStream2String(is, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
