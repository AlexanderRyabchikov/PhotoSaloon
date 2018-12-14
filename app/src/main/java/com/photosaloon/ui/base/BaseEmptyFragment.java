package com.photosaloon.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.photosaloon.R;

import butterknife.BindView;

public abstract class BaseEmptyFragment extends BaseFragment implements EmptyLayout {
    @BindView(R.id.empty_layout) public View emptyLayout;
    @BindView(R.id.empty_text) public TextView emptyText;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        emptyText.setText(getEmptyText());
    }

    protected abstract void updateEmptyView();
}
