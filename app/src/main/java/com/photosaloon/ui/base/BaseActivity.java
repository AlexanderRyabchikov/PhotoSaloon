package com.photosaloon.ui.base;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.photosaloon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    protected ActionBar actionBar;

    protected int toolbarTitleId;

    private MaterialDialog loadingDialog;

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            assert getSupportActionBar() != null;
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.toolbar_arrow_back);
        }

        initLoadingDialog();
    }

    private void initLoadingDialog() {
        loadingDialog = new MaterialDialog
                .Builder(this)
                .progress(true, 0)
                .backgroundColorRes(R.color.dialog_bg)
                .titleColorRes(R.color.dialog_caption)
                .contentColorRes(R.color.dialog_text)
                .title(R.string.dialog_loading_title)
                .content(R.string.dialog_loading_content)
                .cancelable(false)
                .build();
    }

    @Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    public void showLoading() {
        if (loadingDialog.isShowing()) {
            return;
        }

        loadingDialog.show();
    }

    public void hideToolbarTitle() {
        setTitle("");
    }

    public void showToolbarTitle() {
        if (toolbarTitleId > 0) {
            setTitle(toolbarTitleId);
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);

        toolbarTitleId = titleId;
    }

    public void hideLoading() {
        if (!loadingDialog.isShowing()) {
            return;
        }

        loadingDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    public void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToastLongTime(@StringRes int resId) {
        showToastLongTime(getString(resId));
    }

    public void showToastLongTime(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public
    @ColorInt
    int getCompatColor(@ColorRes int colorResId) {
        return ContextCompat.getColor(this, colorResId);
    }
}
