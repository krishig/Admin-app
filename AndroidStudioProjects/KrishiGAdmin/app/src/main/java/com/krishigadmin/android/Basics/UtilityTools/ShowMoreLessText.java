package com.krishigadmin.android.Basics.UtilityTools;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class ShowMoreLessText {
    TextView tv;
    int maxLine;
    String expandText;
    String collapseText;
    String ColorText;
    boolean viewMore;
    boolean isUnderLine;

    public ShowMoreLessText(TextView tv,
                            int maxLine,
                            String expandText,
                            String collapseText,
                            String Color,
                            boolean viewMore, boolean isUnderLine) {
        this.tv = tv;
        this.maxLine = maxLine;
        this.expandText = expandText;
        this.collapseText = collapseText;
        this.ColorText = Color;
        this.viewMore = viewMore;
        this.isUnderLine = isUnderLine;
        if (countWords(tv.getText().toString()) >= 30) {
            makeTextViewResizable(tv, maxLine, expandText, viewMore);
        }

    }

    private int countWords(String s) {
        String trim = s.trim();
        if (trim.isEmpty())
            return 0;
        return trim.split("\\s+").length; // separate string around spaces
    }


    public void makeTextViewResizable(TextView tv,
                                      int maxLine,
                                      String expandText,
                                      boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);

                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(addClickablePartTextViewResizable(new SpannableString(tv.getText().toString()),
                        tv, expandText,
                        viewMore), TextView.BufferType.SPANNABLE);
            }
        });
    }

    private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned,
                                                                     final TextView tv,
                                                                     final String spanableText,
                                                                     final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);
        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, collapseText, false);
                    } else {
                        makeTextViewResizable(tv, maxLine, expandText, true);
                    }

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(isUnderLine);
                    ds.setColor(Color.parseColor(ColorText));

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;
    }

}
