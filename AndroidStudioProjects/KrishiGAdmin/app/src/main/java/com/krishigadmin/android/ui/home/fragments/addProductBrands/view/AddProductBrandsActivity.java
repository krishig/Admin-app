package com.krishigadmin.android.ui.home.fragments.addProductBrands.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.RequestUtils;
import com.krishigadmin.android.data.remote.glide.GlideImageLoader;
import com.krishigadmin.android.data.remote.glide.GlideImageLoadingListener;
import com.krishigadmin.android.databinding.ActivityAddProductBrandsBinding;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.ProductBrands;
import com.krishigadmin.android.model.SubCategory;
import com.krishigadmin.android.model.User;
import com.krishigadmin.android.searchdialog.Search;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.krishigadmin.android.ui.home.fragments.addSubCategory.imageUpload.file.FileUtils;
import com.krishigadmin.android.ui.home.fragments.addSubCategory.imageUpload.imageCompressor.Compressor;
import com.krishigadmin.android.ui.home.fragments.addSubCategory.imageUpload.imageCompressor.CompressorFolderException;
import com.krishigadmin.android.ui.home.fragments.addSubCategory.view.AddToSubCategoryActivity;
import com.krishigadmin.android.ui.home.fragments.manageBrands.viewmodel.BrandModel;
import com.library.utilities.ImageAndVideoUtils;
import com.library.utilities.ImplicitIntentUtils;
import com.library.utilities.file.FileProviderUtils;
import com.library.utilities.file.MediaFileUtils;
import com.library.utilities.file.MemoryUtils;
import com.library.utilities.file.RealPathUtils;
import com.library.utilities.file.StorageUtils;
import com.library.utilities.permissionutils.ManagePermission;
import com.library.utilities.permissionutils.PermissionDialog;
import com.library.utilities.permissionutils.PermissionName;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class AddProductBrandsActivity extends BaseActivity<ActivityAddProductBrandsBinding> {

    BrandModel viewModel;
    String from = "", brandId = "", brandName = "";

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    private static final int MULTIPLE_PERMISSION_REQUEST_CODE = 1001;
    public static final String[] MULTIPLE_PERMISSIONS =
            {
                    PermissionName.READ_EXTERNAL_STORAGE,
                    PermissionName.WRITE_EXTERNAL_STORAGE,
                    PermissionName.CAMERA
            };

    private ManagePermission managePermission;
    private Uri profilePictureUri;


    private Uri imageUri;
    private String realPath = "Path";

    private ArrayList<Search> vehicleTypeSearchArrayList = new ArrayList<Search>();
    private ArrayList<Category> vehicleTypeArraylist = new ArrayList<Category>();
    String brandImageUrl = "";


    @Override
    protected ActivityAddProductBrandsBinding getViewBinding() {
        return ActivityAddProductBrandsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(AddProductBrandsActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void initializeViewModel() {
        viewModel = createViewModel(BrandModel.class);

    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {
        managePermission = new ManagePermission(this);

        if (getIntent().getExtras() != null) {
            from = getIntent().getExtras().getString(AppConstants.Extras.FROM);
            brandId = getIntent().getExtras().getString(AppConstants.Extras.PRODUCT_BRAND_ID);
            brandName = getIntent().getExtras().getString(AppConstants.Extras.PRODUCT_BRAND_NAME);
            if (brandName != null) {
                brandImageUrl = getIntent().getExtras().getString(AppConstants.Extras.PRODUCT_BRAND_IMAGE);
            }
        }
        if (brandName != null) {
            viewBinding.productBrandsTextInputEditText.setText(brandName);
        }
        if (brandImageUrl != null) {
            GlideImageLoader.load(this,
                    brandImageUrl,
                    R.drawable.image_product,
                    R.drawable.image_product,
                    viewBinding.productImageView,
                    new GlideImageLoadingListener() {
                        @Override
                        public void imageLoadSuccess() {

                        }

                        @Override
                        public void imageLoadError() {

                        }
                    }
            );
        }
    }

    @Override
    protected void addTextChangedListener() {

    }

    @Override
    protected void setOnFocusChangeListener() {

    }

    @Override
    protected void observeViewModel() {

        Observer<String> uploadImageUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.uploadImageUserError().observe(this, uploadImageUserError);

        final Observer<ArrayList<Category>> uploadImageUserSuccess = new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> category) {
                hideProgressDialog();

                        brandImageUrl = category.get(0).getUrl();
                        GlideImageLoader.load(AddProductBrandsActivity.this,
                                brandImageUrl,
                                R.drawable.image_product,
                                R.drawable.image_product,
                                viewBinding.productImageView,
                                new GlideImageLoadingListener() {
                                    @Override
                                    public void imageLoadSuccess() {

                                    }

                                    @Override
                                    public void imageLoadError() {

                                    }
                                }
                        );


            }
        };
        viewModel.uploadImageUserSuccess().observe(this, uploadImageUserSuccess);

        Observer<String> addProductBrandsUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.addProductBrandsUserError().observe(this, addProductBrandsUserError);

        final Observer<ProductBrands> addProductBrandsUserSuccess = new Observer<ProductBrands>() {
            @Override
            public void onChanged(ProductBrands productBrands) {
                hideProgressDialog();
                finish();
            }
        };
        viewModel.addProductBrandsUserSuccess().observe(this, addProductBrandsUserSuccess);


        Observer<String> editProductBrandUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.editProductBrandUserError().observe(this, editProductBrandUserError);

        final Observer<ProductBrands> editProductBrandUserSuccess = new Observer<ProductBrands>() {
            @Override
            public void onChanged(ProductBrands productBrands) {
                hideProgressDialog();
                finish();
            }
        };
        viewModel.editProductBrandUserSuccess().observe(this, editProductBrandUserSuccess);

    }

    @Override
    protected void setOnClickListener() {
        viewBinding.icBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewBinding.cameraLinearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                    Log.e("TAG", "permission already granted");
                    showPictureDialog(AddProductBrandsActivity.this);
                } else {
                    Log.e("TAG", "permission is not granted, request for permission");
                    requestPermissions(
                            MULTIPLE_PERMISSIONS,
                            MULTIPLE_PERMISSION_REQUEST_CODE);
                }
            }
        });

        viewBinding.crossImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brandImageUrl = "";
                viewBinding.productImageView.setImageResource(R.drawable.image_product);
            }
        });


        viewBinding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productBrand = viewBinding.productBrandsTextInputEditText.getText().toString().trim();
                if (productBrand.equalsIgnoreCase("")) {
                    viewBinding.productBrandsInputLayout.setError("Please Enter Product Brand Name");
                    viewBinding.productBrandsInputLayout.setErrorEnabled(true);
                } else if (brandImageUrl.equalsIgnoreCase("")) {
                    Toast.makeText(AddProductBrandsActivity.this, "Please Upload Product Brand Image", Toast.LENGTH_SHORT).show();
                    showSnackBar(viewBinding.addProductBrands, "Please Upload Product Brand Image");
                } else {
                    viewBinding.productBrandsInputLayout.setError("");
                    viewBinding.productBrandsInputLayout.setErrorEnabled(false);

                    if (from.equalsIgnoreCase("0")) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("brand_name", productBrand);
                            jsonObject.put("brand_image_url", brandImageUrl);
                            RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject.toString());
                            viewModel.addProductBrands(requestBody, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                        } catch (Exception exception) {

                        }
                    } else if (from.equalsIgnoreCase("1")) {
                        if (brandId != null) {
                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("brand_name", productBrand);
                                jsonObject.put("brand_image_url", brandImageUrl);
                                RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject.toString());
                                viewModel.editProductBrands(brandId, requestBody, "application/json", "application/json",
                                        sharedPreferencesHelper.getKeyToken());
                                showProgressDialog();
                            } catch (Exception exception) {

                            }
                        }

                    }

                }
            }
        });
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProgressDialog() {
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.GONE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    @Override
    public void hideProgressDialog() {
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            String permission = permissions[i];

                            if (permission.equalsIgnoreCase(PermissionName.READ_EXTERNAL_STORAGE)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e("TAG", "READ_EXTERNAL_STORAGE permission denied");
                                    requestPermissions(
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e("TAG", "READ_EXTERNAL_STORAGE denied and don't ask for it again");
                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            this
                                            ,
                                            com.library.utilities.R.drawable.permission_ic_storage,
                                            "Storage Permission",
                                            "Kindly allow Storage Permission from Settings, without this permission the app is unable to provide upload image feature. Please turn on permissions at [Setting] -> [Permissions]",
                                            permission,
                                            permissionFromSettingActivityResultLauncher);
                                    return;
                                }
                            }

                            if (permission.equalsIgnoreCase(PermissionName.WRITE_EXTERNAL_STORAGE)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e("TAG", "WRITE_EXTERNAL_STORAGE permission denied");
                                    requestPermissions(
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e("TAG", "WRITE_EXTERNAL_STORAGE denied and don't ask for it again");
                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            this
                                            ,
                                            com.library.utilities.R.drawable.permission_ic_storage,
                                            "Storage Permission",
                                            "Kindly allow Storage Permission from Settings, without this permission the app is unable to provide upload image feature. Please turn on permissions at [Setting] -> [Permissions]",
                                            permission,
                                            permissionFromSettingActivityResultLauncher);
                                    return;
                                }
                            }

                            if (permission.equalsIgnoreCase(PermissionName.CAMERA)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e("TAG", "CAMERA permission denied");
                                    requestPermissions(
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e("TAG", "CAMERA permission denied and don't ask for it again");
                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            this
                                            ,
                                            com.library.utilities.R.drawable.permission_ic_camera,
                                            "Camera Permission",
                                            "Kindly allow Camera Permission from Settings, without this permission the app is unable to provide photo capture feature. Please turn on permissions at [Setting] -> [Permissions]",
                                            permission,
                                            permissionFromSettingActivityResultLauncher);
                                    return;
                                }
                            }
                        }
                    }
                    Log.e("TAG", "all permission granted, do the task");
                    showPictureDialog(this
                    );
                } else {
                    Log.e("TAG", "Unknown Error");
                }
                break;
            default:
                throw new RuntimeException("unhandled permissions request code: " + requestCode);
        }
    }

    private void showPictureDialog(Activity activity) {
        AlertDialog.Builder option = new AlertDialog.Builder(activity);
        option.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Click from Camera",
                "Cancel"
        };

        option.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent selectImageIntent = ImplicitIntentUtils.actionPickIntent(1);
                                imageFromGalleryActivityResultLauncher.launch(selectImageIntent);
                                break;
                            case 1:
                                String customDirectoryName = AppConstants.AppConfig.APP_NAME;
                                String fileName = MediaFileUtils.getRandomFileName(1);
                                String extension = "jpg";

                                Intent captureImageIntent = createUri(AddProductBrandsActivity.this, AddProductBrandsActivity.this, customDirectoryName, fileName, extension);
                                imageFromCameraActivityResultLauncher.launch(captureImageIntent);
                                break;
                            case 2:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
        option.show();
    }

    private Intent createUri(Context context, Activity activity, String customDirectoryName, String fileName, String extension) {
        if (StorageUtils.isExternalStorageAvailableAndWriteable()) {
            File directory = FileUtils.createDirectory(context, customDirectoryName);
            File mediaFile = FileUtils.createFile(directory, extension, fileName);
            assert mediaFile != null;
            realPath = mediaFile.getAbsolutePath();
            imageUri = FileProviderUtils.getFileUri(context, mediaFile, AppConstants.AppConfig.PACKAGE_NAME);
        }
        return ImageAndVideoUtils.cameraIntent(1, imageUri, activity);
    }

    ActivityResultLauncher<Intent> permissionFromSettingActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                        Log.e("mTag", "permission already granted");
                        String customDirectoryName = AppConstants.AppConfig.APP_NAME;
                        String fileName = MediaFileUtils.getRandomFileName(1);
                        String extension = "jpg";

                        Intent captureImageIntent = createUri(AddProductBrandsActivity.this, AddProductBrandsActivity.this, customDirectoryName, fileName, extension);
                        imageFromCameraActivityResultLauncher.launch(captureImageIntent);
                    } else {
                        Log.e("mTag", "permission is not granted, request for permission");
                        requestPermissions(
                                MULTIPLE_PERMISSIONS,
                                MULTIPLE_PERMISSION_REQUEST_CODE);
                    }
                }
            });

    ActivityResultLauncher<Intent> imageFromGalleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            imageUri = data.getData();
                            if (imageUri != null) {
                                //mViewBinding.profilePictureCircleImageView.setImageURI(imageUri);

                                realPath = RealPathUtils.getRealPath(AddProductBrandsActivity.this, imageUri);

                                File uncompressFile = new File(realPath);
                                System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                                System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                                File compressFile;
                                try {
                                    compressFile = Compressor.getDefault(AddProductBrandsActivity.this).compressToFile(uncompressFile);
                                    System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                                    System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                                    realPath = compressFile.getPath();

                                    MultipartBody.Part profilePic;
                                    if (realPath.equals("Path")) {
                                        profilePic = null;
                                    } else {
                                        profilePic = RequestUtils.createMultipartBody("filename", realPath);
                                    }

                                    viewModel.image(profilePic, sharedPreferencesHelper.getKeyToken());
                                    showProgressDialog();
                                    Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getAbsolutePath());
                                    if (bitmap != null) {
                                        viewBinding.productImageView.setImageBitmap(bitmap);
                                    }
                                } catch (CompressorFolderException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        //showShortToast("User cancelled image from gallery");
                    } else {
                        //showShortToast("Sorry! Failed to image from gallery");
                    }
                }
            });

    ActivityResultLauncher<Intent> imageFromCameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //mViewBinding.profilePictureCircleImageView.setImageURI(imageUri);

                        File uncompressFile = new File(realPath);
                        System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                        System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                        File compressFile;
                        try {
                            compressFile = Compressor.getDefault(AddProductBrandsActivity.this).compressToFile(uncompressFile);
                            System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                            System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                            realPath = compressFile.getPath();

                            MultipartBody.Part profilePic;
                            if (realPath.equals("Path")) {
                                profilePic = null;
                            } else {
                                profilePic = RequestUtils.createMultipartBody("filename", realPath);
                            }

                            viewModel.image(profilePic, sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();


                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));
                            FileUtils.deleteFile(uncompressFile);
                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));

                            Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getAbsolutePath());
                            if (bitmap != null) {
                                viewBinding.productImageView.setImageBitmap(bitmap);
                            }
                        } catch (CompressorFolderException e) {
                            e.printStackTrace();
                        }
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        //showShortToast("User cancelled image from camera");
                    } else {
                        //showShortToast("Sorry! Failed to image from camera");
                    }
                }
            });
}