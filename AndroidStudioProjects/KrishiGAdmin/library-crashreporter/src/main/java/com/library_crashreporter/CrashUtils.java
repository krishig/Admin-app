package com.library_crashreporter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Set;

public class CrashUtils {

    /**
     * Get the App package name
     *
     * @return App package name
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * Get the version number
     *
     * @return The version number of the current application
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get the version number
     *
     * @return The version name of the current application
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "Version unknown";
        }
    }

    public static String printSystemInfo(String threadNameAndId, Context context) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a");
        String time = dateFormat.format(date);

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Crash Time : ").append(time);
        stringBuilder.append("\n\n");

        stringBuilder.append("Application Information : ");
        stringBuilder.append("\n\n");

        stringBuilder.append("Package : ").append(getAppPackageName(context));
        stringBuilder.append("\nVersionCode : ").append(getVersionCode(context));
        stringBuilder.append("\nVersionName : ").append(getVersionName(context));

        stringBuilder.append("\n\n");
        stringBuilder.append("Device Information : ");
        stringBuilder.append("\n\n");

        stringBuilder.append("Manufacturer : ").append(Build.MANUFACTURER);
        stringBuilder.append("\nModel : ").append(Build.MODEL);
        stringBuilder.append("\nDevice : ").append(Build.DEVICE);
        stringBuilder.append("\nAndroid Version : ").append(Build.VERSION.RELEASE);
        stringBuilder.append("\nSDK : ").append(Build.VERSION.SDK_INT);
        stringBuilder.append("\n\n");

        stringBuilder.append("Thread Information : ");
        stringBuilder.append("\n\n");
        stringBuilder.append("Thread Name & Id : ").append(threadNameAndId);
        stringBuilder.append("\n\n");
        stringBuilder.append("Exception Information : ");
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    /**
     * Get the stack trace full string formate
     *
     * @param exception
     * @return string
     */
    public static String getStackTrace(Throwable exception){
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        exception.printStackTrace(printWriter);
        printWriter.close();
        return writer.toString();
    }

    protected String getStackTraceFromAt(Throwable ex) {
        String stackTrace = toStackTraceString(ex.getStackTrace());

        Throwable cause = ex.getCause();
        if (cause != null) {
            stackTrace += "Caused by: " + cause.getClass().getName() + ": " + cause.getMessage() + "\n";
            stackTrace += toStackTraceString(cause.getStackTrace());
        }

        return stackTrace;
    }

