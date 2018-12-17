package com.photosaloon.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.photosaloon.R;

import net.idik.lib.slimadapter.SlimAdapter;

import butterknife.BindView;

public abstract class BaseListFragment<T extends SlimAdapter> extends BaseEmptyFragment {

    protected SlimAdapter adapter = SlimAdapter.create();

    // This fields are marked as public for ButterKnife normal binding
    @BindView(R.id.recycler_view) public RecyclerView recyclerView;

    @Override
    protected void updateEmptyView() {
        emptyLayout.setVisibility(adapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }
}
