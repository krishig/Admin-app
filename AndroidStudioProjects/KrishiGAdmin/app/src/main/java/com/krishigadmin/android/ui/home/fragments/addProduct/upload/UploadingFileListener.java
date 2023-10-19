package com.krishigadmin.android.ui.home.fragments.addProduct.upload;

public interface UploadingFileListener {
    void onError();
    void onFinish(String[] responses);
    void onProgressUpdate(int currentPercent, int totalPercent, int fileNumber);
}