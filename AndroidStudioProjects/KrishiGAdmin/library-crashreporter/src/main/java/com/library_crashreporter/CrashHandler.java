package com.library_crashreporter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {

    private Context applicationContext;

    /* System default exception handling (by default, the system will terminate the current exception program) */
    private static UncaughtExceptionHandler defaultCrashHandler;

    private CrashHandler() {
    }

    @SuppressLint("StaticFieldLeak")
    private static CrashHandler instance;

    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        /* Get the default exception handler of the system */
        defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();

        /* Set the current instance as the system default exception handler */
        Thread.setDefaultUncaughtExceptionHandler(this);

        /* Get Context for internal use */
        applicationContext = context.getApplicationContext();
    }

    /**
     * This is the most critical function. When there is an uncaught exception in the program, the
     * system will automatically call the #uncaughtException method thread is the thread that has
     * the uncaught exception, and ex is the uncaught exception. With this ex, we can get the
     * exception information.
     */
    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        Intent intent = new Intent(applicationContext, CrashReporterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(CrashReporterActivity.KEY_THREAD, thread.getName() + "(" + thread.getId() + ")");
        intent.putExtra(CrashReporterActivity.KEY_EXCEPTION, exception);
        applicationContext.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
}