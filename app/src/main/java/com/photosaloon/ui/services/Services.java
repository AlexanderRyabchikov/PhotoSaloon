package com.photosaloon.ui.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.photosaloon.R;
import com.photosaloon.config.AppPref;
import com.photosaloon.content.ServicesModel;
import com.photosaloon.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Services extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_services;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);

        adapter = new RecyclerViewAdapter(createListService());
        LinearLayoutManager manager = new LinearLayoutManager(Services.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }


    private List<ServicesModel> createListService() {

        final List<ServicesModel> services = new ArrayList<>();

        services.add(new ServicesModel("Прическа", 150));
        services.add(new ServicesModel("Костюм", 1050));
        services.add(new ServicesModel("Выезд", 500));
        services.add(new ServicesModel("Печать фото", 350));
        services.add(new ServicesModel("Тематический макияж", 1500));

        return services;

    }

    @OnClick(R.id.select_services)
    public void onClickServices() {

        String listServices = "";

        for (ServicesModel item : adapter.getSelectItems()) {

            listServices += item.getTypeServices() + "\n";

        }

        if (!listServices.equals("")) {

            Intent intent = new Intent();
            intent.putExtra(AppPref.SERVICES_RESULT, listServices);
            setResult(RESULT_OK, intent);
            finish();

        } else {
            showToast("Выберите услугу");
        }

    }
}
