package com.photosaloon.ui.base;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public abstract class BaseTabsUpdatingAdapter<T extends BaseFragment & Updating> extends BaseTabsAdapter<T> {

    public BaseTabsUpdatingAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    public void updateFragmentsData() {
        Log.e("UPDATE", "ADAPTER");
        for (int i = 0; i < registeredFragments.size(); i++) {
            int key = registeredFragments.keyAt(i);
            Updating fragment = registeredFragments.get(key);
            if (fragment == null) {
                continue;
            }

            fragment.update();
        }
    }
}
