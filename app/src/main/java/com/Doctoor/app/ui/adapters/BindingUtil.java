package com.Doctoor.app.ui.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class BindingUtil {
    @BindingAdapter({"setUpWithViewpager"})
    public static void setUpWithViewpager(final TabLayout tabLayout, ViewPager viewPager) {
        viewPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager,
                                         @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
                if (oldAdapter == null && newAdapter == null) {
                    return;
                }
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}
