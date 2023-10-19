package com.krishigadmin.android.filepicker;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krishigadmin.android.R;
import com.krishigadmin.android.filepicker.adapter.ImagesFromRecyclerViewAdapter;
import com.krishigadmin.android.filepicker.adapter.SelectedImagesRecyclerViewAdapter;
import com.krishigadmin.android.filepicker.model.CameraAndFolder;
import com.krishigadmin.android.filepicker.model.PickerMultiItem;
import com.krishigadmin.android.ui.AppConstants;
import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.adapter.recyclerview.decoration.GridSpacingItemDecoration;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemClick;
import com.library.utilities.ImageAndVideoUtils;
import com.library.utilities.ImplicitIntentUtils;
import com.library.utilities.file.FileProviderUtils;
import com.library.utilities.file.MediaFileUtils;
import com.library.utilities.file.RealPathUtils;
import com.library.utilities.mediastore.MediaStoreUtils;
import com.library.utilities.mediastore.MediaType;
import com.library.utilities.mediastore.SourceOfMedia;
import com.library.utilities.mediastore.images.ImagesMediaFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

;

public class ImagePickerDialogFragment extends DialogFragment {

    private static final String TAG = ImagePickerDialogFragment.class.getSimpleName();

    private static ImagePickerListener imagePickerListener;

    private ImageView backImageView;
    private TextView doneButtonTextView;
    private RecyclerView imagesFromRecyclerView, selectedImagesRecyclerView;

    private int[] imageResource = {R.drawable.ic_camera, R.drawable.ic_folder};
    private String[] title = {"Camera", "Folder"};
    private ArrayList<CameraAndFolder> cameraFolderPickerImagesArrayList = new ArrayList<>();
    private ArrayList<PickerMultiItem> pickerMultiItemArrayList = new ArrayList<>();
    private ImagesFromRecyclerViewAdapter imagesFromRecyclerViewAdapter;

    private ArrayList<String> captureImagesArrayList = new ArrayList<>();
    private ArrayList<String> folderImagesArrayList = new ArrayList<>();
    private ArrayList<String> selectedImagesArrayList = new ArrayList<>();
    private SelectedImagesRecyclerViewAdapter selectedImagesRecyclerViewAdapter;

    private Uri imageUri;
    private static final int FOLDER_REQUEST_CODE = 3001;

    public static ImagePickerDialogFragment display(ImagePickerListener listener) {
        ImagePickerDialogFragment barCodeScannerDialogFragment = new ImagePickerDialogFragment();
        imagePickerListener = listener;
        return barCodeScannerDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_image_picker_dialog, container, false);
        backImageView = view.findViewById(R.id.backButtonImageView);
        imagesFromRecyclerView = view.findViewById(R.id.imagesFromRecyclerView);
        selectedImagesRecyclerView = view.findViewById(R.id.selectedImagesRecyclerView);
        doneButtonTextView = view.findViewById(R.id.doneButtonTextView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        createCameraAndFolderItem();
        getExternalStorageImages();

        imagesFromRecyclerView();
        selectedImagesRecyclerView();

        doneButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelected();
                imagePickerListener.onImageSelectionFinish(selectedImagesArrayList);
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                window.setLayout(width, height);
                window.setWindowAnimations(R.style.BottomSheetAnimation);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImageAndVideoUtils.CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String realPath = RealPathUtils.getRealPath(getContext(), imageUri);
                captureImagesArrayList.add(realPath);
                getSelected();
            } else if (resultCode == RESULT_CANCELED) {
                if (Build.VERSION.SDK_INT >= 29) {
                    ContentResolver contentResolver = getContext().getContentResolver();
                    ContentValues updateContentValue = new ContentValues();
                    updateContentValue.put(MediaStore.Images.Media.IS_PENDING, true);
                    contentResolver.update(imageUri, updateContentValue, null, null);
                }

                Toast.makeText(getContext(), "User cancelled capture image", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == FOLDER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    /* Here read store permission require */
                    imageUri = data.getData();

                    if (imageUri != null) {
                        String realPath = RealPathUtils.getRealPath(getContext(), imageUri);
                        folderImagesArrayList.add(realPath);
                        getSelected();
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), "User cancelled select image", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Sorry! Failed to select image", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void createCameraAndFolderItem() {
        for (int i = 0; i < imageResource.length; i++) {
            CameraAndFolder cameraAndFolder = new CameraAndFolder();
            cameraAndFolder.setResourceImage(imageResource[i]);
            cameraAndFolder.setItemTitle(title[i]);
            cameraFolderPickerImagesArrayList.add(cameraAndFolder);
        }

        List<PickerMultiItem> pickerMultiItemsCameraAndFolder = new ArrayList<>();
        for (int i = 0; i < cameraFolderPickerImagesArrayList.size(); i++) {
            PickerMultiItem multiItem = new PickerMultiItem();
            multiItem.setCameraAndFolder(cameraFolderPickerImagesArrayList.get(i));
            multiItem.setItemType(1);
            pickerMultiItemsCameraAndFolder.add(multiItem);
        }
        pickerMultiItemArrayList.addAll(pickerMultiItemsCameraAndFolder);
    }

    public void getExternalStorageImages() {
        Uri sourceUri = MediaStoreUtils.getSourceOfMedia(SourceOfMedia.EXTERNAL, MediaType.IMAGES);

        List<String> projections = MediaStoreUtils.getProjection(MediaType.IMAGES);
        String[] projection = projections.toArray(new String[projections.size()]);

        String selection = MediaStore.Images.Media.DATE_ADDED + " >= ?";

        String[] selectionArgs = new String[]{String.valueOf(1586180938)};

        /* Descending order according to the added time, that is, the latest shot is in front */
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

        List<ImagesMediaFile> mediaFilesArrayList = MediaStoreUtils.readImageMediaFiles(
                getContext(),
                sourceUri,
                projection,
                selection,
                selectionArgs,
                sortOrder
        );

        /*List<ImagesMediaFile> mediaFilesArrayList = new ArrayList<>();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        while (cursor.moveToNext()) {
            String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            ImagesMediaFile imagesMediaFile = new ImagesMediaFile();
            imagesMediaFile.setData(absolutePathOfImage);
            mediaFilesArrayList.add(imagesMediaFile);
        }
        cursor.close();*/

        List<PickerMultiItem> pickerMultiItemsPicture = new ArrayList<>();
        for (int i = 0; i < mediaFilesArrayList.size(); i++) {
            PickerMultiItem multiItem = new PickerMultiItem();
            multiItem.setImagesMediaFile(mediaFilesArrayList.get(i));
            multiItem.setItemType(2);
            pickerMultiItemsPicture.add(multiItem);
        }
        pickerMultiItemArrayList.addAll(pickerMultiItemsPicture);
    }

    public void imagesFromRecyclerView() {
        selectedImagesRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        imagesFromRecyclerView.setLayoutManager(gridLayoutManager);
        imagesFromRecyclerView.setItemAnimator(new DefaultItemAnimator());

        int spanCount = 3; // 3 columns
        int spacing = 8; // 8px
        boolean includeEdges = false;
        imagesFromRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdges));

