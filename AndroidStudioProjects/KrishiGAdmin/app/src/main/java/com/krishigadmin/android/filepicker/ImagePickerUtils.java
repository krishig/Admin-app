package com.krishigadmin.android.filepicker;

import com.krishigadmin.android.R;
import com.krishigadmin.android.filepicker.model.AddAndAddedMultiItem;
import com.krishigadmin.android.filepicker.model.AddImages;
import com.krishigadmin.android.filepicker.model.AddedImages;

import java.util.ArrayList;
import java.util.List;

public class ImagePickerUtils {

    private ImagePickerUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static List<AddAndAddedMultiItem> createAddItem() {
        int[] imageResource = {R.drawable.ic_add_images_two};
        ArrayList<AddImages> addImagesArrayList = new ArrayList<>();

        for (int i = 0; i < imageResource.length; i++) {
            AddImages addImages = new AddImages();
            addImages.setResourceImage(imageResource[i]);
            addImagesArrayList.add(addImages);
        }

        List<AddAndAddedMultiItem> addAndAddedMultiItemArrayListAddImages = new ArrayList<>();
        for (int i = 0; i < addImagesArrayList.size(); i++) {
            AddAndAddedMultiItem addAndAddedMultiItem = new AddAndAddedMultiItem();
            addAndAddedMultiItem.setAddImages(addImagesArrayList.get(i));
            addAndAddedMultiItem.setItemType(2);
            addAndAddedMultiItemArrayListAddImages.add(addAndAddedMultiItem);
        }

        return addAndAddedMultiItemArrayListAddImages;
    }

    public static List<AddAndAddedMultiItem> createAddedItem(ArrayList<String> paths) {
        ArrayList<AddedImages> addedImagesArrayList = new ArrayList<>();

        for (int i = 0; i < paths.size(); i++) {
            AddedImages addedImages = new AddedImages();
            addedImages.setData(paths.get(i));
            addedImagesArrayList.add(addedImages);
        }

        List<AddAndAddedMultiItem> addAndAddedMultiItemArrayListAddedImages = new ArrayList<>();
        for (int i = 0; i < addedImagesArrayList.size(); i++) {
            AddAndAddedMultiItem addAndAddedMultiItem = new AddAndAddedMultiItem();
            addAndAddedMultiItem.setAddedImages(addedImagesArrayList.get(i));
            addAndAddedMultiItem.setItemType(1);
            addAndAddedMultiItemArrayListAddedImages.add(addAndAddedMultiItem);
        }

        return addAndAddedMultiItemArrayListAddedImages;
    }
}
