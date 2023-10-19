package com.krishigadmin.android.filepicker.model;


import com.library.adapter.recyclerview.adapter.IMultiType;

public class AddAndAddedMultiItem implements IMultiType {

    private AddImages addImages;
    private AddedImages addedImages;
    private int itemType;

    public AddAndAddedMultiItem() {
    }

    public AddImages getAddImages() {
        return addImages;
    }

    public void setAddImages(AddImages addImages) {
        this.addImages = addImages;
    }

    public AddedImages getAddedImages() {
        return addedImages;
    }

    public void setAddedImages(AddedImages addedImages) {
        this.addedImages = addedImages;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
