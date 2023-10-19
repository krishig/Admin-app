package com.krishigadmin.android.filepicker.model;


import com.library.adapter.recyclerview.adapter.IMultiType;
import com.library.utilities.mediastore.images.ImagesMediaFile;

public class PickerMultiItem implements IMultiType {

    private CameraAndFolder cameraAndFolder;
    private ImagesMediaFile imagesMediaFile;
    private int itemType;

    public PickerMultiItem() {
    }

    public CameraAndFolder getCameraAndFolder() {
        return cameraAndFolder;
    }

    public void setCameraAndFolder(CameraAndFolder cameraAndFolder) {
        this.cameraAndFolder = cameraAndFolder;
    }

    public ImagesMediaFile getImagesMediaFile() {
        return imagesMediaFile;
    }

    public void setImagesMediaFile(ImagesMediaFile imagesMediaFile) {
        this.imagesMediaFile = imagesMediaFile;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
