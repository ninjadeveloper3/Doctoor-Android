package com.Doctoor.app.navigation;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class BaseFragmentManager {

    protected final FragmentManager fragmentManager;

    public BaseFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public void onShowHideFragment(@NonNull Fragment toShow, @Nullable Fragment toHide) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (toHide != null) {
            ft.hide(toHide);
        }
        ft.show(toShow)
                .commit();
        if (toHide != null) {
            toHide.onHiddenChanged(true);
        }
        toShow.onHiddenChanged(false);
    }


    public Fragment findFragmentByTag(String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

}