        imagesFromRecyclerViewAdapter = new ImagesFromRecyclerViewAdapter(getContext(), this);
        imagesFromRecyclerViewAdapter.addArrayList(pickerMultiItemArrayList);
        imagesFromRecyclerView.setAdapter(imagesFromRecyclerViewAdapter);

        imagesFromRecyclerViewAdapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClick<PickerMultiItem>() {
            @Override
            public void OnItemClick(View itemView, PickerMultiItem pickerImagesMultiItem, int position) {
                if (position == 0) {
                    createdFileUriAndCaptureImage();
                } else if (position == 1) {
                    openFolder();
                }
            }
        });

        imagesFromRecyclerViewAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<PickerMultiItem>() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void OnItemChildClick(View viewChild, PickerMultiItem pickerImagesMultiItem, int position) {
                switch (viewChild.getId()) {
                    case R.id.checkBox:
                        imagesFromRecyclerViewAdapter.checkCheckBox(position, !(imagesFromRecyclerViewAdapter.isSelected(position)));
                        getSelected();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void selectedImagesRecyclerView() {
        selectedImagesRecyclerView.setHasFixedSize(true);
        selectedImagesRecyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerHorizontal(getContext()));
        selectedImagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        selectedImagesRecyclerViewAdapter = new SelectedImagesRecyclerViewAdapter(getContext());
        selectedImagesRecyclerViewAdapter.addArrayList(selectedImagesArrayList);
        selectedImagesRecyclerView.setAdapter(selectedImagesRecyclerViewAdapter);
    }

    private void createdFileUriAndCaptureImage() {
        String customDirectoryName = "AppName";
        String fileName = MediaFileUtils.getRandomFileName(1);
        String extension = ".jpg";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri sourceUri = MediaStoreUtils.getSourceOfMedia(SourceOfMedia.EXTERNAL, MediaType.IMAGES);
            ContentResolver contentResolver = getContext().getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.TITLE, fileName);
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName + extension);
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            long millis = System.currentTimeMillis();
            contentValues.put(MediaStore.Images.Media.DATE_ADDED, millis / 1000L);
            contentValues.put(MediaStore.Images.Media.DATE_MODIFIED, millis / 1000L);
            contentValues.put(MediaStore.Images.Media.DATE_TAKEN, millis);
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + customDirectoryName);
            contentValues.put(MediaStore.Images.Media.IS_PENDING, false);
            imageUri = contentResolver.insert(sourceUri, contentValues);
        } else {
            File mediaFile = null;
            try {
                mediaFile = MediaFileUtils.createFile(getActivity(), 1, customDirectoryName, extension);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageUri = FileProviderUtils.getFileUri(getContext(), mediaFile, AppConstants.AppConfig.PACKAGE_NAME);
        }

        Intent intent = ImageAndVideoUtils.cameraIntent(1, imageUri, getActivity());
        startActivityForResult(intent, ImageAndVideoUtils.CAPTURE_IMAGE_REQUEST_CODE);
    }

    private void openFolder() {
        Intent intent = ImplicitIntentUtils.actionGetContentIntent("image/*", true);
        startActivityForResult(intent, FOLDER_REQUEST_CODE);
    }

    private <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        Set<T> set = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    private void getSelected() {
        SparseBooleanArray selectedRows = imagesFromRecyclerViewAdapter.getSelectedIds();
        selectedImagesArrayList.clear();

        if (captureImagesArrayList.size() != 0)
            selectedImagesArrayList.addAll(captureImagesArrayList);
        if (folderImagesArrayList.size() != 0)
            selectedImagesArrayList.addAll(folderImagesArrayList);

        if (selectedRows.size() > 0) {
            for (int i = 0; i < selectedRows.size(); i++) {
                int key = selectedRows.keyAt(i);
                String path = pickerMultiItemArrayList.get(key).getImagesMediaFile().getData();
                Log.e(TAG, "KEY : " + path);
                selectedImagesArrayList.add(path);
            }
        }
        selectedImagesRecyclerViewAdapter.replaceArrayList(removeDuplicates(selectedImagesArrayList));
    }
}