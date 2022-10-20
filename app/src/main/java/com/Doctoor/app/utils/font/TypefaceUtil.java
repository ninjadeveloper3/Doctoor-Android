package com.Doctoor.app.utils.font;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.AttributeSet;
import android.util.LruCache;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.Doctoor.app.DoctoorApp;
import com.Doctoor.app.R;

import static com.Doctoor.app.utils.font.TypefacesFont.MARK_PRO_RAGULAR;
import static com.Doctoor.app.utils.font.TypefacesFont.from;

public class TypefaceUtil {

    //region Variable
    private static LruCache<String, Typeface> sTypefaceCache = new LruCache<String, Typeface>(6);
    //endregion

    public static void apply(TypefaceId id, TextView tv) {
        if (tv == null || tv.isInEditMode()) {
            return;
        }
        tv.setTypeface(getTypeface(id));
    }

    public static Typeface getTypeface(TypefaceId id) {
        Typeface typeface = sTypefaceCache.get(id.toString());
        if (typeface == null) {
            typeface = ResourcesCompat.getFont(DoctoorApp.Companion.getInstance(), id.getFont());
            sTypefaceCache.put(id.toString(), typeface);
        }
        return typeface;
    }

    public static void initView(final Context context, AttributeSet attrs, TextView textView) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.textview_typeface, 0, 0);
            try {
                if (a.hasValue(R.styleable.textview_typeface_textFont)) {

                    int position = a.getInteger(R.styleable.textview_typeface_textFont, MARK_PRO_RAGULAR.ordinal());
                    textView.setTypeface(TypefaceUtil.getTypeface(context, from(position)));
                } else {
                    textView.setTypeface(TypefaceUtil.getTypeface(context, MARK_PRO_RAGULAR));
                }

            } finally {
                a.recycle();
            }
        } else {
            textView.setTypeface(TypefaceUtil.getTypeface(context, MARK_PRO_RAGULAR));
        }
    }


    public static Typeface getTypeface(final Context context, TypefaceId id) {
        Typeface typeface = sTypefaceCache.get(id.toString());
        if (typeface == null) {
            typeface = ResourcesCompat.getFont(context, id.getFont());
            sTypefaceCache.put(id.toString(), typeface);
        }
        return typeface;
    }

    public static SpannableString getStyledText(String title, TypefaceId id) {
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(id), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    public interface TypefaceId {
        Typeface get();

        int getFont();
    }

    private static class TypefaceSpan extends MetricAffectingSpan {

        private Typeface mTypeface;

        public TypefaceSpan(TypefaceId id) {
            mTypeface = getTypeface(id);
        }

        @Override
        public void updateMeasureState(TextPaint p) {
            p.setTypeface(mTypeface);
            p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            tp.setTypeface(mTypeface);
            tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }
}