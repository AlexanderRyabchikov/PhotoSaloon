package com.photosaloon.ui.my_record.Injectors;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.photosaloon.R;
import com.photosaloon.content.Services;
import com.photosaloon.model.Records;

import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

public class MyRecordInjector implements SlimInjector<Records> {

    @NonNull
    private final RecordListener listener;

    public MyRecordInjector(@NonNull RecordListener listener) {
        this.listener = listener;
    }

    @Override
    public void onInject(Records data, IViewInjector injector) {
        injector
                .clicked(R.id.root, view -> listener.onClick(data.id))
                .clicked(R.id.delete_button, view -> listener.onDelete(data))
                .text(R.id.master_name, data.nameMaster)
                .text(R.id.date_record, data.date)
                .text(R.id.time_record, data.time)
                .with(R.id.list_services, view -> {

                    String listServices = "";

                    for (Services item : data.typeServices) {
                        listServices += item.typeServices + "\n";
                    }

                    ((TextView)view).setText(listServices);

                });
    }

    public interface RecordListener {
        void onClick(long id);
        void onDelete(Records item);
    }
}
