package com.photosaloon.ui.profile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AndroidException;
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

import butterknife.BindView;
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



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        compositeSubscription.add(
                (Disposable) App
                .getDataBase()
                .daoClient()
                .getClient()
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                  .doOnSubscribe(disposable -> showLoading())
                 .doOnSuccess(client -> {
                     hideLoading();
                     getDataBase(client);
                 })
                 .doOnError(throwable -> {
                     hideLoading();

                     getDataFromServer();

                 })
        );



    }

    private void getDataBase(@NonNull Client client) {

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




        }

    }

}
