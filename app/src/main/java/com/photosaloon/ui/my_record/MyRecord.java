package com.photosaloon.ui.my_record;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.photosaloon.R;
import com.photosaloon.ui.base.BaseListFragment;

import net.idik.lib.slimadapter.SlimAdapter;

import butterknife.BindView;

public class MyRecord extends BaseListFragment<SlimAdapter> {

    @NonNull
    private final SlimAdapter adapter = SlimAdapter.create();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_record;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @Override
    public int getEmptyText() {
        return R.string.my_record_list_empty;
    }
}
