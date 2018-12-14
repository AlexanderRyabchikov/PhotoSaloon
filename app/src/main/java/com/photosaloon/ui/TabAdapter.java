package com.photosaloon.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.photosaloon.R;
import com.photosaloon.ui.base.BaseTabsUpdatingAdapter;
import com.photosaloon.ui.my_record.MyRecord;
import com.photosaloon.ui.profile.Profile;
import com.photosaloon.ui.record.Record;

public class TabAdapter extends BaseTabsUpdatingAdapter {

    public static final int PAGE_NEW_RECORD = 1;
    public static final int PAGE_MY_RECORD = 0;
    public static final int PAGE_PROFILE = 2;

    private static final int PAGE_COUNT = 3;

    public TabAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case PAGE_MY_RECORD:

                MyRecord myRecord = new MyRecord();

                registerFragment(position, myRecord);

                return myRecord;

            case PAGE_NEW_RECORD:

                Record record = new Record();

                registerFragment(position, record);

                return record;

            case PAGE_PROFILE:

                Profile profile = new Profile();

                registerFragment(position, profile);

                return profile;
        }

        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PAGE_MY_RECORD:
                return context.getString(R.string.title_toolbar_my_record);
            case PAGE_NEW_RECORD:
                return context.getString(R.string.title_toolbar_record);
            case PAGE_PROFILE:
                return context.getString(R.string.title_toolbar_profile);
        }
        return null;
    }
}
