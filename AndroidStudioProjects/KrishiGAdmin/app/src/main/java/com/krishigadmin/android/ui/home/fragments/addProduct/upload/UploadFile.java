package com.krishigadmin.android.ui.home.fragments.addProduct.upload;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.util.Log;

import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.ApiResponseArray;
import com.krishigadmin.android.data.remote.RequestUtils;
import com.krishigadmin.android.data.remote.api.ApiService;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.Product;
import com.krishigadmin.android.ui.home.fragments.addProduct.UploadImages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadFile {

    private static final String TAG = UploadFile.class.getSimpleName();

    private ApiService apiService;
    SharedPreferencesHelper sharedPreferencesHelper;
    private MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
    private String contentType;
    private String userId;
    private String from;
    private String uploadedByUserName = "";
    private String fileNameSendByUser = "";
    ArrayList<String> stringArrayList = new ArrayList<>();
    private String fileParameterName = "file";

    private UploadingFileListener uploadingFileListener;
    private File[] files;
    public int uploadIndex = -1;
    private long totalFileLength = 0;
    private long totalFileUploaded = 0;

    private String[] responses;

    private UploadImages uploadImages;

    @SuppressLint("StaticFieldLeak")
    private static UploadFile fileUploader = null;

    @SuppressLint("CommitPrefEdits")
    private UploadFile(ApiService service) {
        apiService = service;
    }

    /**
     * Throws IllegalStateException if this class is not initialized
     *
     * @return unique SharedPrefsManager instance
     */
    public static UploadFile getInstance() {
        if (fileUploader == null) {
            throw new IllegalStateException(
                    "SharedPrefsManager is not initialized, call initialize(applicationContext) " +
                            "static method first");
        }
/*
        UploadFile.initializeFileUploader(apiService);
        if these exception come call this method in init of activity or fragment
*/
        return fileUploader;
    }

    /**
     * Initialize this class using application Context,
     * should be called once in the beginning by any application Component
     *
     * @param service
     */
    public static void initializeFileUploader(ApiService service) {
        if (fileUploader == null) {
            synchronized (UploadFile.class) {
                if (fileUploader == null) {
                    fileUploader = new UploadFile(service);
                }
            }
        }
    }

    public class ProgressRequestBody extends RequestBody {

        private File mFile;
        private static final int DEFAULT_BUFFER_SIZE = 2048;

        public ProgressRequestBody(final File file) {
            mFile = file;
        }

        @Override
        public MediaType contentType() {
            return MediaType.parse(contentType);
        }

        @Override
        public long contentLength() throws IOException {
            return mFile.length();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            long fileLength = mFile.length();
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            FileInputStream fileInputStream = new FileInputStream(mFile);
            long uploaded = 0;

            try {
                int read;
                android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
                while ((read = fileInputStream.read(buffer)) != -1) {

                    // update progress on UI thread
                    handler.post(new ProgressUpdater(uploaded, fileLength));
                    uploaded += read;
                    sink.write(buffer, 0, read);
                }
            } finally {
                fileInputStream.close();
            }
        }


        private class ProgressUpdater implements Runnable {
            private long mUploaded;
            private long mTotal;

            public ProgressUpdater(long uploaded, long total) {
                mUploaded = uploaded;
                mTotal = total;
            }

            @Override
            public void run() {
                int current_percent = (int) (100 * mUploaded / mTotal);
                int total_percent = (int) (100 * (totalFileUploaded + mUploaded) / totalFileLength);
                uploadingFileListener.onProgressUpdate(current_percent, total_percent, uploadIndex + 1);
            }
        }
    }

    public void uploadFiles(String contentType, String userId, UploadImages uploadImages, ArrayList<String> stringArrayList, SharedPreferencesHelper sharedPreferencesHelper, String from, File[] files, UploadingFileListener uploadingFileListener) {
        this.contentType = contentType;
        this.from = from;
        this.userId = userId;
        this.uploadImages = uploadImages;
        this.stringArrayList = stringArrayList;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        this.uploadedByUserName = uploadedByUserName;
        this.fileNameSendByUser = fileNameSendByUser;
        this.files = files;
        this.uploadingFileListener = uploadingFileListener;
        totalFileUploaded = 0;
        totalFileLength = 0;
        this.uploadIndex = -1;
        uploadIndex = -1;
        responses = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            totalFileLength = totalFileLength + files[i].length();
        }
        uploadNext();
    }

    private void uploadNext() {
        if (files.length > 0) {
            if (uploadIndex != -1) {
                totalFileUploaded = totalFileUploaded + files[uploadIndex].length();
            }
            uploadIndex++;

            if (uploadIndex < files.length) {
                uploadSingleFile(uploadIndex);
            } else {
                uploadingFileListener.onFinish(responses);
            }
        } else {
            uploadingFileListener.onFinish(responses);
        }
    }

    private void uploadSingleFile(final int index) {
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(files[index]);

        MultipartBody.Part multipartBodyFileProfilePicture = MultipartBody.Part.createFormData(fileParameterName, files[index].getName(), progressRequestBody);
        Log.e("sharedPrefToken//", "" + sharedPreferencesHelper.getKeyToken());

        Call<ApiResponseArray<Category>> call = apiService.image(multipartBodyFileProfilePicture, sharedPreferencesHelper.getKeyToken());
        call.enqueue(new Callback<ApiResponseArray<Category>>() {
            @Override
            public void onResponse(Call<ApiResponseArray<Category>> call, Response<ApiResponseArray<Category>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        stringArrayList.add(response.body().getData().get(0).getUrl());
                        Log.e("stringArrayList//", "" + stringArrayList);

                        String k = stringArrayList.toString();

                        uploadImages.images(k);


                    } else {

                    }
                }
                uploadNext();
            }

            @Override
            public void onFailure(Call<ApiResponseArray<Category>> call, Throwable t) {

            }
        });


    }
}
