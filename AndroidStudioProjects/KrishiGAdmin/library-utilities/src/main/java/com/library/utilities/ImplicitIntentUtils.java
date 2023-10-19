package com.library.utilities;

import android.content.Intent;
import android.os.Build;

public class ImplicitIntentUtils {

    private ImplicitIntentUtils() {
        throw new UnsupportedOperationException("Should not create instance of Util class. Please use as static..");
    }

    public static Intent actionGetContentIntent(String mimeType, boolean showCloudStorage) {
        Intent intent = new Intent();
        intent.setType(mimeType);

        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent = Intent.createChooser(intent, "Select a file");
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, !showCloudStorage);
        }
        return intent;
    }

    public static Intent actionPickIntent(int mediaType) {
        Intent intent = new Intent(Intent.ACTION_PICK);

        if (mediaType == 1) {
            intent.setType("image/*");

            if (Build.VERSION.SDK_INT < 19) {
                intent = Intent.createChooser(intent, "Select Image");
            } else {
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return intent;
        } else if (mediaType == 2) {
            intent.setType("video/*");

            if (Build.VERSION.SDK_INT < 19) {
                intent = Intent.createChooser(intent, "Select Video");
            } else {
                String[] mimeTypes = {"video/mp4"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return intent;
        }
        return null;
    }
}
