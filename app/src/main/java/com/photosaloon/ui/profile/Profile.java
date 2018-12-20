package com.photosaloon.ui.profile;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AndroidException;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.photosaloon.model.Client;
import com.photosaloon.ui.base.BaseFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.internal.ListenerClass;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Profile extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_avatar)
    ImageView avatarView;

    @BindView(R.id.user_avatar_border)
    ImageView avatarBorderView;

    @BindView(R.id.profile_detail_phone)
    EditText phoneEditText;

    @BindView(R.id.profile_detail_email)
    EditText emailEditText;

    private Client user = new Client();


    private boolean isEditPhone = false;
    private boolean isEmailPhone = false;


    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
                             this.user = client.iterator().next();
                             getDataBase(this.user);
                         } else {

                             getDataFromServer();

                         }
                 })
        );


    }

    private void getDataBase(@NonNull Client user) {

        userName.setText(user.name);

        Glide
                .with(this)
                .load(user.photo)
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

        phoneEditText.setText(user.phone);
        emailEditText.setText(user.email);
    }

    private void getDataFromServer() {

        if(App.getFirebaseUser() != null) {

            FirebaseUser user = App.getFirebaseUser();

            userName.setText(user.getDisplayName());
            this.user.name = user.getDisplayName();
            this.user.photo = Objects.requireNonNull(user.getPhotoUrl()).toString();

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

            this.user.phone = user.getPhoneNumber();
            this.user.email = user.getEmail();
            phoneEditText.setText(user.getPhoneNumber());
            emailEditText.setText(user.getEmail());
            addClient(this.user);

        }

    }

    @SuppressLint("CheckResult")
    private void updateClient(Client client) {
        Completable.fromAction(() ->
                App
                        .getDataBase()
                        .daoClient()
                        .update(client))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> showToast("Данные обновлены"));
    }

    @SuppressLint("CheckResult")
    private void addClient(Client client) {

        Completable.fromAction(() ->
                App
                        .getDataBase()
                        .daoClient()
                        .insert(client))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> showToast("Данные добавлены"));
    }

    @OnClick(R.id.profile_detail_phone)
    public void onClickPhone() {

        if (!isEditPhone) {
            isEditPhone = true;
            phoneEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            phoneEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_save, 0);

        } else {
            isEditPhone = false;
            phoneEditText.setInputType(InputType.TYPE_NULL);
            phoneEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_profile_edit, 0);
            this.user.phone = phoneEditText.getText().toString();

            updateClient(this.user);
        }

    }

    @OnClick(R.id.profile_detail_email)
    public void onClickEmail() {

        if (!isEmailPhone) {
            isEmailPhone = true;
            emailEditText.setEnabled(true);
            emailEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_save, 0);

        } else {
            isEmailPhone = false;
            emailEditText.setEnabled(false);
            emailEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_profile_edit, 0);
            this.user.email = emailEditText.getText().toString();

            updateClient(this.user);
        }
    }

}
