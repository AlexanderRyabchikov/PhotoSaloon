package com.photosaloon.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    private Unbinder unbinder;

    protected abstract @LayoutRes
    int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        compositeSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }

        super.onDestroyView();
    }

    public void showLoading() {
        getBaseActivity().showLoading();
    }

    public void hideLoading() {
        getBaseActivity().hideLoading();
    }


    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void showToast(String message) {
        getBaseActivity().showToast(message);
    }

    public void showToast(@StringRes int messageId) {
        getBaseActivity().showToast(messageId);
    }

    public void showToastLongTime(String message) {
        getBaseActivity().showToastLongTime(message);
    }

    public void showToastLongTime(@StringRes int messageId) {
        getBaseActivity().showToastLongTime(messageId);
    }
}
