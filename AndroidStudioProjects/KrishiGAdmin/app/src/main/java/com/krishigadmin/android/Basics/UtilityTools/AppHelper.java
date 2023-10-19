package com.krishigadmin.android.Basics.UtilityTools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class AppHelper {

    public static byte[] getFileDataFromDrawable(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getFileDataFromBitmap(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @NonNull
    public static byte[] getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        assert drawable != null;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getFileDataFromDrawable(Context context, Uri uri) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream iStream = context.getContentResolver().openInputStream(uri);
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            if (iStream != null) {
                while ((len = iStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }


    public static MultipartBody.Part prepareFilePart(Context context, String partName, Uri fileUri) {
        RequestBody requestBody =
                RequestBody.create( getFileDataFromDrawable(context, fileUri), MediaType.parse(Constants.Key.CONTENT_IMAGE));
        return MultipartBody.Part.createFormData(partName, "Image"+Constants.Key.IMAGE_JPEG, requestBody);
    }

    public static MultipartBody.Part prepareFilePart(String partName, Drawable fileUri) {
        RequestBody requestBody =
                RequestBody.create(getFileDataFromDrawable(fileUri), MediaType.parse(Constants.Key.CONTENT_IMAGE));
        return MultipartBody.Part.createFormData(partName, "Image"+Constants.Key.IMAGE_JPEG, requestBody);
    }

    public static MultipartBody.Part prepareFilePart(Context context, String partName, int fileUri) {
        RequestBody requestBody =
                RequestBody.create(getBitmapFromVectorDrawable(context, fileUri), MediaType.parse(Constants.Key.CONTENT_IMAGE));
        return MultipartBody.Part.createFormData(partName, "Image"+Constants.Key.IMAGE_JPEG, requestBody);
    }

    public static MultipartBody.Part prepareFilePart(String partName) {
        RequestBody requestBody = RequestBody.create("", MediaType.parse(Constants.Key.CONTENT_IMAGE));

        return MultipartBody.Part.createFormData(partName, "", requestBody);
    }

}
