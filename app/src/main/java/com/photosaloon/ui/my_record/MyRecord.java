package com.photosaloon.ui.my_record;

import android.app.AlertDialog;
import android.os.Bundle;
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
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyRecord extends BaseListFragment<SlimAdapter> {

    private MyRecordInjector.RecordListener listener = new MyRecordInjector.RecordListener() {
        @Override
        public void onClick(long id) {
            //TODO надо ли открывать решим просмотра?
        }

        @Override
        public void onDelete(Records item) {

            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("Внимание")
                    .setMessage("Вы уверены что хотите удалить запись?")
                    .setPositiveButton("Да", ((dialogInterface, i) -> {


                            Completable.fromAction(() -> App
                                        .getDataBase()
                                        .daoRecords()
                                        .delete(item))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(()-> showToastLongTime("Запись успешно удалена"));

                        dialogInterface.dismiss();
                    }))
                    .setNegativeButton("Нет", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));

        }
    };

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

        compositeSubscription.add(
                App
                .getDataBase()
                .daoRecords()
                .getAllRecords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
