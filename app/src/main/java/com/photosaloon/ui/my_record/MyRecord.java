package com.photosaloon.ui.my_record;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.photosaloon.R;
import com.photosaloon.config.App;
import com.photosaloon.model.Records;
import com.photosaloon.ui.base.BaseListFragment;
import com.photosaloon.ui.my_record.Injectors.MyRecordInjector;

import net.idik.lib.slimadapter.SlimAdapter;

import butterknife.BindView;
import io.reactivex.schedulers.Schedulers;
import rx.Completable;
import rx.android.schedulers.AndroidSchedulers;

public class MyRecord extends BaseListFragment<SlimAdapter> {

    private MyRecordInjector.RecordListener listener = new MyRecordInjector.RecordListener() {
        @Override
        public void onClick(long id) {
            //TODO надо ли открывать решим просмотра?
        }

        @Override
        public void onDelete(Records item) {

            new AlertDialog.Builder(getContext())
                    .setTitle("Внимание")
                    .setMessage("Вы уверены что хотите удалить запись?")
                    .setPositiveButton("Да", ((dialogInterface, i) -> {

                        Completable
                                .fromAction(() -> App
                                        .getDataBase()
                                        .daoRecords()
                                        .delete(item))
                                .observeOn(AndroidSchedulers.mainThread());

                        dialogInterface.dismiss();
                    }))
                    .setNegativeButton("Нет", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create()
                    .show();

        }
    };

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
        loadRecords();
    }

    private void loadRecords() {

        Completable.fromAction(() -> App
                .getDataBase()
                .daoRecords()
                .getAllRecords()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (adapter.getItemCount() == 0) {
                        showLoading();
                    }
                })
                .doOnEach(listNotification -> hideLoading())
                .subscribe(records -> {
                    adapter.updateData(records);
                    updateEmptyView();
                })
        );

    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter
                .register(R.layout.item_my_order, new MyRecordInjector(listener))
                .attachTo(recyclerView);

    }

    @Override
    public int getEmptyText() {
        return R.string.my_record_list_empty;
    }

}