    protected String toStackTraceString(StackTraceElement[] stackTraceElements) {
        String stackTrace = "";
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            stackTrace += "\tat " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() +
                    "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")\n";
        }
        return stackTrace;
    }

    /**
     * Get the exception class name
     *
     * @param exception
     * @return exception class name
     */
    public static String getCause(Throwable exception) {
        if (exception == null) {
            return "Unknown Exception";
        }
        while (exception.getCause() != null) {
            exception = exception.getCause();
        }
        return exception.getClass().getName();
    }

    /**
     * Get exception description
     *
     * @param exception
     * @return exception description
     */
    public static String getCauseDesc(Throwable exception) {
        if (exception == null) {
            return "Unknown Exception";
        }
        while (exception.getCause() != null) {
            exception = exception.getCause();
        }
        return exception.getMessage();
    }

    /**
     * Crash information
     *
     * @param throwable exception
     * @return crash information
     */
    static SpannableStringBuilder getCrashInfo(Throwable throwable, String highlightContent) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        Set<Throwable> set = Collections.newSetFromMap(new IdentityHashMap<Throwable, Boolean>());
        set.add(throwable);

        builder.append(highlightSpan(throwable.toString())).append("\n");

        /* Print our stack trace */
        StackTraceElement[] trace = throwable.getStackTrace();
        for (StackTraceElement traceElement : trace) {
            if (traceElement.toString().contains(highlightContent)) {
                builder.append("\tat ");
                printStackTraceElement(traceElement, builder);
                builder.append("\n");
            } else {
                builder.append("\tat ").append(traceElement.toString()).append("\n");
            }
        }

        /* Print suppressed exceptions, if any */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            for (Throwable se : throwable.getSuppressed())
                printEnclosedStackTrace(se, builder, trace, "Suppressed: ", "\t", set, highlightContent);
        }

        /* Print cause, if any */
        Throwable ourCause = throwable.getCause();
        if (ourCause != null) {
            printEnclosedStackTrace(ourCause, builder, trace, "Caused by: ", "", set, highlightContent);
        }
        return builder;
    }

    private static void printEnclosedStackTrace(Throwable throwable,
                                                SpannableStringBuilder builder,
                                                StackTraceElement[] enclosingTrace,
                                                String caption,
                                                String prefix,
                                                Set<Throwable> set,
                                                String highlightPackage) {
        if (set.contains(throwable)) {
            builder.append("\t[CIRCULAR REFERENCE:").append(throwable.toString()).append("]\n");
        } else {
            set.add(throwable);
            /* Compute number of frames in common between this and enclosing trace */
            StackTraceElement[] trace = throwable.getStackTrace();
            int m = trace.length - 1;
            int n = enclosingTrace.length - 1;
            while (m >= 0 && n >= 0 && trace[m].equals(enclosingTrace[n])) {
                m--;
                n--;
            }
            int framesInCommon = trace.length - 1 - m;

            /* Print our stack trace */
            builder.append(prefix).append(caption).append(throwable.toString()).append("\n");
            for (int i = 0; i <= m; i++) {
                if (trace[i].toString().contains(highlightPackage)) {
                    builder.append("\tat ");
                    printStackTraceElement(trace[i], builder);
                    builder.append("\n");
                } else {
                    builder.append(prefix).append("\tat ").append(trace[i].toString()).append("\n");
                }
            }
            if (framesInCommon != 0) {
                builder.append(prefix).append("\t... ").append(String.valueOf(framesInCommon)).append(" more\n");
            }

            /* Print suppressed exceptions, if any */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (Throwable se : throwable.getSuppressed()) {
                    printEnclosedStackTrace(se, builder, trace, "Suppressed: ", "\t", set, highlightPackage);
                }
            }

            /* Print cause, if any */
            Throwable ourCause = throwable.getCause();
            if (ourCause != null) {
                printEnclosedStackTrace(ourCause, builder, trace, "Caused by: ", prefix, set, highlightPackage);
            }
        }
    }

    /**
     * Output stack element information
     *
     * @param traceElement stack element
     * @param builder      crash information
     */
    private static void printStackTraceElement(StackTraceElement traceElement, SpannableStringBuilder builder) {
        /*
         * Android-changed: When ART cannot find a line number, the lineNumber field is set to the
         * dex_pc and the fileName field is set to null.
         */
        builder.append(traceElement.getClassName()).append(".").append(traceElement.getMethodName());
        if (traceElement.isNativeMethod()) {
            builder.append("(Native Method)");
        } else if (traceElement.getFileName() != null) {
            if (traceElement.getLineNumber() >= 0) {
                builder.append("(")
                        .append(highlightSpan(traceElement.getFileName()))
                        .append(highlightSpan(":"))
                        .append(highlightSpan(String.valueOf(traceElement.getLineNumber())))
                        .append(")");
            } else {
                builder.append("(").append(traceElement.getFileName()).append(")");
            }
        } else {
            if (traceElement.getLineNumber() >= 0) {
                /* The line number is actually the dex pc. */
                builder.append("(Unknown Source:").append(String.valueOf(traceElement.getLineNumber())).append(")");
            } else {
                builder.append("(Unknown Source)");
            }
        }
    }

    /**
     * Highlight content
     *
     * @param content content
     * @return SpannableString object
     */
    public static SpannableString highlightSpan(String content) {
        SpannableString highlightSpan = new SpannableString(content);
        ForegroundColorSpan textColorSpan = new ForegroundColorSpan(0xFFFF0000);
        TypefaceSpan typefaceSpan = new TypefaceSpan("monospace");
        highlightSpan.setSpan(textColorSpan, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        highlightSpan.setSpan(typefaceSpan, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return highlightSpan;
    }
}
