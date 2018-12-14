package com.photosaloon.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public abstract class BaseTabsAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    protected SparseArray<T> registeredFragments;
    protected Context context;

    public BaseTabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        registeredFragments = new SparseArray<>();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        registeredFragments.remove(position);
    }

    protected void registerFragment(int key, T fragment) {
        registeredFragments.put(key, fragment);
    }

    protected T getFragmentAtPosition(int position) {
        if (position < 0 || position > registeredFragments.size() - 1) {
            return null;
        }

        return registeredFragments.get(position);
    }
}

