package com.library.utilities.permissionutils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class PermissionDialog {

    private PermissionDialog() {
        throw new UnsupportedOperationException("Should not create instance of PermissionDialog class. Please use as static..");
    }

    public static void permissionDeniedWithNeverAskAgain(final Activity activity, int permissionIcon, String permissionName, String neverAskAgainMessage, final String permission, final ActivityResultLauncher<Intent> intentActivityResultLauncher) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setIcon(permissionIcon);
        alertDialogBuilder.setTitle(permissionName);
        alertDialogBuilder.setMessage(neverAskAgainMessage);

        alertDialogBuilder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = PermissionSettingIntent.getIntent(activity, permission);
                intentActivityResultLauncher.launch(intent);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static void permissionDeniedWithNeverAskAgain(final Activity activity, final Fragment fragment, int permissionIcon, String permissionName, String neverAskAgainMessage, final String permission, final ActivityResultLauncher<Intent> intentActivityResultLauncher) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setIcon(permissionIcon);
        alertDialogBuilder.setTitle(permissionName);
        alertDialogBuilder.setMessage(neverAskAgainMessage);

        alertDialogBuilder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = PermissionSettingIntent.getIntent(activity, permission);
                intentActivityResultLauncher.launch(intent);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}