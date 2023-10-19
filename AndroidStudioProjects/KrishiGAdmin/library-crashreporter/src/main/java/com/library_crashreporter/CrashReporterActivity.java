package com.library_crashreporter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class CrashReporterActivity extends AppCompatActivity {

    public static final String TAG = CrashReporterActivity.class.getSimpleName();

    public static final String KEY_THREAD = "thread";
    public static final String KEY_EXCEPTION = "exception";

    private String errorString;

    private TextView infoTextView, exceptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_reporter);
        initializeView();
        initializeObject();
        setOnClickListener();
    }

    private void initializeView() {
        infoTextView        = findViewById(R.id.infoTextView);
        exceptionTextView   = findViewById(R.id.exceptionTextView);
    }

    private void initializeObject() {
        String threadNameAndId = getIntent().getStringExtra(KEY_THREAD);
        String info = com.library_crashreporter.CrashUtils.printSystemInfo(threadNameAndId, com.library_crashreporter.CrashHandler.getInstance().getApplicationContext());
        infoTextView.setText(info);

        Throwable exception = (Throwable) getIntent().getSerializableExtra(KEY_EXCEPTION);
        String stacktrace = com.library_crashreporter.CrashUtils.getStackTrace(exception);
        SpannableStringBuilder spannableStringBuilder = com.library_crashreporter.CrashUtils.getCrashInfo(exception, getPackageName());
        exceptionTextView.setText(spannableStringBuilder);

        errorString = info +"\n"+ spannableStringBuilder;
    }

    private void setOnClickListener() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crash_menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_copy) {
            copy(errorString);
        }
        else if (itemId == R.id.action_whatsapp_send) {
            whatsAppSend(CrashReporterActivity.this, errorString);
        }
        else if (itemId == R.id.action_gmail_send) {
            gmailSend(CrashReporterActivity.this, errorString, null);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void whatsAppSend(Activity activity, String shareText) {
        Intent whatsAppIntent = new Intent(Intent.ACTION_SEND);
        whatsAppIntent.setType("text/plain");
        whatsAppIntent.setPackage("com.whatsapp");
        whatsAppIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        try {
            activity.startActivity(whatsAppIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
        }
    }

    private void gmailSend(Activity activity, String body, String filePath) {
        Intent gmailIntent = new Intent(Intent.ACTION_SEND);

        gmailIntent.setType("plain/text");
        gmailIntent.setPackage("com.google.android.gm");
        gmailIntent.putExtra(Intent.EXTRA_EMAIL,new String[] {"rohit24mobile@gmail.com"});

        String subject  = "Error Description";
        gmailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        gmailIntent.putExtra(Intent.EXTRA_TEXT, body);

        if (filePath != null)
        {
            gmailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)) );
        }

        gmailIntent.setType("message/rfc822");

        try {
            activity.startActivity(gmailIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.gm")));
        }
    }

    private void copy(String textToClip) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(textToClip);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text label",textToClip);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
    }
}