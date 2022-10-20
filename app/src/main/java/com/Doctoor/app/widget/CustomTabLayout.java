package com.Doctoor.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.Doctoor.app.R;
import com.Doctoor.app.utils.font.TypefaceUtil;
import com.Doctoor.app.utils.font.TypefacesFont;

import static com.Doctoor.app.utils.font.TypefacesFont.MARK_PRO_MEDIUM;
import static com.Doctoor.app.utils.font.TypefacesFont.from;


public class CustomTabLayout extends TabLayout {
    private Typeface typeface;

    public CustomTabLayout(Context context) {
        this(context, null);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.textview_typeface, 0, 0);
            try {
                if (a.hasValue(R.styleable.textview_typeface_textFont)) {

                    Integer position = a.getInteger(R.styleable.textview_typeface_textFont, MARK_PRO_MEDIUM.ordinal());
                    typeface = TypefaceUtil.getTypeface(context, from(position));
                } else {
                    typeface = TypefaceUtil.getTypeface(context, TypefacesFont.MARK_PRO_MEDIUM);
                }

            } finally {
                a.recycle();
            }
        } else {
            typeface = TypefaceUtil.getTypeface(context, MARK_PRO_MEDIUM);
        }
    }

    @Override
    public void addTab(@NonNull Tab tab) {
        super.addTab(tab);
        ViewGroup mainView = (ViewGroup) getChildAt(0);
        LinearLayout tabView = (LinearLayout) mainView.getChildAt(tab.getPosition());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tabView.getLayoutParams();
        if (tab.getPosition() == 2) {
            layoutParams.width = 0;
            layoutParams.weight = 1f;
        } else {
            layoutParams.width = 0;
            layoutParams.weight = 0.5f;
        }
        tabView.setLayoutParams(layoutParams);
        tabView.setMeasureWithLargestChildEnabled(true);
        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                ((TextView) tabViewChild).setTypeface(typeface, Typeface.NORMAL);
            }
        }
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        this.removeAllTabs();

        ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);
        if (viewPager.getAdapter() != null) {
            PagerAdapter adapter = viewPager.getAdapter();
            for (int i = 0, count = adapter.getCount(); i < count; i++) {

                Tab tab = this.newTab();
                tab.setTag(i == 2 ? "Long" : null);
                this.addTab(tab.setText(adapter.getPageTitle(i)));
                LinearLayout tabView = (LinearLayout) slidingTabStrip.getChildAt(i);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                if (i == 2) {
                    layoutParams.width = 0;
                    layoutParams.weight = 1f;
                }
                tabView.setLayoutParams(layoutParams);
                AppCompatTextView view = (AppCompatTextView) tabView.getChildAt(1);

                view.setTypeface(typeface, Typeface.NORMAL);
                view.setAllCaps(false);

            }
        }
    }
}
