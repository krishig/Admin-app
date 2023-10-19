package com.krishigadmin.android.Basics.UtilityTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.krishigadmin.android.Basics.Database.UserDataHelper;
import com.krishigadmin.android.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Utils {

     /* public static UserData GetSession() {
          return UserDataHelper.getInstance().getList().get(0);
      }

      public static boolean IS_LOGIN() {
          return UserDataHelper.getInstance().getList().size() > 0;
      }
      */
    public static void I(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
    }


    public static void UnAuthorizationToken(Context cx) {
        UserDataHelper.getInstance().deleteAll();
      //  I_clear(cx, LoginActivity.class, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
/*
    public static void InternetDialog(Context context) {
        Dialog dialog = new Dialog(context,android.R.style.Theme_DeviceDefault_Dialog_Alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.alertdialog);
        dialog.findViewById(R.id.tvPermittManually).setOnClickListener(view -> {
            if (AppController.getInstance().isOnline()) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
*/

/*
    public static Dialog logoutAlertDialog(Context c) {
        Dialog dialog = new Dialog(c, android.R.style.Theme_DeviceDefault_Dialog_Alert);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout);
        dialog.findViewById(R.id.tvOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.UnAuthorizationToken(c);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
*/

/*
    public static void Picasso(String Url, ImageView imageView, int dummy) {
        Picasso.get().load(Const.Url.HOST_URL + Url).fetch(new Callback() {
            @Override
            public void onSuccess() {
                if (dummy == 0)
                    Picasso.get()
                            .load(Const.Url.HOST_URL + Url)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .into(imageView);
                else
                    Picasso.get()
                            .load(Const.Url.HOST_URL + Url)
                            .error(dummy)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .into(imageView);
            }

            @Override
            public void onError(Exception e) {
                if (dummy == 0)
                    Picasso.get()
                            .load(Const.Url.HOST_URL + Url)
                            .into(imageView);
                else
                    Picasso.get()
                            .load(Const.Url.HOST_URL + Url)
                            .error(dummy)
                            .into(imageView);
            }
        });
    }
*/

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

  /*  public static void UnAuthorizationToken(Context cx) {
        UserDataHelper.getInstance().deleteAll();
        I_clear(cx, WelcomeActivity.class, null);
    }*/

  /*  public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        Utils.E("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    @NonNull
    public static String prettyCount(@NonNull Number number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            Utils.E("::prettyCount::" + (numValue / Math.pow(10, base * 3)));
            String i = "" + (numValue / Math.pow(10, base * 3));
            String[] values = i.split("\\.");
            if ((values[1].equals("0"))) {
                return values[0] + suffix[base];
            } else {
                return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
            }

        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }*/

    @NonNull
    public static <T> List<T> removeDuplicates(@NonNull List<T> list) {

        // Create a new ArrayList
        List<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

   /* public static void UnAuthorizationToken(Context cx) {
        UserDataHelper.getInstance().deleteAll();
        MyContentProvider.deleteAll();
        I_clear(cx, LoginActivity.class, null);
    }*/

    /**
     * Change the status bar Color of the Activity to the Desired Color.
     *
     * @param activity - Activity
     * @param color    - Desired Color
     */
    public static void changeStatusBarColor(@NonNull Activity activity, int color) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(activity, color));
    }

/*
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
//getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

//Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Utils.E("Package Name=" + context.getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

// String key = new String(Base64.encodeBytes(md.digest()));
                Utils.E("Key Hash=" + key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Utils.E("Name not found" + e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Utils.E("No such an algorithm" + e.toString());
        } catch (Exception e) {
            Utils.E("Exception" + e.toString());
        }

        return key;
    }
*/

/*
    public static CurrentCountryModel getDefaultCountryCode(Context context) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.createInstance(context);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
        Locale locale = new Locale("en", countryCodeValue);
        String countryName = locale.getDisplayCountry();
        return new CurrentCountryModel(countryCodeValue.toUpperCase(),"+"+phoneNumberUtil.getCountryCodeForRegion(countryCodeValue.toUpperCase()),countryName);
    }
*/

    public static void I_finish(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
    }

    public static void I_clear(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
    }

  /*  public static void E(String msg) {
        if (Const.Url.Development.equals(Constants.Key.Debug))
            Log.e("Log.E", msg);
    }*/
    public static int DetectUIMode(Activity activity) {
        return activity.getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
    }

    public static String getFormattedDate(long smsTimeInMilis, Context context) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEE, MMM d | h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return context.getString(R.string.today) + " " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return context.getString(R.string.yesterday) + " " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy | h:mm aa", smsTime).toString();
        }
    }



    public static String getDateAfterYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, 1);
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(cal.getTime().getTime());
        return DateFormat.format("dd'" + getOrdinal(smsTime.get(Calendar.DAY_OF_MONTH)) + "' MMM yyyy", smsTime).toString();

    }

    public static String getOrdinal(int day) {
        String ordinal;
        switch (day % 20) {
            case 1:
                ordinal = "st";
                break;
            case 2:
                ordinal = "nd";
                break;
            case 3:
                ordinal = "rd";
                break;
            default:
                ordinal = day > 30 ? "st" : "th";
        }
        return ordinal;
    }

/*
    public static Dialog initProgressDialog(Context c) {
        Dialog dialog = new Dialog(c);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }
*/

    public static void T(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    public static void share(@NonNull Context c, String subject, String shareBody) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        c.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void T_Long(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }


    /*public static void setLanguage(String language, @NonNull Context context) {
        SavedData.saveLanguage(language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
        LocaleHelper.setLocale(context, language);
    }*/

    public static void alert(Context activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.app_name));
        builder.setMessage(message)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alertdialog = builder.create();
        alertdialog.show();
    }

}