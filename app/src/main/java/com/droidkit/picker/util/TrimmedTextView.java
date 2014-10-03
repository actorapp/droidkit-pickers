package com.droidkit.picker.util;
import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class TrimmedTextView extends TextView {
    public static enum EllipsizeRange {
        ELLIPSIS_AT_START, ELLIPSIS_AT_END;
    }

    private CharSequence originalText;
    private SpannableStringBuilder builder = new SpannableStringBuilder();

    /**
     * This allows the cached value of the original unmodified text to be
     * invalidated whenever set externally.
     */
    private final TextWatcher textCacheInvalidator = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            originalText = null;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public TrimmedTextView(Context context) {
        this(context, null, 0);
    }

    public TrimmedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrimmedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addTextChangedListener(textCacheInvalidator);
    }

    /**
     * Make sure we return the original unmodified text value if it's been
     * custom-ellipsized by us.
     */
    public CharSequence getText() {
        if (originalText == null) {
            return super.getText();
        }
        return originalText;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        long startTime = System.currentTimeMillis();
        Layout layout = getLayout();
        CharSequence text = layout.getText();
        if (text instanceof Spanned) {
            Spanned spanned = (Spanned) text;
            int ellipsisStart;
            int ellipsisEnd;
            TruncateAt where = null;
            ellipsisStart = spanned.getSpanStart(EllipsizeRange.ELLIPSIS_AT_START);
            if (ellipsisStart >= 0) {
                where = TruncateAt.START;
                ellipsisEnd = spanned.getSpanEnd(EllipsizeRange.ELLIPSIS_AT_START);
            } else {
                ellipsisStart = spanned.getSpanStart(EllipsizeRange.ELLIPSIS_AT_END);
                if (ellipsisStart >= 0) {
                    where = TruncateAt.END;
                    ellipsisEnd = spanned.getSpanEnd(EllipsizeRange.ELLIPSIS_AT_END);
                } else {
                    // No EllipsisRange spans in this text
                    return;
                }
            }


            builder.clear();
            builder.append(text, 0, ellipsisStart).append(text, ellipsisEnd, text.length());
            float consumed = Layout.getDesiredWidth(builder, layout.getPaint());
            float width = getMeasuredWidth();
            CharSequence ellipsisText = text.subSequence(ellipsisStart, ellipsisEnd);
            CharSequence ellipsizedText = TextUtils.ellipsize(ellipsisText, layout.getPaint(),
                    width - consumed, where);
            if (ellipsizedText.length() < ellipsisText.length()) {

                Log.d("Picker", "Measurment "+ ellipsizedText.length() +" "+ ellipsisText.length());
                builder.clear();
                builder.append(text, 0, ellipsisStart).append(ellipsizedText)
                        .append(text, ellipsisEnd, text.length());
                setText(builder);
                originalText = text;
                requestLayout();
                invalidate();
            }
        }
        Log.d("Picker", "Time to measure: "+ (System.currentTimeMillis() - startTime));
    }
}