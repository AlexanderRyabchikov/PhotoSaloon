package com.photosaloon.ui.record;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseUser;
import com.photosaloon.R;
import com.photosaloon.config.App;
import com.photosaloon.config.AppDataBase;
import com.photosaloon.config.AppPref;
import com.photosaloon.model.Client;
import com.photosaloon.model.Records;
import com.photosaloon.ui.TabAdapter;
import com.photosaloon.ui.base.BaseFragment;
import com.photosaloon.ui.my_record.MyRecord;
import com.photosaloon.ui.services.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Record extends BaseFragment {

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_avatar)
    ImageView avatarView;

    @BindView(R.id.user_avatar_border)
    ImageView avatarBorderView;

    @BindView(R.id.phone)
    EditText editTextPhone;

    @BindView(R.id.email)
    EditText editTextEmail;

    @BindView(R.id.service)
    TextView textViewService;

    @BindView(R.id.masters)
    Spinner spinnerMasters;

    @BindView(R.id.date)
    EditText editTextDate;

    @BindView(R.id.time)
    EditText editTextTime;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_record;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<String> masters  = new ArrayList<>();
        masters.add("Выберите мастера");
        masters.add("Иванов Иван Иванович");
        masters.add("Петров Петр Петрович");
        masters.add("Сидоров Сергей Сидорович");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, masters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMasters.setAdapter(adapter);

        compositeSubscription.add(
                App
                        .getDataBase()
                        .daoClient()
                        .getClient()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> showLoading())
                        .doOnEach(notification -> hideLoading())
                        .subscribe(client -> {

                            if (!client.isEmpty()) {
                                getDataBase(client.iterator().next());
                            } else {

                                getDataFromServer();

                            }
                        })
        );



    }

    private void getDataFromServer() {

        if(App.getFirebaseUser() != null) {

            FirebaseUser user = App.getFirebaseUser();

            userName.setText(user.getDisplayName());

            Glide
                    .with(this)
                    .load(user.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            avatarBorderView.setImageResource(R.drawable.avatar_placeholder);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            avatarBorderView.setImageResource(R.drawable.avatar_border);
                            return false;
                        }
                    })
                    .into(avatarView);

            editTextPhone.setText(user.getPhoneNumber());
            editTextEmail.setText(user.getEmail());

        }

    }

    private void getDataBase(Client client) {

        userName.setText(client.name);

        Glide
                .with(this)
                .load(client.photo)
                .apply(RequestOptions.circleCropTransform())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        avatarBorderView.setImageResource(R.drawable.avatar_placeholder);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        avatarBorderView.setImageResource(R.drawable.avatar_border);
                        return false;
                    }
                })
                .into(avatarView);

        editTextPhone.setText(client.phone);
        editTextEmail.setText(client.email);

    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.save_record)
    public void onClickSave(){

        if (TextUtils.isEmpty(editTextPhone.getText())) {

            showToastLongTime("Отсутсвует номер телефона");
            return;

        }

        if (TextUtils.isEmpty(editTextEmail.getText())) {

            showToastLongTime("Отсутсвует email");
            return;
        }

        if (spinnerMasters.getSelectedItem().equals("Выберите мастера")) {
            showToastLongTime("Надо выбрать мастера");
            return;
        }

        if (TextUtils.isEmpty(editTextDate.getText())) {

            showToastLongTime("Отсутсвует дата");
            return;
        }

        if (TextUtils.isEmpty(editTextTime.getText())) {

            showToastLongTime("Отсутсвует время");
            return;
        }

        if (TextUtils.isEmpty(textViewService.getText())) {

            showToastLongTime("Отсутсвуют услуги");
            return;
        }

        final Records records = new Records();

        records.date = editTextDate.getText().toString();
        records.time = editTextTime.getText().toString();
        records.nameMaster = spinnerMasters.getSelectedItem().toString();
        records.typeServices = textViewService.getText().toString();

        Completable.fromAction(() ->
                App
                        .getDataBase()
                        .daoRecords()
                        .insert(records))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    showToast("Данные добавлены");
                });

    }

    @OnClick(R.id.service)
    public void onClickService(){

        startActivityForResult(new Intent(getContext(), Services.class), AppPref.REQUEST_CODE_SERVICES);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == AppPref.REQUEST_CODE_SERVICES) {
            if (data != null) {
                textViewService.setText(data.getStringExtra(AppPref.SERVICES_RESULT));
            }
        }

    }
}
